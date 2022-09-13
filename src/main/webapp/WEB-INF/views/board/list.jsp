<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<title>Mysite</title>
<style>

	#table-css{
		margin: 0 auto;
	}

	#td-css {
		width: 253px;
		display: flex;
		flex-wrap: nowrap;
		align-items: stretch;
		justify-content: space-around;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#datepicker").datepicker({
			dateFormat: 'yy-mm-dd' //Input Display Format 변경
		 });
		$("#datepicker").hide();
		//초기값을 오늘 날짜로 설정
		$('#datepicker').datepicker('setDate', 'today');

		$('#keyField').focusout(function() {
			if (this.value == "reg_date") {
				$("#input_keyWord").hide();
				$("#datepicker").show();
			}
		});

	});

	function pageing(page) {
		document.readFrm.nowPage.value = page;
		if ('${kewWord}' != "") {
			document.readFrm.keyWord.value = '${kewWord}';
			document.readFrm.keyField.value = '${keyField}';
		}
		document.readFrm.submit();
	}

	function block(value) {
		var param = '${pagePerBlock}';
		document.readFrm.nowPage.value = parseInt(param) * (value - 1) + 1; // 5 x (2 - 1) + 1 = 6
		if ('${kewWord}' != "") {
			document.readFrm.keyWord.value = '${kewWord}';
			document.readFrm.keyField.value = '${keyField}';
		}
		document.readFrm.submit();
	}
	function check() { // 검색어 입력 여부 확인
		if (document.searchFrm.keyField.value == "reg_date") {
			document.searchFrm.keyWord.value = $("#datepicker").val();
			alert( $("#datepicker").val());
		}else{
			if (document.searchFrm.keyWord.value == "") {
				alert("검색어를 입력하세요.");
				document.searchFrm.keyWord.focus();
				return;
			}
		}
		document.searchFrm.action = "/mysite/board?a=list";
		document.searchFrm.submit();
	}

	function read(num) {
		document.readFrm.num.value = num;
		document.readFrm.action = "/mysite/board";
		document.readFrm.submit();
	}
</script>
</head>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form id="searchFrm" name="searchFrm" method="post" action="/mysite/board">
					<table id="table-css" cellpadding="4" cellspacing="0">
						<tr>
							<td align="center" valign="bottom" id="td-css">
							<select name="keyField" size="1" id="keyField">
								<option value="name">글쓴이</option>
								<option value="reg_date">작성일</option>
								<option value="title">제 목</option>
								<option value="content">내 용</option>
								<option value="attach">첨 부</option>
							</select>
							<input size="16" type="text" id="datepicker" style="height: 10px; border-color:black; ">
							<input size="16" name="keyWord" value="" id="input_keyWord">
							<input type="button" value="찾기" onClick="javascript:check()">
							<input type="hidden" name="nowPage" value="1"></td>
						</tr>
					</table>
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
					<c:forEach var="vo" items="${list}" begin="0" step="1" end="${numPerPage}">
						<tr>
							<td>${vo.no}</td>
							<td><a href="/mysite/board?a=read&no=${vo.no}">${vo.title}</a></td>
							<td>${vo.user_name}</td>
							<td>${vo.hit}</td>
							<td>${vo.reg_date}</td>
							<td><c:if test="${authUser.no == vo.user_no}">
									<a href="/mysite/board?a=delete&no=${vo.no}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
				<ul>
					<c:if test="${not empty totalPage}">
							<c:if test="${nowBlock > 1}">
								<a href="javascript:block(${nowBlock-1});">이전</a>
							</c:if>&nbsp;   
	  		    			  		
    					<c:forEach var="cur" begin="${pageStart}" end="${pageEnd}">
								<a href="javascript:pageing(${cur});">[${cur}] </a>
							</c:forEach>&nbsp;
			  		    			  		
   						<c:if test="${totalBlock > nowBlock}">
								<a href="javascript:block(${nowBlock+1});">다음</a>
							</c:if>&nbsp;
    			    </c:if>
				</ul>
				</div>				
				<c:if test="${authUser != null }">
					<div class="bottom">
						<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
			
			<form name="readFrm" method="get">
				<input type="hidden" name="a" value="list"> 
				<input type="hidden" name="nowPage" value="${nowPage}"> 
				<input type="hidden" name="keyField" value="${keyField}"> 
				<input type="hidden" name="keyWord" value="${keyWord}">
			</form>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		