<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Book added</title>
</head>
<body>
<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Books</h1>
				<h2> Book added correctly.</h2>
			</div>
			
			<sec:authorize access="!isAuthenticated()">
				<p align="right">
					<a href="/webstore/login" class="btn btn-success"> <span
						class="glyphicon glyphicon-user" /></span> LOGIN
					</a>
				</p>
			</sec:authorize>
			
			<sec:authorize access="isAuthenticated()">
				<p align="right">
					<a href = "<c:url value="/j_spring_security_logout" />" class="btn btn-danger"> <span
						class="glyphicon glyphicon-user" /></span> LOGOUT
					</a>
				</p>
			</sec:authorize>

			<p align="right">
				<a href="<spring:url value="/" />" class="btn btn-primary"> <span
					class="glyphicon glyphicon-home"></span> BACK TO HOME
				</a>
			</p>
		</div>
</section>

</body>
</html>