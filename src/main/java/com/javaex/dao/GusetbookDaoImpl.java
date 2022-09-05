package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;


public class GusetbookDaoImpl implements GuestbookDao {

	private DBConnectionMgr pool;

	public GusetbookDaoImpl() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<GuestbookVo> getList() {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GuestbookVo> list = new ArrayList<GuestbookVo>();

		try {
			conn = pool.getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query 	= "select no, name, PASSWORD , CONTENT, to_char(REG_DATE,'yyyy-mm-dd') REG_DATE\r\n"
							+ "from guestbook\r\n"
							+ "order by no desc";
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");

				GuestbookVo vo = new GuestbookVo(no, name, password, content, reg_date);
				list.add(vo);
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

		return list;
	}

	@Override
	public int insert(GuestbookVo vo) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    int count = 0 ;
	    
	    try {
	      conn = pool.getConnection();
	      
	      String query ="insert into guestbook values (seq_guestbook_no.nextval, ?, ?, ?, sysdate)";
	      pstmt = conn.prepareStatement(query); 
	      
	      pstmt.setString(1, vo.getName());
	      pstmt.setString(2, vo.getPassword());
	      pstmt.setString(3, vo.getContent());
	    
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
	    return count;
	}

	@Override
	public int delete(GuestbookVo vo) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    int count = 0 ;
	    
	    try {
	      conn = pool.getConnection();
	      
	      String query ="delete from guestbook where no = ? and password = ?";
	      pstmt = conn.prepareStatement(query); 
	      
	      pstmt.setInt(1, vo.getNo());
	      pstmt.setString(2, vo.getPassword());
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
	    return count;
	  }

}
