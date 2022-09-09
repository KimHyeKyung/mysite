package com.javaex.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.javaex.dao.BoardDao;
import com.javaex.dao.BoardDaoImpl;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/write")
public class BoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CHARSET = "utf-8";
	private static final String ATTACHES_DIR = "D:\\javaStudy\\workspace\\mysite\\src\\main\\webapp\\assets\\fileUpload";
	private static final int LIMIT_SIZE_BYTES = 1024 * 1024;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(CHARSET);
		PrintWriter out = response.getWriter();

		File attachesDir = new File(ATTACHES_DIR);
		
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setRepository(attachesDir);
		fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			for(FileItem item : items) {
				if(item.isFormField()) {//그 밖의 form데이터
					System.out.printf("파라미터 명 : %s, 파라미터 값 :  %s \n", item.getFieldName(), item.getString(CHARSET));
				}else {//파일데이터
					System.out.printf("파라미터 명 : %s, 파일 명 :  %s", item.getFieldName(), item.getName(), item.getSize());
					if(item.getSize() > 0) {
						String separator = File.separator;
						int index = item.getName().lastIndexOf(separator);
						String fileName = item.getName().substring(index + 1);
						File uploadFile = new File(ATTACHES_DIR + separator + fileName);
						item.write(uploadFile);
					}

//					UserVo authUser = getAuthUser(request);
//					String title = request.getParameter("title");
//					String content = request.getParameter("content");
//					int user_no = authUser.getNo();
//
//					System.out.println("user_no : ["+user_no+"]");
//					System.out.println("title : ["+title+"]");
//					System.out.println("content : ["+content+"]");
//					System.out.println("filename : ["+filename+"]");
//					System.out.println("new_filename : ["+new_filename+"]");
//
//					BoardVo vo = new BoardVo(user_no, title, content, filename, new_filename);
//					BoardDao dao = new BoardDaoImpl();
//					dao.insert(vo);

					WebUtil.redirect(request, response, "/mysite/board?a=list");
				}
			}
			System.out.println("파일업로드완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인 되어 있는 정보를 가져온다.
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

}
