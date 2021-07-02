<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">

$(function () {
	$('#btnBoard').click(function() {
		$.ajax({
			type : "get",
			url : '../restapi/boardView.do',
			data : {num : $('#num').val()}, // 파라미터는 게시물의 일련번호
			contentType : "text/html;charset:utf-8",
			dataType : "json",
			success : sucCallBack,
			error : errCallBack
		
		});	
	});
	
	$('#btnBoard').trigger('click');
});

function sucCallBack(resData) {
	
	// 콜백 받은 데이터를 각각의 td태그에 삽입한다.
	let tableData = "";
	$('#td1').html(resData.num);
	$('#td2').html(resData.id);
	$('#td3').html(resData.postdate);
	$('#td4').html(resData.visitcount);
	$('#td5').html(resData.title);
	$('#td6').html(resData.content);

}

function errCallBack(errData) {
	console.log(errData.status+":"+errData.statusText);
}

</script>
</head>
<body>
<div class="container">
	<h2>게시판 API 활용하여 내용 출력하기</h2>
	<table class="table table-bordered">
		<tr>
			<th>번호</th><td id="td1"></td>
			<th>아이디</th><td id="td2"></td>
		</tr>
		<tr>
			<th>작성일</th><td id="td3"></td>
			<th>조회수</th><td id="td4"></td>
		</tr>
		<tr>
			<th>제목</th><td colspan="3" id="td5"></td>
		</tr>
		<tr>
			<th>내용</th><td colspan="3" id="td6"></td>
		</tr>
		
	</table>
	<div>
		<input type="text" id="num" value="50"/>
		<input type="button" id="btnBoard" value="내용 불러오기"/>
	</div>
</div>

</body>
</html>























