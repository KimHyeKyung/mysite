package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.BoardDaoImpl;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String savePagenum ; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String actionName = request.getParameter("a");
		System.out.println("board:" + actionName);
		
		//페이징
		int totalRecord = 0; // 전체 레코드 수
		int numPerPage = 10; // 페이지당 레코드 수

		request.setAttribute("numPerPage", numPerPage);

		int pagePerBlock = 10; // 블럭당 페이지수

		request.setAttribute("pagePerBlock", pagePerBlock);

		int totalPage = 0; // 전체 페이지 수
		int totalBlock = 0; // 전체 블럭 수

		int nowPage = 1; // 현재 페이지
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage")); // readfrm 에서 가져오는 거
		}

		request.setAttribute("nowPage", nowPage);
		int nowBlock = 1; // 현재블럭

		int start = 0; 
		int end = 10; 

		int listSize = 0; // 현재 읽어온 게시물의 수

		String keyWord = "";
		String keyField = "";

		if (request.getParameter("reload") != null) {
			if (request.getParameter("reload").equals("true")) {
				keyWord = "";
				keyField = "";
			}
		}
		
		if (request.getParameter("keyWord") != null) { 
			keyWord = request.getParameter("keyWord");
			keyField = request.getParameter("keyField");

		}

		start = (nowPage * numPerPage) - numPerPage; 
		end = numPerPage; 

		BoardDao dao = new BoardDaoImpl();
		totalRecord = dao.getTotalCount(keyField, keyWord);  // 전체 게시물 수
		request.setAttribute("totalRecord", totalRecord);
		
		totalPage = (int) Math.ceil((double) totalRecord / numPerPage); // 전체페이지수
		request.setAttribute("totalPage", totalPage);
		
		nowBlock = (int) Math.ceil((double) nowPage / pagePerBlock); // 현재블럭 계산
		request.setAttribute("nowBlock", nowBlock);
		
		totalBlock = (int) Math.ceil((double) totalPage / pagePerBlock); // 전체블럭계산
		request.setAttribute("totalBlock", totalBlock);

		int pageStart = (nowBlock - 1) * pagePerBlock + 1; // 하단 페이지 시작번호
		request.setAttribute("pageStart", pageStart);

		int pageEnd = ((pageStart + pagePerBlock) <= totalPage) ? (pageStart + pagePerBlock - 1) : totalPage;
		// 하단 페이지 끝번호
		request.setAttribute("pageEnd", pageEnd);
				
		if ("list".equals(actionName)) {
			//현재 몇번째 메이지인지 페이지 값 저장하기 위한 처리
			//두번째 페이지에서 글 목록 눌렀을때 두번째 페이지로 되돌아가기위해서!
			savePagenum =Integer.toString(nowPage);
			
			// 리스트 가져오기
			List<BoardVo> list = dao.getList(keyField, keyWord, start, end);
			request.setAttribute("list", list);
			request.setAttribute("keyField", keyField);
			request.setAttribute("keyWord", keyWord);
			
			System.out.println("list:" + list.toString());
			listSize = list.size();
			System.out.println("listSize:" + listSize);
			
			System.out.println("완료");
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
	    
		} else if ("read".equals(actionName)) {
			//세션에 저장
			HttpSession session = request.getSession(true);
			session.setAttribute("savePagenum", savePagenum);
			
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardVo = dao.getBoard(no);
			dao.upCount(no);

			System.out.println(boardVo.toString());

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		} else if ("modifyform".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardVo = dao.getBoard(no);

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
			
		} else if ("modify".equals(actionName)) {
			// 게시물 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo vo = new BoardVo(no, title, content);
			
			dao.update(vo);
			
			WebUtil.redirect(request, response, "/mysite/board?a=list&nowPage="+savePagenum);
		} else if ("writeform".equals(actionName)) {
			// 로그인 여부체크
			UserVo authUser = getAuthUser(request);
			if (authUser != null) { // 로그인했으면 작성페이지로
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");
			} else { // 로그인 안했으면 리스트로
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}

		} else if ("write".equals(actionName)) {
			UserVo authUser = getAuthUser(request);
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int user_no = authUser.getNo();

			System.out.println("user_no : ["+user_no+"]");
			System.out.println("title : ["+title+"]");
			System.out.println("content : ["+content+"]");

			BoardVo vo = new BoardVo(title, content, user_no);
			dao.insert(vo);

			WebUtil.redirect(request, response, "/mysite/board?a=list");
			
		} else if ("delete".equals(actionName)) {
			int no = Integer.parseInt(request.getParameter("no"));

			dao.delete(no);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		} else if ("replyform".equals(actionName)) {
			// 로그인 여부체크
			UserVo authUser = getAuthUser(request);
			if (authUser != null) { // 로그인했으면 작성페이지로
				WebUtil.forward(request, response, "/WEB-INF/views/board/replyform.jsp");
			} else { // 로그인 안했으면 리스트로
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}
			
		} else if ("reply".equals(actionName)) {
			int no = Integer.parseInt(request.getParameter("no"));
			//dao.delete(no);
			WebUtil.redirect(request, response, "/mysite/board?a=list");
			
			
			
		} else {
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	// 로그인 되어 있는 정보를 가져온다.
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

}
