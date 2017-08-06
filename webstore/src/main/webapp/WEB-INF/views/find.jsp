<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>FIND BOOK</title>
</head>
<body>
<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Books</h1>
				<p>Find book by authors or title</p>
				<h2>${info}</h2>
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
		<form:form modelAttribute="newBook" class="form-horizontal">
			<fieldset>
				<legend>Find book</legend>
				
				<div class="form-group">
					<label class="control-label col-lg-2" for="name">Author</label>
					<div class="col-lg-10">
						<form:input id="authors" path="authors" type="text"
							class="form:input-large" />
					</div>
				</div>

				
				<div class="form-group">
					<label class="control-label col-lg-2" for="name">Title</label>
					<div class="col-lg-10">
						<form:input id="title" path="title" type="text"
							class="form:input-large" />
					</div>
				</div>
				
			</fieldset>
			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<input type="submit"  class="btn btn-primary"
						value="Search" />
				</div>
			</div>
		</form:form>
	</section>
</body>
</html>