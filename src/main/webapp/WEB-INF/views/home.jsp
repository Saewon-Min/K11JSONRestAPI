<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

</head>
<body>

	<h2>JSON 사용하기</h2>
	<li>
		<a href="./jsonUse/jsonView.do" target="_blank">
			@ResponseBody 어노테이션을 이용한 JSON데이터 보기
		</a>
	
	</li>


	<h2>RestAPI 만들어보기</h2>
	<li>
		<a href="./restapi/boardList.do?nowPage=1" target="_blank">
			리스트보기(페이지별)
		</a>
	</li>
	<li>
		<a href="./restapi/boardList.do?searchField=title&searchTxt=107+109" target="_blank">
			검색결과보기(공백으로 구분)
		</a>
	</li>
	<li>
		<a href="./restapi/boardView.do?num=5" target="_blank">
			내용보기
		</a>
	</li>

	<h2>회원 리스트 JSON으로 출력하기</h2>
	<a href="./android/memberObject.do">
	회원 리스트 가져오기(객체형태)</a>
	<br />
	<a href="./android/memberList.do">
	회원 리스트 가져오기(배열형태)</a>



</body>
</html>
