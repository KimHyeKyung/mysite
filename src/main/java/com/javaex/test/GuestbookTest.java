package com.javaex.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.javaex.dao.DBConnectionMgr;

class GuestbookTest {

	private DBConnectionMgr pool;

	public void GuestbookDaoImpl() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void insert() {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    int count = 0 ;
	    
	    try {
	      conn = pool.getConnection();
	      
	      String query ="insert into guestbook values (seq_guestbook_no.nextval, ?, ?, ?, sysdate)";
	      pstmt = conn.prepareStatement(query); 
	      
	      pstmt.setString(1, "junit");
	      pstmt.setString(2, "4567");
	      pstmt.setString(3, "content");
	    
	      count = pstmt.executeUpdate();
	      
	      System.out.println(count + "건 등록");
	      
	    } catch (SQLException e) {
	      System.out.println("error:" + e);
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        if (pstmt != null) pstmt.close();
	        if (conn != null) pool.freeConnection(conn);
	      } catch (SQLException e) {
	        System.out.println("error:" + e);
	      }
	    }
	}
	
	@Test
	public void delete() {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    int count = 0 ;
	    
	    try {
	      conn = pool.getConnection();
	      
	      String query ="delete from guestbook where no = ? and password = ?";
	      pstmt = conn.prepareStatement(query); 
	      
	      pstmt.setInt(1, 41);
	      pstmt.setString(2, "4567");
	      count = pstmt.executeUpdate();
	      
	      System.out.println(count + "건 삭제");
	      
	    } catch (SQLException e) {
	      System.out.println("error:" + e);
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        if (pstmt != null) pstmt.close();
	        if (conn != null) pool.freeConnection(conn);
	      } catch (SQLException e) {
	        System.out.println("error:" + e);
	      }
	    }
	  }

	
	
	
	
	
}//class 종료
