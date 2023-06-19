<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>새 글 쓰기</title>
    <style>
        <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        h2 {
            font-size: 24px;
            margin: 20px 0;
        }

        form {
            width: 80%;
            margin: 0 auto;
        }

        label {
            display: block;
            font-size: 16px;
            margin-top: 20px;
        }

        input[type="text"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        textarea {
            height: 200px;
        }

        input[type="button"] {
            background-color: #FFEB00;
            border: none;
            color: #4B4F56;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
        }

        input[type="button"]:hover {
            background-color: #FFD500;
        }

        /* 예약 페이지에서 사용할 스타일 */
        #reservation {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px;
        }

        #reservation label {
            font-weight: bold;
            font-size: 18px;
            margin-top: 50px;
        }

        #reservation input[type="date"],
        #reservation select {
            margin-top: 10px;
            font-size: 18px;
            padding: 8px;
            border-radius: 5px;
            border: none;
            box-shadow: 1px 1px 5px #ccc;
            width: 100%;
            max-width: 400px;
        }

        #reservation input[type="button"] {
            margin-top: 50px;
            font-size: 22px;
            padding: 10px 40px;
            border-radius: 10px;
            background-color: #4CAF50;
            color: white;
            transition: background-color 0.3s ease;
        }

        #reservation input[type="button"]:hover {
            background-color: #3e8e41;
        }

        select:disabled {
            opacity: 0.5; /* 비활성화된 select 요소의 투명도를 50%로 설정 */
            pointer-events: none; /* 비활성화된 select 요소의 이벤트를 모두 막음 */
        }
    </style>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
		$(document).ready(function() {
		    document.getElementById('play_date').addEventListener('change', function() {
		        var selectedDate = new Date(this.value);
		        var today = new Date();
		        var minDate = new Date();
		        minDate.setDate(today.getDate() + 28); // 오늘로부터 28일 후로 설정
		        
		        if (selectedDate < minDate) {
		            this.value = '';
		            alert('28일 이후의 날짜만 선택할 수 있습니다.');
		        }
		    });
		
		    // 추가된 로직: 페이지 로드 시 초기 날짜 체크
		    var initialDate = new Date(document.getElementById('play_date').value);
		    var today = new Date();
		    var minDate = new Date();
		    minDate.setDate(today.getDate() + 28); // 오늘로부터 28일 후로 설정
		
		    if (initialDate < minDate) {
		        document.getElementById('play_date').value = '';
		        this.style.backgroundColor = 'red'; // 빨간색 배경 색상 적용
		        alert('28일 이후의 날짜만 선택할 수 있습니다.');
		    }
		});
    function getReservedTime() {
        var play_date = document.getElementById("play_date").value;
        var location_name = document.getElementById("location_name").options[document.getElementById("location_name").selectedIndex].value;
        var selectedDate = new Date(play_date); // 선택된 날짜
        var currentDate = new Date(); // 현재 날짜
        
        currentDate.setDate(currentDate.getDate() + 28);
        currentDate.setHours(0, 0, 0, 0);
        
        // 예약된 시간 가져오기
        $.ajax({
            url: "/get_reserved_time",
            type: "POST",
            data: ({ location_name: location_name, play_date: play_date }),
            async: false,
            success: function (response) {
                var reserved_time = response.reserved_time; // 예약된 시간 배열
                var options = $("#play_time option"); // select 요소 내 option 요소들

                // 예약된 시간을 비활성화 처리
                options.each(function () {
                    var option_value = $(this).val();                    
                    var option_time = new Date(play_date + " " + option_value); // 선택된 날짜와 시간
                    
                    if (reserved_time.includes(option_value) ||  option_time < currentDate) {
                    	console.log(selectedDate);
                    	console.log(currentDate);
                        $(this).prop("disabled", true).css("color", "red");
                    } else {
                        $(this).prop("disabled", false).css("color", "black");
                    }
                });
            },
            error: function (xhr) {
                // 서버 오류 발생시
                alert("예약날짜를 선택하세요.");
            },
        });
    }


function validate() {
    // 입력값 검증
    var play_date = document.getElementById("play_date").value;
    var play_time = document.getElementById("play_time").value;
    var location_name = document.getElementById("location_name").value;

    if (play_date == "") {
        alert("예약일자를 선택해주세요.");
        return;
    }
    if (play_time == "") {
        alert("예약시간을 선택해주세요.");
        return;
    }
    if (location_name == "") {
        alert("골프장을 선택해주세요.");
        return;
    }  
    
    if (play_time === '07:00') {
    	play_time == '1부(07:00~12:00)';
    } else if (play_time === '13:00') {
    	  play_time == '2부(13:00~18:00)';
     }

    // 서버로 예약 요청 보내기
    $.ajax({
        url: "/reserve",
        type: "POST",
        data: $("#reservation").serialize(),
        success: function (response) {
            if (response == "success") {
                // 예약이 성공했을 경우
                alert("예약이 완료되었습니다.");
                        // 예약이 완료되면, 예약 결과 페이지로 이동                         
                        window.location.replace("/reservation_check");
                    } else {
                        // 예약이 실패했을 경우
                        alert("이미 예약된 시간입니다.");             
                    }
                },
                error: function (xhr) {
                    // 서버 오류 발생시
                    alert("서버 오류가 발생했습니다.");
                },
            });
        }   
        
</script>
<body>
  <h2>Reservation</h2>
<form method="post" id="reservation">
<label for="location_name">지역:</label>
<select id="location_name" name="location_name" onchange="getReservedTime()">
    <option value="경기남부">경기남부</option>
    <option value="경기북부">경기북부</option>
    <option value="충청북부">충청북부</option>
    <option value="충청남부">충청남부</option>
    <option value="경상북부">경상북부</option>
    <option value="경상남부">경상남부</option>
    <option value="전라북부">전라북부</option>
    <option value="전라남부">전라남부</option>
    <option value="강원서부">강원서부</option>
    <option value="강원동부">강원동부</option>
    <option value="제주">제주</option>
  </select><br><br>
<label for="play_date">예약일자:</label>
<input type="date" id="play_date" name="play_date" onchange="getReservedTime()"><br><br>

<label for="play_time">예약시간:</label>
<select id="play_time" name="play_time">
	<option value="">--예약 가능한 시간--</option>
	<option value="07:00">1부(07:00~12:00)</option>
	<option value="13:00">2부(13:00~18:00)</option>	
</select><br><br>
<input type="button" value="예약하기" onclick="validate()"><br><br>
</form>
</body>
</html>