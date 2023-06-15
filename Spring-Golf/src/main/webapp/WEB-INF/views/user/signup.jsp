<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html lang="ko">
<head>

	  <meta charset="UTF-8">
	  <title>회원가입</title>
	<style>
    body {
        font-family: "Noto Sans KR", sans-serif;
        background-color: #F7F8FA;
        color: #444444;
        font-size: 16px;
    }

    h1 {
        font-weight: bold;
        font-size: 36px;
        text-align: center;
        margin-top: 80px;
        margin-bottom: 40px;
    }

    form {
        margin: 0 auto;
        padding: 40px;
        max-width: 600px;
        background-color: #FFFFFF;
        border-radius: 8px;
        box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
    }

    label {
        display: inline-block;
        margin-bottom: 8px;
        font-size: 16px;
        font-weight: bold;
    }

    input[type="text"],
    input[type="password"],    
    select {
        display: inline-block;
        width: 100%;
        padding: 12px;
        margin-bottom: 24px;
        font-size: 16px;
        font-weight: bold;
        border: 1px solid #DADCE0;
        border-radius: 8px;
        box-sizing: border-box;
    }  

    select {
        appearance: none;        
        background-size: 24px 24px;
        -webkit-appearance: none;
        -moz-appearance: none;
    }

    button[id="register"] {
        display: block;
        width: 150px;
        padding: 16px;
        margin: auto;
        margin-top: 32px;
        background-color: rgb(144, 238, 144);
        color: #000;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s;
    }

    button[id="register"]:hover {
        background-color: rgb(50, 205, 50);
    }

    #checkUsernameBtn {
        display: inline-block;
        padding: 12px;
        margin-left: 86px;
        background-color: rgb(144, 238, 144);
        color: #000;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s;
    }

    #checkUsernameBtn:hover {
        background-color: rgb(50, 205, 50);
    }
    
     #checkUsernickname {
        display: inline-block;
        padding: 12px;
        margin-left: 86px;
        background-color: rgb(144, 238, 144);
        color: #000;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s;
    }

    #checkUsernickname:hover {
        background-color: rgb(50, 205, 50);
    }
    #phone, .selectSellPhone, #phone_last{
   width:100px;
   height:35px;
   border: 2px solid #333;
   border-radius: 5px;
   padding : 5px;
   display: inline-block;
   box-sizing: border-box;
   }
   .form-label{
   display: block;
   }
   input[id=user_id],
   input[id=user_nickname] {
   display: inline-block;
   width: 70%;
   }
   
   #findAddressBtn {
        display: inline-block;
        padding: 7px;
        margin-left: 5px;
        background-color: #FFD600;
        color: #FFFFFF;
        font-size: 16px;
        font-weight: bold;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s;
    }

    #findAddressBtn:hover {
        background-color: #FFEB00;
    }
    #taddress{
    	display: inline-block;
    }
    #postcode{
    display: inline-block;
        width: 30%;
        padding: 12px;
        margin-bottom: 24px;
        font-size: 16px;
        font-weight: bold;
        border: 1px solid #DADCE0;
        border-radius: 8px;
        box-sizing: border-box;
    }
    
</style>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(function() {		
		var exist = false;
		//중복확인 버튼 클릭시
		$('#checkUsernameBtn').on('click', function() {
			//입력된 아이디를 가져옴
			var id = $('#user_id').val().toLowerCase();
			var email = $('#email').val();
			//아이디가 입력되지 않았을 경우			
			var regex = /^[A-Za-z0-9_]+$/; // 영문 대/소문자, 숫자, 특수문자 _ 만 허용하는 정규식 패턴
			

			if(!id) {
				alert('아이디를 입력해주세요.');
				return;
			}
			
			if(!regex.test(id)) {
				alert('아이디는 영문 대/소문자, 숫자, 특수문자(_)로만 이루어져야 합니다.');
				return;
			} else if(id.length < 4 || id.length > 12) {
				alert('아이디는 4글자 이상 12글자 이하여야됩니다.');
				return;
			}			
			//AJAX 요청 보내기
			$.ajax({
				type: 'POST',	//타입확인
				url: '/checkIdDuplicate',	//url확인
				data: {id: id},
				dataType: "json",
				success: function(result) {
					console.log(result);
					if (result.result === "notExist") {
			            alert('사용 가능한 아이디입니다.');
			        } else {
			        	console.log(result.reuslt);
			           alert('이미 사용중인 아이디입니다.');
			        } 
				},
				error: function() {
					alert('서버 오류가 발생했습니다.');
				}
			});
		});
	});
	
	$(function() {		
		var exist = false;
	$('#checkUsernickname').on('click', function() {
		//입력된 아이디를 가져옴
		var nickname = $('#user_nickname').val().toLowerCase();		
		

		if(!nickname) {
			alert('닉네임을 입력해주세요.');
			return;
		}		
		//AJAX 요청 보내기
		$.ajax({
			type: 'POST',	//타입확인
			url: '/checknicknameDuplicate',	//url확인
			data: {nickname: nickname},
			dataType: "json",
			success: function(result) {
				console.log(result);
				if (result.result === "notExist") {
		            alert('사용 가능한 닉네임입니다.');
		        } else {
		        	console.log(result.reuslt);
		           alert('이미 사용중인 닉네임입니다.');
		        } 
			},
			error: function() {
				alert('서버 오류가 발생했습니다.');
			}
		});
	});
});
	//입력 없을시 알림
	function validate() {
		var userId = $('#user_id').val();
		var userName = $('#user_name').val();
		var userPwd = $('#user_pwd').val();
		var userNickname = $('#user_nickname').val();
		var phone = $('#phone').val();
		
		
		/*if(!emailRegex.test(email)) {
			alert('이메일의 형태가 아닙니다.');
			return;
		} */
		if(!userId) {
			alert('아이디를 입력해주세요.');
			return;
		}else if(!userName) {
			alert('이름을 입력해주세요.');
			return;
		}else if(!userPwd) {
			alert('비밀번호를 입력해주세요.');
			return;
		}else if(!userNickname){
			alert('닉네임을 입력해주세요.');
			return;
		}else if(!phone) {
			alert('핸드폰번호를 입력해주세요.');
			return;
		}else {
			$('#registerForm').attr("action", "/signupOk");
			alert("회원가입이 성공하였습니다.");
			$('#registerForm').submit();
		}
	} 
</script>
<body>
  <h1>회원가입</h1>
  <form id="registerForm" method="post">
    <div>
      <label class ="form-label">아이디:</label>
      <input type="text" id="user_id" name="user_id">
      <button type="button" id="checkUsernameBtn">중복확인</button>
    </div>
    <div>
      <label class ="form-label">이름:</label>
      <input type="text" id="user_name" name="user_name">
    </div>
    <div>
      <label class = form-label">비밀번호:</label>
      <input type="password" id="user_pwd" name="user_pwd">
    </div>
    <div>
      <label class ="form-label">유저닉네임:</label>
      <input type="text" id="user_nickname" name="user_nickname">
      <button type="button" id="checkUsernickname">중복확인</button>
    </div>    
    <div>
	  <label class="form-label">핸드폰번호:</label>
	  <input type="text" id="phone" name="phone" style="width:200px;" placeholder="숫자만 입력하세요">
	</div>
    <button type="button" id="register" onclick="validate()">회원가입</button>
  </form>
</body>
</html>