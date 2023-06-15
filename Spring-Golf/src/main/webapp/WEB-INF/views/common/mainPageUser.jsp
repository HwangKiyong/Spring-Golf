<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="EUC-KR">
	<title>로그인 성공</title>
	<style>
		body {
			background-color: #F9F9F9;
			font-family: 'Noto Sans KR', sans-serif;
			font-size: 16px;
			line-height: 1.5;
		}
		h1 {
			color: #333333;
			font-size: 36px;
			margin-top: 50px;
			text-align: center;
		}
		#welcome {
			color: #333333;
			font-size: 24px;
			margin-top: 20px;
			text-align: center;
		}
		a {
			color: #4B4F56;
			text-decoration: none;
		}
		a:hover {
			text-decoration: underline;
		}
		.container {
			margin: 0 auto;
			max-width: 500px;
			padding: 20px;
		}
		.button {
			background-color: rgb(144, 238, 144);
			border: none;
			border-radius: 4px;
			color: #4B4F56;
			cursor: pointer;
			font-size: 16px;
			font-weight: bold;
			padding: 10px 20px;
			text-align: center;
			text-decoration: none;
			text-transform: uppercase;
			transition: background-color 0.3s ease-in-out;
			margin-left: 186px;
		}
		.button:hover {
			background-color: rgb(50, 205, 50);
		}		
	</style>
</head>
<body>
	<div class="container">
		<h1>로그인 성공</h1>
		<c:choose>
			<c:when test="${not empty user}">
				<div id="welcome">${user.user_id} 님 환영합니다.</div>		
				<br>
				<div><a href="/logOut" class="button">로그아웃</a></div>	
			</c:when>
			<c:otherwise>
				<div><a href="/login" class="button">로그인</a></div>
			</c:otherwise>		
		</c:choose>	
		<br>
		<a href="/reservePage" class="button">RESERVATION</a>	
		<br><br>
		<a href="/reservation_check" class="button">예약확인</a>
	</div>
</body>
</html>