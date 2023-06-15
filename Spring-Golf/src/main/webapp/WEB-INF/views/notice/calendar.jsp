<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang='en'>
  <head>
  <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
    <meta charset="UTF-8">    
     <style>
    /* 일요일 날짜 빨간색 */
		.fc-day-sun a {
		  color: red;
		  text-decoration: none;
		}
		/* 토요일 날짜 파란색 */
		.fc-day-sat a {
		  color: blue;
		  text-decoration: none;
		}
		
		a {
			color: #4B4F56;
			text-decoration: none;
		}
		a:hover {
			text-decoration: underline;
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
			margin-left: 380px;
		}
		.button:hover {
			background-color: rgb(50, 205, 50);
		}	
		
   		
   		/* 달력을 감싸는 컨테이너 스타일 */
    .calendar-container {
      width: 700px;
      margin: 0 auto;
    }    

    /* 달력 헤더 버튼 스타일 */
    .fc-toolbar button {
      background-color: transparent;
      color: #ffffff;
    }

    /* 달력 헤더 버튼 호버 스타일 */
    .fc-toolbar button:hover {
      background-color: #3b3f47;
    }

    /* 이벤트 스타일 */
    .fc-event {
      background-color: rgb(144, 238, 144);
      color: #ffffff;
      border: none;
    }
    .fc-sticky{
    	color:#000;
    }
	.fc-col-header {
		background-color: rgb(144, 238, 144);
	}
    /* 이벤트 마우스 호버 스타일 */
    .fc-event:hover {
      background-color: rgb(50, 205, 50);
    }

    /* 이벤트 타이틀 스타일 */
    .fc-event .fc-title {
      font-weight: bold;
    }

    /* 이벤트 상자 스타일 */
    .fc-event .fc-content {
      padding: 5px;
    }          
    
    body {
	  font-family: 'Roboto', sans-serif;
	}
	ul {
	  position: fixed;
	  bottom: 200px;
	  right: 10px;
	}
	li {
	  list-style-type: none;
	  position: relative;
	  padding-left: 30px;
	  margin-bottom: 10px;
	}
	
	li:before {
	  content: "";
	  position: absolute;
	  top: 50%;
	  left: 0;
	  transform: translateY(-50%);
	  width: 12px;
	  height: 12px;
	  border-radius: 50%;
	}
	
	.green {
		background-color: #98FB98;
	}
	.yellow { 
		background-color: #FFD700;
	}
	.blue {
		background-color: #87CEFA;
	}
	.pink {
		background-color: #FFC0CB;
	}
    </style>
    
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@6.1.6/index.global.min.js'></script>
<script src='https://cdn.jsdelivr.net/npm/@fullcalendar/daygrid@6.1.6/index.global.min.js'></script>
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.7/index.global.min.js'></script>

    
<script>
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: 'dayGridMonth',
      locale: 'ko',
      expandRows: true,
      dayMaxEvents: true,
      droppable: true,
      editable: true, // 이벤트 이동 가능하도록 설정
      height: '500px',
      events: '/calendar/events',
      eventDidMount: function(info) {
        var event = info.event;  
        var phone = event.extendedProps.phone;
      },
      eventClick: function(info) {
        var event = info.event;
        var playTime = event.extendedProps.play_time;
        var locationName = event.extendedProps.location_name;
        var reservationNo = event.extendedProps.reservation_no;
        var userId = event.extendedProps.user_id;
        var userNo = event.extendedProps.user_no;
        var phone = event.extendedProps.phone;
        var target = info.el;
         
     // 클릭한 요소가 삭제 버튼인 경우 이벤트 클릭 동작 중지
        if (target.classList.contains('delete-button')) {
          return;
        }
        
        if (playTime === '07:00') {
          playTime = '1부(07:00~12:00)';
        } else if (playTime === '13:00') {
          playTime = '2부(13:00~18:00)';
        }
        
        // Prompt 골프장 선택
        var selectedGolf = prompt(
          'Play Time: ' +
            playTime + '\n' + 
            'Location Name: ' +
            locationName +
            '\n' +
            'Phone: ' + 
            phone + '\n' + 
            '골프장을 선택해주세요.'
        );

        if (selectedGolf) {
          // AJAX 요청으로 서버에 선택한 골프장 정보 전송
          var xhr = new XMLHttpRequest();
          xhr.open('POST', '/calendar/confirm');
          xhr.setRequestHeader('Content-Type', 'application/json');
          xhr.onload = function() {
            if (xhr.status === 200) {
              // 문자 발송 성공              
              alert(
                '선택한 골프장: ' +
                  selectedGolf +
                  '\n문자 발송이 완료되었습니다.'
              );
              
            } else {
              // 문자 발송 실패
              alert('등록되지 않은 번호 입니다. 확인해주세요');
            }
          };

          // 선택한 골프장 정보와 이벤트 정보를 서버로 전송
          var data = JSON.stringify({
        	user_no: userNo,
        	golf_name: selectedGolf,
            eventTitle: event.title,
            play_date: event.startStr,
            play_time: playTime,
            location_name: locationName,
            reservation_no: reservationNo,
            user_id: userId,
            phone: phone
          });
          xhr.send(data);
        }
      },
      dateClick: function(info) {
    	  var clickedDate = info.dateStr;
    	  var user_name = prompt('User Name:');

    	  if (user_name) {
    	    // 이름으로 조회하여 DB에 정보가 있는지 확인
    	    var xhr = new XMLHttpRequest();
    	    xhr.open('POST', '/calendar/check');
    	    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    	    xhr.onload = function() {
    	      if (xhr.status === 200) {
    	        var response = parseInt(xhr.responseText);
    	        if (response != 0) {
    	          // DB에 정보가 있는 경우
    	          // 예약 절차 진행
    	          var location_name = null;
    	          var play_time = null;
    	          console.log(response);
    	          
				  if(response >= 2) {
					  var phone = prompt('Phone Number:'); 
				  }
    	          // Location Name 선택
    	          var locationSelect = document.createElement('select');
    	          locationSelect.innerHTML = `
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
    	          `;
    	          var locationContainer = document.createElement('div');
    	          locationContainer.classList.add('container');
    	          locationContainer.appendChild(document.createTextNode('Location Name: '));
    	          locationContainer.appendChild(locationSelect);
    	          document.body.appendChild(locationContainer);

    	          // Play Time 선택
    	          var playTimeSelect = document.createElement('select');
    	          playTimeSelect.innerHTML = `
    	            <option value="07:00">1부(07:00~12:00)</option>
    	            <option value="13:00">2부(13:00~18:00)</option>
    	            <!-- 다른 시간 옵션들을 추가하세요 -->
    	          `;
    	          var playTimeContainer = document.createElement('div');
    	          playTimeContainer.classList.add('container');
    	          playTimeContainer.appendChild(document.createTextNode('Play Time: '));
    	          playTimeContainer.appendChild(playTimeSelect);
    	          document.body.appendChild(playTimeContainer);

    	          var submitButton = document.createElement('button');
    	          submitButton.textContent = '예약';
    	          document.body.appendChild(submitButton);

    	          submitButton.addEventListener('click', function() {
    	            location_name = locationSelect.value;
    	            play_time = playTimeSelect.value;

    	            if (location_name && play_time) {
    	              // 예약 객체 생성
    	              var reservation = {
    	                user_name: user_name,
    	                location_name: location_name,
    	                play_date: clickedDate,
    	                play_time: play_time,
    	                phone: phone
    	              };

    	              // 서버에 예약 생성 요청
    	              var xhr = new XMLHttpRequest();
    	              xhr.open('POST', '/calendar/reservation');
    	              xhr.setRequestHeader('Content-Type', 'application/json');
    	              xhr.onload = function() {
    	                if (xhr.status === 200) {
    	                	console.log(phone);
    	                  alert('예약이 완료되었습니다.');
    	                  location.reload();
    	                } else {
    	                  alert('예약 생성에 실패했습니다.');
    	                  location.reload();
    	                }
    	              };
    	              xhr.send(JSON.stringify(reservation));
    	            } else {
    	              alert('위치와 플레이 시간을 선택해야 예약을 진행할 수 있습니다.');
    	            }
    	          });

    	        } else {
    	        	  // DB에 정보가 없는 경우
    	        	  // 전화번호 입력 받기
    	        	  var phone = prompt('Phone Number:');
    	        	  if (phone) {
    	        	    // 전화번호가 입력된 경우
    	        	    // 예약 절차 진행

    	        	    // Location Name 선택
    	        	    var locationSelect = document.createElement('select');
    	        	    locationSelect.innerHTML = `
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
    	        	    `;
    	        	    var locationContainer = document.createElement('div');
    	        	    locationContainer.classList.add('container');
    	        	    locationContainer.appendChild(document.createTextNode('Location Name: '));
    	        	    locationContainer.appendChild(locationSelect);
    	        	    document.body.appendChild(locationContainer);

    	        	    // Play Time 선택
    	        	    var playTimeSelect = document.createElement('select');
    	        	    playTimeSelect.innerHTML = `
    	        	      <option value="07:00">1부(07:00~12:00)</option>
    	        	      <option value="13:00">2부(13:00~18:00)</option>
    	        	      <!-- 다른 시간 옵션들을 추가하세요 -->
    	        	    `;
    	        	    var playTimeContainer = document.createElement('div');
    	        	    playTimeContainer.classList.add('container');
    	        	    playTimeContainer.appendChild(document.createTextNode('Play Time: '));
    	        	    playTimeContainer.appendChild(playTimeSelect);
    	        	    document.body.appendChild(playTimeContainer);

    	        	    var submitButton = document.createElement('button');
    	        	    submitButton.textContent = '예약';
    	        	    document.body.appendChild(submitButton);

    	        	    submitButton.addEventListener('click', function() {
    	        	      location_name = locationSelect.value;
    	        	      play_time = playTimeSelect.value;

    	        	      if (location_name && play_time) {
    	        	        // 예약 객체 생성
    	        	        var reservation = {
    	        	          user_name: user_name,
    	        	          location_name: location_name,
    	        	          play_date: clickedDate,
    	        	          play_time: play_time,
    	        	          phone: phone
    	        	        };

    	        	        // 서버에 예약 생성 요청
    	        	        var xhr = new XMLHttpRequest();
    	        	        xhr.open('POST', '/calendar/reservation');
    	        	        xhr.setRequestHeader('Content-Type', 'application/json');
    	        	        xhr.onload = function() {
    	        	          if (xhr.status === 200) {
    	        	        	  console.log(phone);
    	        	            alert('예약이 완료되었습니다.');
    	        	            location.reload();
    	        	          } else {
    	        	        	  console.log(phone);
    	        	            alert('예약 생성에 실패했습니다.');
    	        	            location.reload();
    	        	          }
    	        	        };
    	        	        xhr.send(JSON.stringify(reservation));
    	        	      } else {
    	        	        alert('위치와 플레이 시간을 선택해야 예약을 진행할 수 있습니다.');
    	        	      }
    	        	    });
    	        	  } else {
    	        	    // 전화번호가 입력되지 않은 경우
    	        	    alert('전화번호를 입력해야 예약을 진행할 수 있습니다.');
    	        	  }
    	        	}
    	      } else {
    	        alert('서버 오류가 발생했습니다.');
    	      }
    	    };
    	    xhr.send('user_name=' + encodeURIComponent(user_name));
    	  }
    		},
    		eventDrop: function(info) {
    			  var event = info.event;

    			  // 모달 요소 생성
    			  var modal = document.createElement('div');
    			  modal.classList.add('modal');

    			  // 모달 컨텐츠 생성
    			  var modalContent = document.createElement('div');
    			  modalContent.classList.add('modal-content');

    			  // Location Name 선택
    			  var locationContainer = document.createElement('div');
    			  locationContainer.classList.add('modal-row');
    			  locationContainer.appendChild(document.createTextNode('Location Name: '));
    			  var locationSelect = document.createElement('select');
    			  locationSelect.classList.add('location-select');
    			  locationSelect.innerHTML = `
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
    			  `;
    			  locationContainer.appendChild(locationSelect);
    			  modalContent.appendChild(locationContainer);

    			  // Play Time 선택
    			  var playTimeContainer = document.createElement('div');
    			  playTimeContainer.classList.add('modal-row');
    			  playTimeContainer.appendChild(document.createTextNode('Play Time: '));
    			  var playTimeSelect = document.createElement('select');
    			  playTimeSelect.classList.add('playtime-select');
    			  playTimeSelect.innerHTML = `
    			    <option value="07:00">1부(07:00~12:00)</option>
    			    <option value="13:00">2부(13:00~18:00)</option>
    			    <!-- 다른 시간 옵션들을 추가하세요 -->
    			  `;
    			  playTimeContainer.appendChild(playTimeSelect);
    			  modalContent.appendChild(playTimeContainer);

    			  // 확인 버튼 생성
    			  var confirmButton = document.createElement('button');
    			  confirmButton.innerText = '확인';
    			  confirmButton.classList.add('confirm-button');
    			  confirmButton.addEventListener('click', function() {
    			    // 선택한 값 가져오기
    			    var selectedLocation = locationSelect.value;
    			    var selectedPlayTime = playTimeSelect.value;

    			    // 변경된 정보로 업데이트 요청 생성
    			    var updateEvent = {
    			      user_no: event.extendedProps.user_no,
    			      reservation_no: event.extendedProps.reservation_no,
    			      play_date: moment(event.start).format('YYYY-MM-DD'),
    			      user_id: event.extendedProps.user_id,
    			      play_time: selectedPlayTime,
    			      location_name: selectedLocation,
    			      phone: event.extendedProps.phone
    			    };

    			    // 서버로 이벤트 업데이트 요청 전송
    			    var xhr = new XMLHttpRequest();
    			    xhr.open('POST', '/calendar/update');
    			    xhr.setRequestHeader('Content-Type', 'application/json');
    			    xhr.onload = function() {
    			      if (xhr.status === 200) {
    			        // 성공적으로 업데이트됨
    			        alert('예약 날짜, 플레이 시간, 지역 위치가 변경되었습니다.');
    			        // 모달 닫기
    			        modal.style.display = 'none';
    			        location.reload();
    			      } else {
    			        // 업데이트 실패
    			        alert('예약 변경이 실패했습니다.');
    			        location.reload();
    			      }
    			    };
    			    xhr.send(JSON.stringify(updateEvent));
    			  });
    			  modalContent.appendChild(confirmButton);

    			  // 모달에 컨텐츠 추가
    			  modal.appendChild(modalContent);

    			  // 스타일 적용
    			  modal.style.position = 'fixed';
    			  modal.style.top = '10px';
    			  modal.style.right = '10px';
    			  modal.style.backgroundColor = 'rgb(144, 238, 144)';
    			  modal.style.padding = '10px';

    			  // 모달 표시
    			  document.body.appendChild(modal);
    			  modal.style.display = 'block';
    		},
    		eventMouseEnter: function(info) {
    			  var event = info.event;
    			  
    			  // 삭제 버튼 생성
    			  var deleteButton = document.createElement('button');
    			  deleteButton.innerText = '삭제';
    			  deleteButton.classList.add('delete-button');
    			  deleteButton.addEventListener('click', function(e) {   
    				  
    				e.stopPropagation(); // 클릭 이벤트 전파 중지
    			    if (confirm('이벤트를 삭제하시겠습니까?')) {
    			      // 이벤트 삭제 요청 생성
    			      var deleteEvent = {
    			        reservation_no: event.extendedProps.reservation_no
    			      };
						
    			      console.log("여기");
    			      // 서버로 이벤트 삭제 요청 전송
    			      var xhr = new XMLHttpRequest();
    			      xhr.open('POST', '/calendar/delete');
    			      xhr.setRequestHeader('Content-Type', 'application/json');
    			      xhr.onload = function() {
    			        if (xhr.status === 200) {
    			        	console.log("여길까?");
    			          // 성공적으로 삭제됨    			          
    			          alert('이벤트가 삭제되었습니다.');
    			          // 달력에서 이벤트 제거
    			          info.el.remove();
    			          location.reload();
    			        } else {
    			          // 삭제 실패
    			          alert('이벤트 삭제가 실패했습니다.');
    			          location.reload();
    			        }
    			      };
    			      xhr.send(JSON.stringify(deleteEvent));
    			    }
    			  });

    			  // 삭제 버튼을 이벤트 요소에 추가
    			  info.el.appendChild(deleteButton);
    			},

    			eventMouseLeave: function(info) {
    			  // 마우스가 이벤트에서 벗어나면 삭제 버튼을 제거
    			  var deleteButton = info.el.querySelector('.delete-button');
    			  if (deleteButton) {
    			    deleteButton.remove();
    			  }
    			}
    		});   		 

    calendar.render();
  });
</script>
</head>
<body>
    <div id='calendar' style="width:50%; margin:0 auto;"></div>    
    <br>    
    <a href="../list" class="button">GO USERLIST</a>
    <ul>
    	<li class="green">유저 및 관리자 예약상태</li>
    	<li class="yellow">유저 및 관리자 예약변경상태</li>
    	<li class="blue">관리자 유저에게 문자발송상태</li>
    	<li class="pink">비회원 예약 상태</li>
    </ul>
  </body>
</html>