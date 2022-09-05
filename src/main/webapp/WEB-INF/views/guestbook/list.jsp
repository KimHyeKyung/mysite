<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.javaex.dao.GuestbookDao"%>
<%@ page import="com.javaex.dao.GusetbookDaoImpl"%>
<%@ page import="com.javaex.vo.GuestbookVo"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		</div> <!-- /header -->
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		</div> <!-- /navigation -->
		
		<div id="wrapper">
			<div id="content">
				<div id="guestbook">
					
					<form action="/mysite/gb" method="post">
						<input type="hidden" name="a" value="add">
						<table>
							<tr>
								<td>이름</td><td><input type="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" id="content"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input type="submit" VALUE="확인" /></td>
							</tr>
						</table>
					</form>
					<ul>
						<li>
							<!-- 방명록 리스트 -->
							<%-- list에서 하나씩 빼서 테이블를 채운다--%>
							<%
							GuestbookDao dao = new GusetbookDaoImpl();
							List<GuestbookVo> list = dao.getList();      
						      for(GuestbookVo vo : list) {
						  	%>
							<table width="100%" border=1>
								<tr>
									<td style="text-align: center;">[<%=vo.getNo()%>]</td>
									<td  width="200px"><%=vo.getName()%></td>
									<td><%=vo.getReg_date()%></td>
									<td width="50">
										<a href="/mysite/gb?a=deleteform&no=<%=vo.getNo()%>" style="text-decoration : none; color: gray;">삭제</a>
									</td>
								</tr>
								<tr>
									<td colspan=4 style="padding: 15px 10px"><%=vo.getContent()%></td>
								</tr>
							</table>
							</br>
						  <%
						  }
						  %>
							<br>
						</li>
					</ul>
					
				</div><!-- /guestbook -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<div id="footer">
			<p>(c)opyright 2015,2016,2017</p>
		</div> <!-- /footer -->
		
	</div> <!-- /container -->

</body>
</html>