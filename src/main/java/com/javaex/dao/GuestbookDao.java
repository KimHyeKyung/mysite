package com.javaex.dao;

import java.util.List;

import com.javaex.vo.GuestbookVo;

public interface GuestbookDao {
	
	public List<GuestbookVo> getList();

	public int insert(GuestbookVo vo);

	public int delete(GuestbookVo vo);
}
