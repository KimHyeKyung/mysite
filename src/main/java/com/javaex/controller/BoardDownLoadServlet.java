package com.javaex.controller;

import java.io.File;
import java.io.IOException;
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

@WebServlet("/downLoad")
public class BoardDownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CHARSET = "UTF-8";
	private static final String ATTACHES_DIR = "D:\\javaStudy\\workspace\\mysite\\src\\main\\webapp\\assets\\fileUpload";
	private static final int LIMIT_SIZE_BYTES = 1024 * 1024;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(CHARSET);

		File attachesDir = new File(ATTACHES_DIR);
		
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setRepository(attachesDir);
		fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		
		// 파라미터 이름이 title인 데이터를 저장할 변수	
		String title = null;
		String content = null;
		String filename1 = null;
		String filename2 = null;
		String new_filename1 = null;
		String new_filename2 = null;
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			for(FileItem item : items) {
				String fieldName = item.getFieldName();
                String fieldValue = item.getString();
				if(item.isFormField()) {//그 밖의 form데이터
					if(fieldName.equals("title")) {
						title=fieldValue;
						title = new String(title.getBytes("8859_1"),"utf-8");
					}else if(fieldName.equals("content")) {
						content=fieldValue;
						content = new String(content.getBytes("8859_1"),"utf-8");
					}
				}else {//파일데이터
					System.out.printf("파라미터 명 : %s, 파일 명 :  %s \n", item.getFieldName(), item.getName(), item.getSize());
					if(item.getSize() > 0) {
						if(fieldName.equals("filename1")) {
							String separator = File.separator;
							int index = item.getName().lastIndexOf(separator);
							String fileUploadName = item.getName().substring(index + 1);
							File uploadFile = new File(ATTACHES_DIR + separator + fileUploadName);
							item.write(uploadFile);
							
							//[파일명 설정]
							//getName():데이터가 첨부파일인 경우 파일명 또는 파일 경로를 리턴
							filename1 = item.getName();
							
							//new_fileName
							UUID uuid = UUID.randomUUID();
							
							//filename의 확장자 제거
							String fileNameWithoutExtension = filename1.substring(0, filename1.lastIndexOf('.'));
							
							//filename의 확장자
							String ext = filename1.substring(filename1.lastIndexOf("."));
							
							//new_filename = 확장자제거한 파일명 + 랜덤
							new_filename1 = fileNameWithoutExtension+uuid+ext;
						}else if(fieldName.equals("filename2")) {
							String separator = File.separator;
							int index = item.getName().lastIndexOf(separator);
							String fileUploadName = item.getName().substring(index + 1);
							File uploadFile = new File(ATTACHES_DIR + separator + fileUploadName);
							item.write(uploadFile);
							
							//[파일명 설정]
							//getName():데이터가 첨부파일인 경우 파일명 또는 파일 경로를 리턴
							filename2 = item.getName();
							
							//new_fileName
							UUID uuid = UUID.randomUUID();
							
							//filename의 확장자 제거
							String fileNameWithoutExtension = filename2.substring(0, filename2.lastIndexOf('.'));
							
							//filename의 확장자
							String ext = filename2.substring(filename2.lastIndexOf("."));
							
							//new_filename = 확장자제거한 파일명 + 랜덤
							new_filename2 = fileNameWithoutExtension+uuid+ext;
						}
					}
				}
			}
			UserVo authUser = getAuthUser(request);
			int user_no = authUser.getNo();
			request.setAttribute("filename1", filename1);
			request.setAttribute("filename2", filename2);
			
			System.out.println("user_no : ["+user_no+"]");
			System.out.println("title : ["+title+"]");
			System.out.println("content : ["+content+"]");
			System.out.println("filename1 : ["+filename1+"]");
			System.out.println("filename2 : ["+filename2+"]");
			System.out.println("new_filename1 : ["+new_filename1+"]");
			System.out.println("new_filename2 : ["+new_filename2+"]");
			
			BoardVo vo = new BoardVo(user_no, title, content, filename1, filename2, new_filename1, new_filename2);
			BoardDao dao = new BoardDaoImpl();
			dao.insert(vo);

			WebUtil.redirect(request, response, "/mysite/board?a=list");
			
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
