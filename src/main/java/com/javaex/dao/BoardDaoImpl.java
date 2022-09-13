package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDaoImpl implements BoardDao {
	private DBConnectionMgr pool;

	  public BoardDaoImpl() {
	    try {
	      pool = DBConnectionMgr.getInstance();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
  
	  public List<BoardVo> getList(String keyField, String keyWord, int start, int end) {

			// 0. import java.sql.*;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BoardVo> list = new ArrayList<BoardVo>();
			try {
				conn = pool.getConnection();
				// 3. SQL문 준비 / 바인딩 / 실행
				if (keyWord.equals("null") || keyWord.equals("")) {
					String query =  "SELECT B.* \r\n" 
									+"FROM( \r\n"
									+"		SELECT ROWNUM AS RNUM, A.* \r\n"
									+"	  	FROM (	select b.no, b.title, b.hit, to_char(b.reg_date,'yy-mm-dd hh24:mi') reg_date, b.user_no, b.pos, b.ref, b.depth, u.name\r\n"
									+"				from board b, users u	\r\n"
									+"				where b.user_no = u.NO order by b.reg_date desc) A	\r\n"
									+"	  	WHERE ROWNUM <= ?+?	\r\n"
									+"	    )B	\r\n"
									+"WHERE B.RNUM > ?	\r\n";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, start);
		        pstmt.setInt(2, end);
		        pstmt.setInt(3, start);
				} else {
					if(keyField.equals("attach")) {
						String query =  "SELECT B.* \r\n" 
										+"FROM( \r\n"
										+"		SELECT ROWNUM AS RNUM, A.* \r\n"
										+"	  	FROM (	select b.no, b.title, b.hit, to_char(b.reg_date,'yy-mm-dd hh24:mi') reg_date, b.user_no, b.pos, b.ref, b.depth, u.name \r\n"
										+"				from board b, users u	\r\n"
										+"				where (filename1 like ? OR filename2 like ?) AND b.user_no = u.NO  order by b.reg_date desc) A	\r\n"
										+"	  	WHERE ROWNUM <= ?+?	\r\n"
										+"	      )B	\r\n"
										+"WHERE B.RNUM > ?	\r\n";
						pstmt = conn.prepareStatement(query);
						pstmt.setString(1, "%" + keyWord + "%");
						pstmt.setString(2, "%" + keyWord + "%");
						pstmt.setInt(3, start);
						pstmt.setInt(4, end);
						pstmt.setInt(5, start);
					}else {
						String query =  "SELECT B.* \r\n" 
										+"FROM( \r\n"
										+"		SELECT ROWNUM AS RNUM, A.* \r\n"
										+"	  	FROM (	select b.no, b.title, b.hit, to_char(b.reg_date,'yy-mm-dd hh24:mi') reg_date, b.user_no, b.pos, b.ref, b.depth, u.name \r\n"
										+"				from board b, users u	\r\n"
										+"				where "+ keyField +" like ? and b.user_no = u.NO  order by b.reg_date desc) A	\r\n"
										+"	  	WHERE ROWNUM <= ?+?	\r\n"
										+"	      )B	\r\n"
										+"WHERE B.RNUM > ?	\r\n";
						pstmt = conn.prepareStatement(query);
						pstmt.setString(1, "%" + keyWord + "%");
						pstmt.setInt(2, start);
						pstmt.setInt(3, end);
						pstmt.setInt(4, start);
					}
				}
				
				rs = pstmt.executeQuery();

				// 4.결과처리-
				while (rs.next()) {
					BoardVo vo = new BoardVo();
					vo.setNo(rs.getInt("no"));
					vo.setUser_no(rs.getInt("user_no"));
					vo.setTitle(rs.getString("title"));
					vo.setPos(rs.getInt("pos"));
					vo.setRef(rs.getInt("ref"));
					vo.setDepth(rs.getInt("depth"));
					vo.setReg_date(rs.getString("reg_date"));
					vo.setHit(rs.getInt("hit"));
					vo.setUser_name(rs.getString("name"));
					
					list.add(vo);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			      System.out.println("error:" + e);
			    } catch (Exception e) {
			      e.printStackTrace();
			    } finally {
			      // 5. 자원정리
			      try { 
			        if (pstmt != null) {
			          pstmt.close();
			        }
			        if (conn != null) {
			          pool.freeConnection(conn);
			        }
			      } catch (SQLException e) {
			    	 e.printStackTrace();
			        System.out.println("error:" + e);
			      }

			    }
			
			return list;
		}

	
	public BoardVo getBoard(int no) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo boardVo = null;
		
		try {
		  conn = pool.getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, u.name, b.filename1, b.filename2 "
					     + "from board b, users u "
					     + "where b.user_no = u.no "
					     + "and b.no = ?";
			
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
				String filename1 = rs.getString("filename1");
				String filename2 = rs.getString("filename2");
				
				boardVo = new BoardVo(no, title, content, hit, reg_date, user_no, user_name, filename1, filename2);
			}
			
		} catch (SQLException e) {
		      
		      System.out.println("error:" + e);
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // 5. 자원정리
		      try {
		        if (pstmt != null) {
		          pstmt.close();
		        }
		        if (conn != null) {
		          pool.freeConnection(conn);
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
		  conn = pool.getConnection();
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = 	"insert into board (no, user_no, title, content, filename1, filename2, new_filename1, new_filename2, hit, reg_date)"
						  + "values (seq_board_no.nextval, ?, ?, ?, ?, ?, ?, ?, 0, sysdate)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, vo.getUser_no());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getFilename1());
			pstmt.setString(5, vo.getFilename2());
			pstmt.setString(6, vo.getNew_filename1());
			pstmt.setString(7, vo.getNew_filename2());
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
		      
		      System.out.println("error:" + e);
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // 5. 자원정리
		      try {
		        if (pstmt != null) {
		          pstmt.close();
		        }
		        if (conn != null) {
		          pool.freeConnection(conn);
		        }
		      } catch (SQLException e) {
		    	  e.printStackTrace();
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
		  conn = pool.getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
		      
		      System.out.println("error:" + e);
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // 5. 자원정리
		      try {
		        if (pstmt != null) {
		          pstmt.close();
		        }
		        if (conn != null) {
		          pool.freeConnection(conn);
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
		  conn = pool.getConnection();

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
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // 5. 자원정리
		      try {
		        if (pstmt != null) {
		          pstmt.close();
		        }
		        if (conn != null) {
		          pool.freeConnection(conn);
		        }
		      } catch (SQLException e) {
		        System.out.println("error:" + e);
		      }

		    }

		return count;
	}
	
	// 조회수 증가
	public void upCount(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = pool.getConnection();
			sql = "update Board set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
		      
		      System.out.println("error:" + e);
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // 5. 자원정리
		      try {
		        if (pstmt != null) {
		          pstmt.close();
		        }
		        if (conn != null) {
		          pool.freeConnection(conn);
		        }
		      } catch (SQLException e) {
		        System.out.println("error:" + e);
		      }

		    }
	}
	
	//총게시물수
	public int getTotalCount(String keyField, String keyWord) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int totalCount = 0;
		try {
			conn = pool.getConnection();
			if (keyWord.equals("null") || keyWord.equals("")) {
				sql = "select count(no) from Board";
				pstmt = conn.prepareStatement(sql);
			} else {
				if(keyField.equals("attach")) {
					sql = "select count(*) from  Board b, users u where b.user_no = u.NO and (filename1 like ? OR filename2 like ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%" + keyWord + "%");
					pstmt.setString(2, "%" + keyWord + "%");
				}else {
					sql = "select count(*) from  Board b, users u where b.user_no = u.NO and " + keyField + " like ? ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%" + keyWord + "%");
				}
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
		      
		      System.out.println("error:" + e);
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // 5. 자원정리
		      try {
		        if (pstmt != null) {
		          pstmt.close();
		        }
		        if (conn != null) {
		          pool.freeConnection(conn);
		        }
		      } catch (SQLException e) {
		        System.out.println("error:" + e);
		      }

		    }
		return totalCount;
	}
	
}
