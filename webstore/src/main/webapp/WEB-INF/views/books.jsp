<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Books</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Books</h1>
				<p>This page contains all informations about books</p>
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

	<section class="container">
		<div class="row" align = "center">
			<c:forEach items="${bookList}" var="book">
				<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
					<div class="thumbnail">
						<div class="caption">
							<h3>${book.id}</h3>
							<p>${book.title}</p>
							<p>${book.authors}</p>
							<p>Status: ${book.status}</p>
							<p align = "center" style="word-spacing: 7pt; ">
								<a
									href=" <spring:url value="/books/book?id=${book.id}" /> "
									class="btn btn-primary"> <span
									class="glyphicon-info-sign glyphicon" /></span> Details
								</a>
								<a
									href=" <spring:url value="/books/delete?id=${book.id}" /> "
									class="btn btn-danger"> <span
									class="glyphicon-info-sign glyphicon" /></span> Delete
								</a>
							</p>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>
</body>
</html>
