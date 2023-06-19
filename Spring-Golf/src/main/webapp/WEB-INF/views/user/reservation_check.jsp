<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.LocalDate, java.time.LocalTime, java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> 
<title>Insert title here</title>
<style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        h1 {
            font-size: 32px;
            margin: 20px 0;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #ddd;
            font-weight: bold;
        }
        input[type="date"] {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-right: 10px;
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
		.button,input[type='submit']:hover {
			background-color: rgb(50, 205, 50);
		}
		form {
		  display: flex;
		  align-items: center;
		  justify-content: center;
		}
		
		.search-field {
		  display: inline-block;
		  margin-right: 10px;
		  vertical-align: top;
		}
		
		button[type="submit"] {
		  margin-left: 10px;
		}
		select {
		  width: 150px; /* 변경 가능한 너비 */
		  padding: 10px;
		  font-size: 16px;
		  border: 1px solid #ccc;
		  border-radius: 4px;
		}
		input[type="text"] {
		  width: 250px; /* 변경 가능한 너비 */
		  padding: 10px;
		  font-size: 16px;
		  border: 1px solid #ccc;
		  border-radius: 4px;
		  margin-right: 10px;
		}
		input[type='submit'] {
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
			margin: 0 auto;
			display: block;
		}
		ul {
	  	text-align: center;
	  } 
	  .paging {
	  display: flex;
	  justify-content: center;
	  align-items: center;
	}
	
	.paging a {
	  display: block;
	  margin: 0 5px;
	  padding: 5px 10px;
	  border: 1px solid #ccc;
	  border-radius: 5px;
	  text-decoration: none;
	  color: #333;
	}
	
	.paging a.on {
	  background-color: #ccc;
	  color: #fff;
	}
		
    </style>
</head>
<body>
    <h1>Reservation_check</h1>
    <div id="reservation_check">  
    <form method="get">
   		<input type="hidden" name="user_id" value="${param.user_id}">       
    </form>        
    <div class="container">
		<table>    
		<tr>  		
			<th>No.</th>	
			<th>Location_Name</th>
        	<th>PLAY_DATE</th>
            <th>PLAY_TIME</th> 
            <th>GOLF_NAME & TIME</th>
            <!--
            <th>예약 취소</th>
            <th>예약 변경</th>    
            --> 
        </tr>                      
        <c:set var="currentDate" value="<%= java.time.LocalDate.now() %>" />
        <c:set var="cancellationDeadline" value="${currentDate.plusDays(14)}" />
            <c:forEach items="${dates}" var="date">
                <tr>
                	<td>${date.reservation_no}</td>
                	<td>${date.location_name}</td>
                    <td>${date.play_date}</td>                    
                    <td> 
                    <c:choose>
	                <c:when test="${date.play_time eq '07:00'}">1부(07:00~12:00)</c:when>
	                <c:when test="${date.play_time eq '13:00'}">2부(13:00~18:00)</c:when>
	                <c:otherwise>${date.play_time}</c:otherwise>
           			</c:choose>
            		</td>   
            		<td>${date.golf_name}</td>
            		<!--                      
			        <c:choose>
			        	<c:when test="${date.play_date < cancellationDeadline}">
                            <td></td>
                            <td></td>
                        </c:when>
			        	<c:otherwise>
                    	<td><form method="GET" action="/deleteReservation">
                    	   <input type="hidden" name="user_id" value="${param.user_id}"> 
					       <input type="hidden" name="location_name" value="${date.location_name}">
					       <input type="hidden" name="play_date" value="${date.play_date}">
					       <input type="hidden" name="play_time" value="${date.play_time}">
					       <input type="submit" value="예약 취소">					    
					   </form></td>  	
					   <td><form method="GET" action="/modifyReservation">
						    <input type="hidden" name="reservation_no" value="${date.reservation_no}">
						    <input type="hidden" name="user_id" value="${param.user_id}">
						    <input type="hidden" name="location_name" value="${date.location_name}">
						    <input type="hidden" name="play_date" value="${date.play_date}">
						    <input type="hidden" name="play_time" value="${date.play_time}">
						    <input type="submit" value="예약 변경">
					  </form></td>				
					</c:otherwise>
				</c:choose>		
				-->			                                                              
                </tr>
            </c:forEach>                 
    </table>     
		<br>
		<a href="/logOut" class="button">LOGOUT</a>		
	</div>	
	</div>	
<ul class="paging">
    <c:if test="${paging.prev}">
        <span><a href="${url}?page=${paging.startPage-1}">이전</a></span>
    </c:if>
    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="num">
        <c:choose>
            <c:when test="${paging.cri.page == num}">
                <strong>${num}</strong>
            </c:when>
            <c:otherwise>
                <span><a href="<c:url value='${url}?page=${num}'/>">${num}</a></span>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${paging.next && paging.endPage>0}">
        <span><a href="<c:url value='${url}?page=${paging.endPage+1}'/>">다음</a></span>
    </c:if>
</ul>
</body>
</html>