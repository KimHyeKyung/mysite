package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;


public class BoardDaoImpl implements BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, "webdb", "1234");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 로드 실패!");
		}
		return conn;
	}
/////////////////////////////////////새로만든영역 시작/////////////////////////////////////////
@Override
public List<BoardVo> getBoardList(String keyField, String keyWord, int start, int end) {
	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<BoardVo> list = new ArrayList<BoardVo>();

	try {
		conn = getConnection();

		// 3. SQL문 준비 / 바인딩 / 실행
		if (keyWord.equals("null") || keyWord.equals("")) {
			String query = "SELECT * \r\n" 
							+"FROM( \r\n"
							+"		SELECT ROWNUM AS RNUM, A.* \r\n"
							+"	  	FROM (	select b.no, b.title, b.hit, to_char(b.reg_date,'yy-mm-dd hh24:mi') reg_date, b.user_no, b.pos, b.ref, b.depth, u.name AS user_name	\r\n"
							+"				from board b, users u	\r\n"
							+"				where b.user_no = u.NO order by ref desc, pos ) A	\r\n"
							+"	  	WHERE ROWNUM <= ?+?	\r\n"
							+"	    )	\r\n"
							+"WHERE RNUM > ?	\r\n";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
		} else {
			String query = "SELECT * \r\n" 
							+"FROM( \r\n"
							+"		SELECT ROWNUM AS RNUM, A.* \r\n"
							+"	  	FROM (	select b.no, b.title, b.hit, to_char(b.reg_date,'yy-mm-dd hh24:mi') reg_date, b.user_no, b.pos, b.ref, b.depth, u.name AS user_name	\r\n"
							+"				from board b, users u	\r\n"
							+"				where where \" + keyField + \" like ? and b.user_no = u.NO  order by ref desc, pos ) A	\r\n"
							+"	  	WHERE ROWNUM <= ?+?	\r\n"
							+"	      )	\r\n"
							+"	WHERE RNUM > ?	\r\n";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyWord + "%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			pstmt.setInt(4, start);
		}
		rs = pstmt.executeQuery();
		// 4.결과처리
		while (rs.next()) {
			BoardVo vo = new BoardVo();
			vo.setNo(rs.getInt("no"));
			vo.setUser_name(rs.getString("user_name"));
			vo.setUser_no(rs.getInt("user_no"));
			vo.setTitle(rs.getString("title"));
			vo.setPos(rs.getInt("pos"));
			vo.setRef(rs.getInt("ref"));
			vo.setDepth(rs.getInt("depth"));
			vo.setReg_date(rs.getString("reg_date"));
			vo.setHit(rs.getInt("hit"));
			list.add(vo);
		}

	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("error:" + e);
	} finally {
		// 5. 자원정리
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	return list;
}
/////////////////////////////////////새로만든영역 끝/////////////////////////////////////////

	// read
	public BoardVo getBoard(int no) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo boardVo = null;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, u.name "
					+ "from board b, users u " + "where b.user_no = u.no " + "and b.no = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String user_name = rs.getString("name");

				boardVo = new BoardVo(no, title, content, hit, reg_date, user_no, user_name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		System.out.println(boardVo);
		return boardVo;

	}

	public int insert(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			System.out.println("vo.user_no : [" + vo.getUser_no() + "]");
			System.out.println("vo.title : [" + vo.getTitle() + "]");
			System.out.println("vo.content : [" + vo.getContent() + "]");

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUser_no());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int delete(int no) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int update(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set title = ?, content = ? where no = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	// 조회수 증가
	public void upHit(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}
	// 총 게시물수
	public int getTotalCount(String keyField, String keyWord) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int totalCount = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			if (keyWord.equals("null") || keyWord.equals("")) {
				sql = "select count(no) from board";
				pstmt = conn.prepareStatement(sql);
			} else {
				sql = "select count(no) from  board where " + keyField + " like ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyWord + "%");
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error:" + e);
			}

		}
		return totalCount;
	}

		
	
}
