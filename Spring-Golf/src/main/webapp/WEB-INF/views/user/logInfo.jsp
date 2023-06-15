<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ�� ����</title>
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
        border-top: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }
    th, td {
        padding: 10px;
        border: none;
        border-bottom: 1px solid #ccc;
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
    .button:hover {
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
        width: 150px; /* ���� ������ �ʺ� */
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    input[type="text"] {
        width: 250px; /* ���� ������ �ʺ� */
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-right: 10px;
    }		
    
    .saveRemarks {
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
    }

    .saveRemarks:hover {
        background-color: rgb(50, 205, 50);
    }

    .saveRemarks:focus {
        outline: none;
    }
</style>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
	  $('.count').click(function() {
	    var user_no = $(this).data('user');
	    $.ajax({
	      url: '/reservationDates',
	      data: { user_no: user_no },
	      success: function(data) {
	        var html = '<table><tr><th>Play Date</th><th>Play Time</th><th>Golf Course</th></tr>';
	        for (var i = 0; i < data.length; i++) {
	          html += '<tr><td>' + data[i].play_date + '</td><td>' + data[i].play_time + '</td><td>' + data[i].golf_name + '</td></tr>';
	        }
	        html += '</table>';
	        $('#reservationDates').html(html);
	      },
	      error: function() {
	        alert('������');
	      }
	    });
	  });
	});
	
	
$(document).ready(function() {
    $('.saveRemarks').click(function() {
        var user_no = $(this).data('user');
        var text = $('#remarks_' + user_no).val();
        console.log(user_no);
        // ��� ���� ������ ����
        $.ajax({
            url: '/saveRemarks',
            method: 'POST',
            data: { user_n9: user_no, text: text },
            success: function(response) {
                console.log(user_no);
                // ���������� ����Ǿ��� ���� ó��
                alert('��� ����Ǿ����ϴ�.');
                // �߰����� �۾� ���� ����

                // ��� ������Ʈ�� �����͸� �ٽ� ������
                $.ajax({
                    url: '/getUserData', // getUserData �޼��带 ����Ͽ� ������ ������
                    method: 'GET',
                    data: { user_no: user_no },
                    success: function(data) {
                    	console.log(text)
                        // ������ �����͸� �ش� �࿡ ������Ʈ
                        $('#remarks_' + user_no).val(data.text);
                    },
                    error: function() {
                        alert('��� �����͸� �������� �߿� ������ �߻��߽��ϴ�.');
                    }
                });
            },
            error: function() {
                alert('��� �����ϴ� �߿� ������ �߻��߽��ϴ�.');
            }
        });
    });
});
</script>
<body>
<h1>User List</h1>
    <form method="get">
   		<input type="hidden" name="user_no" value="${param.user_no}">
        <input type="date" name="start" value="${start}" onchange="this.form.submit()">
        <input type="date" name="end" value="${end}" onchange="this.form.submit()">
    </form>
	<div class="container">
		<table>   
		<tr> 
        	<th>No.</th>
            <th>UserName</th>
            <th>UserNickName</th>                      
            <th>Day</th>
            <th>Total</th>
            <th>���</th>  
        </tr>       
            	<c:forEach items="${user}" var="user">
            	<c:choose>
            	<c:when test="${not empty user.text}">
                <tr>                	
                    <td>${user.user_no}</td>
                    <td>${user.user_name}</td>
                    <td>${user.user_nickname}</td>    
                    <td><a href="/reservationDates" class="count" data-user="${user.user_no}">${user.period_count}</a></td>                    
                    <td>${user.total_count}</td>
                    <td>
					    <input type="text" id="remarks_${user.user_no}" value="${user.text}" placeholder="��� �Է�">
					    <button class="saveRemarks" data-user="${user.user_no}">����</button>
					</td>                                                      
                </tr>
                </c:when>
                <c:otherwise>
                <tr>                	
                    <td>${user.user_no}</td>
                    <td>${user.user_name}</td>
                    <td>${user.user_nickname}</td>    
                    <td><a href="/reservationDates" class="count" data-user="${user.user_no}">${user.period_count}</a></td>                    
                    <td>${user.total_count}</td>
                    <td>
					    <input type="text" id="remarks_${user.user_no}" value="${user.text}" placeholder="��� �Է�">
					    <button class="saveRemarks" data-user="${user.user_no}">Ȯ��</button>
					</td>                                                      
                </tr>	
                </c:otherwise>
                </c:choose>
            </c:forEach>                 
    </table>     
		<br>
		<a href="../list" class="button">GO USERLIST</a>		
	</div>
</body>
</html>