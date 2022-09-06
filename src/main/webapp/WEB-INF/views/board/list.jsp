<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.javaex.vo.BoardVo"%>
<%@page import="java.util.*"%>
<jsp:useBean id="bdImpl" class="com.javaex.dao.BoardDaoImpl" />
<%	
	  request.setCharacterEncoding("UTF-8");
	  
      int totalRecord=0; //전체레코드수
	  int numPerPage=10; // 페이지당 레코드 수 
	  int pagePerBlock=10; //블럭당 페이지수 
	  
	  int totalPage=0; //전체 페이지 수
	  int totalBlock=0;  //전체 블럭수 

	  int nowPage=1; // 현재페이지
	  int nowBlock=1;  //현재블럭
	  
	  int start=0; //디비의 select 시작번호
	  int end=10; //시작번호로 부터 가져올 select 갯수
	  
	  int listSize=0; //현재 읽어온 게시물의 수

	String keyWord = "", keyField = "";
	List<BoardVo> vlist = null;
	if (request.getParameter("keyWord") != null) {
		keyWord = request.getParameter("keyWord");
		keyField = request.getParameter("keyField");
	}
	if (request.getParameter("reload") != null){
		if(request.getParameter("reload").equals("true")) {
			keyWord = "";
			keyField = "";
		}
	}
	
	if (request.getParameter("nowPage") != null) {
		nowPage = Integer.parseInt(request.getParameter("nowPage"));
	}
	 start = (nowPage * numPerPage)-numPerPage;
	 end = numPerPage;
	 
	totalRecord = bdImpl.getTotalCount(keyField, keyWord);			//전체 게시물 건수
	totalPage = (int)Math.ceil((double)totalRecord / numPerPage);  	//전체페이지수
	nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock); 		//현재블럭 계산
	  
	totalBlock = (int)Math.ceil((double)totalPage / pagePerBlock);  //전체블럭계산
	
	vlist = bdImpl.getBoardList(keyField, keyWord, start, end);
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
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
		 document.readFrm.nowPage.value=<%=pagePerBlock%>*(value-1)+1;
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
				</table>
				<div class="pager">
					<!-- 페이징 및 블럭 처리 Start--> 
					<%
   				 	int pageStart = (nowBlock -1)*pagePerBlock + 1 ; //하단 페이지 시작번호
   				  	int pageEnd = ((pageStart + pagePerBlock ) <= totalPage) ?  (pageStart + pagePerBlock): totalPage+1; 
   				  	//하단 페이지 끝번호
   				  	if(totalPage !=0){
    			  		if (nowBlock > 1) {%>
    			  			<a style="text-decoration: none;"  href="javascript:block('<%=nowBlock-1%>')">◀</a><%}%>&nbsp; 
    			  		<%for ( ; pageStart < pageEnd; pageStart++){%>
     			     		<a style="text-decoration: none;" href="javascript:pageing('<%=pageStart %>')"> 
     						<%if(pageStart==nowPage) {%><font color="blue"> <%}%>
     						[<%=pageStart %>] 
     						<%if(pageStart==nowPage) {%></font> <%}%></a> 
    					<%}//for%>&nbsp; 
    					<%if (totalBlock > nowBlock ) {%>
    					<a style="text-decoration: none;" href="javascript:block('<%=nowBlock+1%>')">▶</a>
    				<%}%>&nbsp;  
   				<%}%>
 				<!-- 페이징 및 블럭 처리 End-->
				</div>
				<form name="listFrm" method="post">
					<input type="hidden" name="a" value="list">
					<input type="hidden" name="reload" value="true"> 
					<input type="hidden" name="nowPage" value="1">
				</form>
				<form name="readFrm" method="get">
					<input type="hidden" name="a" value="list">
					<input type="hidden" name="num"> 
					<input type="hidden" name="nowPage" value="<%=nowPage%>"> 
					<input type="hidden" name="keyField" value="<%=keyField%>"> 
					<input type="hidden" name="keyWord" value="<%=keyWord%>">
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
		
