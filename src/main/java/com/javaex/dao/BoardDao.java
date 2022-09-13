package com.javaex.dao;

import java.util.List;
import com.javaex.vo.BoardVo;

public interface BoardDao {
	public List<BoardVo> getList(String keyField, String keyWord, int start, int end);  // 게시물 전체 목록 조회
	public BoardVo getBoard(int no); // 게시물 상세 조회
	public int insert(BoardVo vo);   // 게시물 등록
	public int delete(int no);       // 게시물 삭제
	public int update(BoardVo vo);   // 게시물 수정
	public int getTotalCount(String keyField, String keyWord);	//페이징 전체 수 가져오기
	public void upCount(int num);	//조회수 증가
	public void replyUpBoard(int ref, int pos);	// 답변에 위치값 증가
	public void replyBoard(BoardVo boardVo);	// 게시물 답변
}
