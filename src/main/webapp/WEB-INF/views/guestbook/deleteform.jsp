<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String no = request.getParameter("no");
%>
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
				<div id="guestbook" class="delete-form">
					<form method="post" action="/mysite/gb">
						<input type='hidden' name="no" value="<%= no %>">
	    				<input type='hidden' name="a" value="delete">
						<label>비밀번호</label>
						<input type="password" name="password">
						<input type="submit" value="확인">
					</form>
					<a href="/WEB-INF/views/guestbook/list.jsp">방명록 리스트</a>
					
				</div>
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<div id="footer">
			<p>(c)opyright 2015,2016,2017</p>
		</div> <!-- /footer -->
		
	</div> <!-- /container -->

</body>
</html>
