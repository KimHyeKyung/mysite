<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
pageContext.setAttribute( "newLine", "\n" );
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet"
	type="text/css">
<title>Mysite</title>
<script type="text/javascript">
	function down(filename){
		 document.downFrm.filename.value=filename;
		 document.downFrm.submit();
	}
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		</div>
		<!-- /header -->
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		</div>
		<!-- /navigation -->

		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title}</td>
					</tr>
					<tr>
						<td class="label">파일</td>
						<td>
							<c:if test="${boardVo.filename1 != null || boardVo.filename2 != null}">
								<p>
									<a href="javascript:down(${boardVo.filename1})">${boardVo.filename1}</a>
								</p>
								<p style="margin-top: 5px;">
									<a href="javascript:down(${boardVo.filename2})">${boardVo.filename2}</a>
								</p>
							</c:if>
							<c:if test="${boardVo.filename1 == null || boardVo.filename2 == null}">
								<p>등록된 파일이 없습니다.</p>
							</c:if>
			  		 	</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content" style="height: 80px;">${fn:replace(boardVo.content, newLine, "<br>")}
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="/mysite/board?a=list&nowPage=${savePagenum}">글목록</a>

					<c:if test="${authUser.no == boardVo.user_no}">
						<a href="/mysite/board?a=modifyform&no=${boardVo.no}">글수정</a>
					</c:if>
				</div>
			</div>
		</div>
		<form name="downFrm" action="/mysite/downLoad" method="post">
			<input type="hidden" name="filename1">
			<input type="hidden" name="filename2">
		</form>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>

	</div>
	<!-- /container -->
</body>
</html>

