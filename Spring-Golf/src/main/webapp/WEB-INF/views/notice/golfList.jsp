<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">    
    <title>Golf List</title>
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
	        table-layout: fixed;
	        border: none; /* 추가된 부분 */
	    }
         th, td {
	        padding: 10px;
	        text-align: center;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        border: none; /* 추가된 부분 */
	        border-bottom: 1px solid #ccc; /* 추가된 부분 */
	    }
        th {
            background-color: #ddd;
            font-weight: bold;
        }
        td a {
            color: #0366d6;
            text-decoration: none;
        }
        td a:hover {
            text-decoration: underline;
        }
        input[type="date"] {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-right: 10px;
        }
        
        button {
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
		ul.paging {
            display: flex;
            justify-content: center;
            align-items: center;
            list-style: none;
            padding: 0;
            margin-top: 20px;
        }
        ul.paging li {
            margin: 0 5px;
        }
        ul.paging a {
            display: block;
            padding: 5px 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-decoration: none;
            color: #333;
        }
        ul.paging a.on {
            background-color: #ddd;
            color: #333;
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
    <h1>Golf List</h1>    
    <table> 
    	<c:choose>    
    	<c:when test="${not empty searchResults}">   
        <tr>
            <th>No.</th>
            <th>UserId</th>
            <th>UserNickname</th>
            <th>UserName</th>            
            <th>Phone</th>       
        </tr>
        </c:when>
        <c:otherwise>
        	<th>No.</th>
            <th>UserId</th>
            <th>UserNickname</th>
            <th>UserName</th>            
            <th>Phone</th>                       
        </c:otherwise>
        </c:choose>
        <c:choose>
        	<c:when test="${not empty searchResults}">        	
            <c:forEach items="${searchResults}" var="golf">
                <tr>
                    <td><a href="/logInfo?user_no=${empty golf.user_no ? '비회원' : golf.user_no}">${golf.user_no}</a></td>
                    <td>${empty golf.user_id ? '비회원' : golf.user_id}</td>
                    <td>${golf.user_nickname}</td>    
                    <td>${golf.user_name}</td>                   
                    <td>${golf.phone}</td>                    
                </tr>
            </c:forEach>
            </c:when>
            <c:otherwise>
            	<c:forEach items="${golfList}" var="golf">
                <tr>
                    <td><a href="/logInfo?user_no=${empty golf.user_no ? '비회원' : golf.user_no}">${golf.user_no}</a></td>
                    <td>${empty golf.user_id ? '비회원' : golf.user_id}</td>
                    <td>${golf.user_nickname}</td>    
                    <td>${golf.user_name}</td>                   
                    <td>${golf.phone}</td>                 
                </tr>
            </c:forEach>
            </c:otherwise>
            </c:choose>        
    </table>     
<br><br>
<form action="/search" method="get" style="text-align: center;">
	    <div class="search-field">      
	      <select id="type" name="searchCondition">        
	        <option value="user_id">유저아이디</option>
	        <option value="user_name">유저이름</option>                
	      </select>
	    </div>
	    <div class="search-field">
	      <input type="text" id="keyword" name="searchKeyword" placeholder="검색어를 입력해주세요" value="${param.searchKeyword}">
	    </div>
	    <button type="submit">검색</button><br>	     
</form>

<ul class="paging">
    <c:if test="${paging.prev}">
        <span><a href="${url}?page=${paging.startPage-1}&user_id=${param.user_id}&user_name=${param.user_name}&searchCondition=${param.searchCondition}&searchKeyword=${param.searchKeyword}">이전</a></span>
    </c:if>
    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="num">
        <c:choose>
            <c:when test="${paging.cri.page == num}">
                <strong>${num}</strong>
            </c:when>
            <c:otherwise>
                <span><a href="<c:url value='${url}?page=${num}&user_id=${param.user_id}&user_name=${param.user_name}&searchCondition=${param.searchCondition}&searchKeyword=${param.searchKeyword}'/>">${num}</a></span>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${paging.next}">
        <span><a href="<c:url value='${url}?page=${paging.endPage+1}&user_id=${param.user_id}&user_name=${param.user_name}&searchCondition=${param.searchCondition}&searchKeyword=${param.searchKeyword}'/>">다음</a></span>
    </c:if>
</ul>
<a href="../calendar" class="button">GO CALENDAR</a>
</body>
</html>