package com.kosta.khk.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kosta.khk.dao.UserDao;
import com.kosta.khk.dao.UserDaoImpl;
import com.kosta.khk.util.WebUtil;
import com.kosta.khk.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");
		System.out.println("user:" + actionName);

		if ("joinform".equals(actionName)) {

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");

		} else if ("join".equals(actionName)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			UserVo vo = new UserVo(name, email, password, gender);

			UserDao dao = new UserDaoImpl();
			dao.insert(vo);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp");
			rd.forward(request, response);

			// id(email) 중복검사 : api/emailCheck.jsp 대신 적용
		} else if ("idcheck".equals(actionName)) {
			String email = request.getParameter("email");
			UserDao dao = new UserDaoImpl();

			response.setContentType("text/html; charset=UTF-8");

			// "true", "false" 문자열이 반환되므로 ajax에서 결과값으로 받아서 처리
			response.getWriter().write(dao.idCheck(email));
			System.out.println(dao.idCheck(email));
			
		} else if ("modify".equals(actionName)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setGender(gender);

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			int no = authUser.getNo();
			vo.setNo(no);

			UserDao dao = new UserDaoImpl();
			dao.update(vo);

			authUser.setName(name);

			response.sendRedirect("/mysite/main");

		} else if ("modifyform".equals(actionName)) {

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			int no = authUser.getNo(); // 회원번호

			UserDao dao = new UserDaoImpl();
			UserVo userVo = dao.getUser(no);
			System.out.println(userVo.toString());

			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");

		} else if ("loginform".equals(actionName)) {

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);

		} else if ("login".equals(actionName)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			UserDao dao = new UserDaoImpl();
			UserVo vo = dao.getUser(email, password);

			if (vo == null) {
				System.out.println("실패");
				response.sendRedirect("/mysite/user?a=loginform&result=fail");
			} else {
				System.out.println("성공");
				HttpSession session = request.getSession(true);
				session.setAttribute("authUser", vo);
				response.sendRedirect("/mysite/main");
				return;
			}

		} else if ("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			response.sendRedirect("/mysite/main");

		} else {

			WebUtil.redirect(request, response, "/mysite/main");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
