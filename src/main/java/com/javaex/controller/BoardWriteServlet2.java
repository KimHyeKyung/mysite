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

@WebServlet("/writeTest")
public class BoardWriteServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파일 (출처: dololak.tistory.com/720)
		String filename = "";
		String new_filename = "";
		int filesize;
		//저장할 디렉토리
		String saveDir = "D:\\javaStudy\\workspace\\mysite\\src\\main\\webapp\\assets\\fileUpload";
		int size = 10 * 1024 * 1024;
		File currentDir = new File(saveDir);
		
		//DiskFileItemFactory는 업로드 된 파일을 저장할 저장소와 관련된 클래스
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//setRepository()메서드는 업로드된 파일을 저장할 위치를  'File'객체로 지정
		factory.setRepository(currentDir);
		//setSizeThreshold()메서드는 저장소에 임시파일을 생성할 한계 크기를 byte단위로 지정
		factory.setSizeThreshold(size);
		
		//ServletFileUpload오브젝트 생성
		//: 'ServletFileUpload'클래스는 HTTP요청에 대한 'HttpServletRequest '객체로부터 ' multipart/form-data '형식으로 넘어온 HTTP Body부분을 다루기 쉽게 변환해주는 역할을 수행.
		//: ex)
		//: ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory); 
		//: List<FileItem> items = fileUpload.parseRequest(request);
		//: ' parseRequest() '메서드를 수행하면 ' FileItem '이라는 형식으로 반환해줌
		
		//FileItem
		//: 사용자가 업로드한 ' File '데이터나 사용자가 ' input text '에 입력한 일반 요청 데이터에 대한 객체.
		//: 'FileItem isFormField() '메서드의 리턴값이 'true'이면 'text'같은 일반 입력 데이터이며, 'false'이면 파일데이터임을 알 수 있음. 즉, 리턴값이 'false'인 경우에만 업로드된 파일인 것으로 인지하여 처리하면 됨.
		ServletFileUpload upload = new ServletFileUpload(factory);
		String title = "";
		String content = "";
		try {
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem fi : items) {
				if(fi.isFormField()) {//그 밖의 form데이터
					//getFieldName(): 데이터의 name을 리턴. input태그에 설정된 name값
					System.out.println(fi.getFieldName() + " = " + fi.getString("utf-8"));
					if(fi.getFieldName().equals("title")) {
						title = fi.getString("utf-8");
					}else if(fi.getFieldName().equals("content")){
						content = fi.getString("utf-8");
					}
				}else {//파일데이터
					System.out.println(fi.getFieldName());
		
					//getName():데이터가 첨부파일인 경우 파일명 또는 파일 경로를 리턴
					filename = fi.getName();
					System.out.println("filename: "+filename);
					
					//new_filname
					UUID uuid = UUID.randomUUID();
					//filename의 확장자 제거
					String fileNameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
					//filename의 확장자
					String ext = filename.substring(filename.lastIndexOf("."));
					//new_filename = 확장자제거한 파일명 + 랜덤
					new_filename = fileNameWithoutExtension+uuid+ext;
					System.out.println("new_filename: "+new_filename);
					
					//파일사이즈
					filesize = (int) fi.getSize();
					System.out.println("filesize: "+fi.getSize());

					UserVo authUser = getAuthUser(request);
					//String title = request.getParameter("title");
					//String content = request.getParameter("content");
					int user_no = authUser.getNo();

					System.out.println("user_no : ["+user_no+"]");
					System.out.println("title : ["+title+"]");
					System.out.println("content : ["+content+"]");
					System.out.println("filename : ["+filename+"]");
					System.out.println("new_filename : ["+new_filename+"]");

					BoardVo vo = new BoardVo(user_no, title, content, filename, new_filename);
					BoardDao dao = new BoardDaoImpl();
					dao.insert(vo);

					WebUtil.redirect(request, response, "/mysite/board?a=list");
				}
			}
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
