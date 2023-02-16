<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>

<head>

	<title>Welcome to the Company</title>

</head>

<body>
	<h2>Company HomePage</h2>
	<hr>
	
	<p>
	Welcome to the company, Ma Boi woohoo
	</p>
	
	<hr>
	
	<!-- display user name and role -->
	<p>
		User: <security:authentication property="principal.username"/>
		<br><br>
		Role(s): <security:authentication property="principal.authorities"/>
		
	</p>
	
	<hr>
	
	<security:authorize access="hasRole('MANAGER')">
	
		<!-- Add a link to point  to the leaders .. this is for the managers-->
		<p>
			<a href = "${pageContext.request.contextPath}/leaders">Leadership meeting</a>
			(Only for Manager people)
		
		</p>
		
	</security:authorize>
	
	
	
	
	
	<security:authorize access="hasRole('ADMIN')">
	
		<!-- Add a link to point  to the systems .. this is for the admins-->
		
		<p>
			<a href = "${pageContext.request.contextPath}/systems">Admin meeting</a>
			(Only for Admin people)
		
		</p>
	
	</security:authorize>
	
	
	
	<!-- Add a logout button -->
	<form:form action= "${pageContext.request.contextPath}/logout" 
	method = "POST">
	
		<input type = "submit" value = "Logout" />
	</form:form>
</body>

</html>









