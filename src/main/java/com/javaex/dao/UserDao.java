package com.javaex.dao;

import com.javaex.vo.UserVo;

public interface UserDao {

	public int update(UserVo vo);
	public int insert(UserVo vo);
	public UserVo getUser(String email, String password);
	public UserVo getUser(int no);
	public String idCheck(String email);
}
