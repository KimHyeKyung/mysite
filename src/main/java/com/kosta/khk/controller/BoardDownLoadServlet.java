package com.kosta.khk.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downLoad")
public class BoardDownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ATTACHES_DIR = "D:\\javaStudy\\workspace\\mysite\\src\\main\\webapp\\assets\\fileUpload\\";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String filename = request.getParameter("filename");
			filename = new String(filename.getBytes("8859_1"),"utf-8");
			byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File(ATTACHES_DIR +filename));

		    response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");
		    response.getOutputStream().write(fileByte);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
