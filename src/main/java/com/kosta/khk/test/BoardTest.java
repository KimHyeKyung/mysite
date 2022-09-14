package com.kosta.khk.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.kosta.khk.dao.DBConnectionMgr;
import com.kosta.khk.vo.BoardVo;

class BoardTest {
	private DBConnectionMgr pool;

	
	public void  insert() {
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
			pstmt.setInt(1, 41);
			pstmt.setString(2, "junit title");
			pstmt.setString(3, "junit content");
			pstmt.setString(4, "");
			pstmt.setString(5, "");
			pstmt.setString(6, "");
			pstmt.setString(7, "");
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
	}

	
	public void update() {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
		  conn = pool.getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set title = ?, content = ? where no = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "junit title 수정");
			pstmt.setString(2, "junit contente 수정");
			pstmt.setInt(3, 41);

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
	}
	
	@Test
	public void delete() {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
		  conn = pool.getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, 41);

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
	}
	
}//class end
