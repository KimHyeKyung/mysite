<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.javaex.vo.BoardVo"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function list() {
		document.listFrm.action = "list.jsp";
		document.listFrm.submit();
	}
	
	function pageing(page) {
		document.readFrm.nowPage.value = page;
		document.readFrm.submit();
	}
	
	function block(value){
		alert("block" + value );
		 document.readFrm.nowPage.value = ${pageVo.pagePerBlock} * (value-1) +1;
		 document.readFrm.submit();
	} 
	
	function read(num){
		document.readFrm.num.value=num;
		document.readFrm.action="read.jsp";
		document.readFrm.submit();
	}
	
	function check() {
	     if (document.searchFrm.keyWord.value == "") {
			alert("검색어를 입력하세요.");
			document.searchFrm.keyWord.focus();
			return;
	     }
	  document.searchFrm.submit();
	}
</script>
<title>Mysite</title>
</head>
<body>
	<div id="container">
	
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		</div>
		
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		</div>
		
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:forEach items="${list}" var="vo">
						<tr>
							<td>${vo.no}</td>
							<td><a href="/mysite/board?a=read&no=${vo.no}">${vo.title}</a></td>
							<td>${vo.user_name}</td>
							<td>${vo.hit}</td>
							<td>${vo.reg_date}</td>
							<td>
								<c:if test="${authUser.no == vo.user_no}">
									<a href="/mysite/board?a=delete&no=${vo.no}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<!-- 페이징 시작-->
					<tr>
						<td>
							<!-- 페이징 및 블럭 처리 Start--> 
							<!--<span>${pageVo.totalPage}</span> 	 2 -->
							<!--<span>${pageVo.nowBlock}</span> 	 1 -->
							<!--<span>${pageVo.pageStart}</span> 	 1 -->
							<!--<span>${pageVo.pageEnd}</span> 		 3 -->
							<!--<span>${pageVo.nowPage}</span> 		 1 -->
							<!--<span>${pageVo.totalBlock}</span> 	 1 -->
							
							<c:if test="${pageVo.totalPage != 0 }">
								<c:if test="${pageVo.nowBlock > 1 }"> 
									<a href="javascript:block('${pageVo.nowBlock - 1}');">◀</a>
								</c:if>
								
								<c:forEach begin="${pageVo.pageStart}" end="${pageVo.pageEnd}">
									<c:set var="i" value="${i+1}"></c:set>
									<c:if test="${pageVo.pageStart == pageVo.nowPage}">
										<a href="javascript:pageing(${i});">
											<font color="gray">[${i}]</font>
										</a>
									</c:if>
								</c:forEach>
								
								<c:if test="${pageVo.totalBlock > pageVo.nowBlock}">
									<a href="javascript:block(${pageVo.nowBlock}+1);">▶</a>
								</c:if>
							</c:if>
			 			 	<!-- 페이징 및 블럭 처리 End-->
							</td>
						</tr>
				<!-- 페이징 종료-->	
				</table>
				<form name="listFrm" method="post">
					<input type="hidden" name="reload" value="true"> 
					<input type="hidden" name="nowPage" value="1">
				</form>
				<form name="readFrm" method="post">
					<input type="hidden" name="num"> 
					<input type="hidden" name="nowPage" value="${pageVo.nowPage}"> 
					<input type="hidden" name="keyField" value="${pageVo.keyField}"> 
					<input type="hidden" name="keyWord" value="${pageVo.keyWord}">
				</form>
				<c:if test="${authUser != null }">
					<div class="bottom">
						<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
