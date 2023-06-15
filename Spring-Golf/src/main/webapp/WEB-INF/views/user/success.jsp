<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<style>
		  body {
	  font-family: 'Noto Sans KR', sans-serif;
	  background-color: #F7F8F9;
	  color: #333;
	  padding: 20px;
	  margin: 0;
	}
	
	/* h1 스타일 */
	h1 {
	  font-size: 24px;
	  font-weight: 500;
	  margin-bottom: 20px;
	  color: #2D2D2D;
	}
	
	/* 링크 스타일 */
	a {
	  color: #4C8BF5;
	  text-decoration: none;
	  border-bottom: 1px solid #4C8BF5;
	  transition: all 0.3s ease-in-out;
	}
	
	/* 링크 hover 스타일 */
	a:hover {
	  text-decoration: none;
	  border-bottom-color: #2D5CFF;
	}
	
	/* 가입완료 메시지 스타일 */
	.message {
	  width: 300px;
	  margin: 100px auto;
	  padding: 50px;
	  background-color: #FFFFFF;
	  box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
	  text-align: center;
	}
	
	/* 로그인 링크 스타일 */
	.login-link {
	  display: block;
	  margin-top: 20px;
	  font-size: 16px;
	  font-weight: 500;
	  color: #4C8BF5;
	  text-decoration: none;
	  border-bottom: 1px solid #4C8BF5;
	  transition: all 0.3s ease-in-out;
	}
	
	/* 로그인 링크 hover 스타일 */
	.login-link:hover {
	  text-decoration: none;
	  border-bottom-color: #2D5CFF;
	}
 </style>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
 <div class="message">
    <h1>가입 완료!</h1>
    <a href="/login" class="login-link">로그인</a>
  </div>
</body>
</html>