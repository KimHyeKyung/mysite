package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;

public interface BoardDao {
	
	// 게시물 전체 목록 조회
	public List<BoardVo> getBoardList(String keyField, String keyWord, int start, int end);

	// 게시물 상세 조회
	public BoardVo getBoard(int no);
	
	// 게시물 등록
	public int insert(BoardVo vo); 		
	
	// 게시물 삭제
	public int delete(int no); 			
	
	// 게시물 수정
	public int update(BoardVo vo); 		
	
	// 조회수 증가
	public void upHit(int no);

	public int getTotalCount(String keyField, String keyWord);
	
}
