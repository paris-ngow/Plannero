<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plannero</title>
<style>
/* register redirect link format */
a:link, a:visited {
	width: 50%;
	border: none;
	border-radius: 4px;
	padding: 30px 30px;
	text-align: middle;
	font-family: tahoma;
	color: #435567;
	padding: 10px 20px;
	transition-duration: 0.4s;
}

/* hover register redirect link format */
a:hover {
	width: 50%;
	border: none;
	border-radius: 4px;
	padding: 30px 30px;
	text-align: middle;
	font-family: tahoma;
	color: grey;
	padding: 10px 20px;
}

/* format register form */
.loginForm {
	background-color: white;
	width: 25%;
	height: 100%;
	margin-left: auto;
	margin-right: auto;
	padding: 1px 20px 20px 20px;
	text-align: center;
}

/* format submit button */
.button {
	background-color: #435567;
	border: none;
	color: white;
	padding: 8px 10px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	transition-duration: 0.4s;
}

/* button hover */
.button:hover {
	background-color: #5c6e80;
}

/* format heading text */
.headingTextStyle {
	font-family: tahoma;
	color: white;
	text-align: center;
	font-size: 50px;
}

/* format register heading text */
.registerTextStyle {
	font-family: tahoma;
	color: #435567;
	text-align: center;
	font-size: 25px;
}

/* format body text */
.bodyTextStyle {
	font-family: tahoma;
	color: #435567;
	text-align: center;
	font-size: 16px;
}
</style>
</head>
<body style="background-color: #435567">
	<!-- title -->
	<h1 class="headingTextStyle">Plannero</h1>

	<!-- login input form -->
	<div class="loginForm">
		<!-- heading -->
		<h2 class="registerTextStyle">Log-in</h2>
		
		<!-- error message for register -->
		<p style="font-family: tahoma; color: green; text-align: center">Register
		Successful. <br>Log-in to access your account.</p>
	
		<!-- declare form block -->
		<form name="loginForm" method="post" action="LoginHandler">

			<!-- email input -->
			<label class="bodyTextStyle" for="email" >Email:</label>
			<input type="email" id="email" name="email" size="24" required></input><br>

			<!-- password input -->
			<label class="bodyTextStyle" for="password">Password:</label>
			<input type="password" id="password" name="password" required></input>

			<!-- submit button -->
			<br> <input class="button" type="submit" value="Submit"></input><br>
		</form>

		<!-- navigate to register -->
		<br> <a href="register.jsp">Don't have an account?<br>Register here.</a>
	</div>

</body>
</html>
