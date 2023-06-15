<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인</title>
<style>
		body {
			background-color: #F9F9F9;
			font-family: 'Noto Sans KR', sans-serif;
			height: 100vh;
			display: flex;
			align-items: center;
			justify-content: center;
		}
		form {
			display: flex;
			flex-direction: column;
			align-items: center;		
			padding: 20px;
			border: 1px solid #ccc;
			border-radius: 5px;
			box-shadow: 2px 2px 5px #ccc;
			width: 360px;
			margin: auto;	
		}
		h2 {
			font-size: 1.8rem;
			font-weight: bold;
			margin-bottom: 20px;
		}
		input {
			width: 300px;
			margin-bottom: 10px;
			padding: 10px;
			border: none;
			border-radius: 5px;
			box-shadow: 1px 1px 3px #ccc;
		}
		button {
			margin-top: 20px;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			background-color: rgb(144, 238, 144);
			color: #333;
			cursor: pointer;
			font-size: 1.2rem;
			font-weight: bold;
			transition: background-color 0.3s ease;
		}
		button:hover {
			background-color: rgb(50, 205, 50);
		}
		button:focus {
			outline: none;
		}		
		.login_warn {
		  color: red;
		  animation: blink 1s infinite;
		}
		
		@keyframes blink {
		  0% { opacity: 1.0; }
		  50% { opacity: 0.0; }
		  100% { opacity: 1.0; }
		}
		.signup {
			margin-top: 20px;
			font-size: 1rem;
			color: #666;
			cursor: pointer;
			transition: color 0.3s ease;
		}
		.signup:hover {
			color: #333;
		}
		
		
</style>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
		function login() {
			var loginForm = document.loginForm;
			
			var user_id = $("#user_id").val();
			var user_pwd = $("#user_pwd").val();				
			
			
			if(user_id =='' && user_pwd =='') {
				alert('아이디와 비밀번호를 입력하세요');				
			} else if(user_id =='') {
				alert('아이디를 입력하세요.');
			} else if(user_pwd =='') {
				alert('비밀번호를 입력하세요.');
			} else {			
				$("#loginForm").attr("action", "/loginCheck");
				$("#loginForm").submit();
				return true;
			}	
			
		}				
	
</script>
</head>
<body>
	<form id="loginForm" method="post">
		<h2>골프장</h2>
		<input type="text" name="user_id" id="user_id" placeholder="ID를 입력해주세요."/>
		<input type="password" name="user_pwd" id="user_pwd" placeholder="비밀번호를 입력해주세요."/>
		<button type="button" onclick="login()">로그인</button>		
		<div class="signup" onclick="location.href='./signup'">회원가입</div>
		<c:choose>
			<c:when test="${user == null}"> <!-- session에 담은  login값 불러오기 -->
				<div class="login_warn">${msg}</div>
			</c:when>
		</c:choose>
		<!-- if는 한가지 경우 when은 여러가지 경우일때 사용한다. -->
	</form>
</body>
</html>