<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
	});

</script>

<body>
	<form action="/loginGo" method="post"> <!-- #2 post를 필수로 잡아주어야 한다 그래야지 시큐리티에서 인증을 시도한다. -->
		<input type="text" id="username" name="username"/>
		<input type="password" id="password" name="password"/>
		<input type="submit" value="Login!!!" />
		
	</form>
	
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
	    <font color="red">
	        <p>Your login attempt was not successful due to <br/>
	            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
	        <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
	    </font>
	</c:if>	
	
	<c:if test="${not empty param.logout}">
		<font color="green">
			logout OK!
		</font>
	</c:if>

</body>
</html>