package com.kosta.khk.dao;

import java.util.List;

import com.kosta.khk.vo.GuestbookVo;

public interface GuestbookDao {
	
	public List<GuestbookVo> getList();

	public int insert(GuestbookVo vo);

	public int delete(GuestbookVo vo);
}
