<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plannero</title>
<script>
	/**
	 * Validate course information submitted. 
	 */
	function validateRequest() {
		//password
		var password = document.forms["login"]["password"].value;

		//check if password is at least 8 characters
		if (password.length < 8) {
			alert("Password must be at least 8 characters!");
			return false;
		}

		return true;
	}
</script>
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

/* format heading */
.headingFormat {
	text-align: center;
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
	<div class="headingFormat">
		<img src="/Plannero/logos/topLogo.png" alt="Plannero"
			style="width: 250px; height: 178.5px; font-family: tahoma; color: white; font-size: 50px; padding: 20px;">
	</div>

	<!-- login input form -->
	<div class="loginForm">
		<!-- heading -->
		<h2 class="registerTextStyle">Log-in</h2>

		<!--  success message for log out -->
		<p style="font-family: tahoma; color: green; text-align: center">Log-out
			Successful.</p>

		<!-- declare form block -->
		<form name="login" method="post" action="LoginHandler" onsubmit="return validateRequest()">

			<!-- email input -->
			<label class="bodyTextStyle" for="email">Email:</label> <input
				type="email" id="email" name="email" size="24" required></input><br>

			<!-- password input -->
			<label class="bodyTextStyle" for="password">Password:</label> <input
				type="password" id="password" name="password" required></input>

			<!-- submit button -->
			<br> <input class="button" type="submit" value="Submit"></input><br>
		</form>

		<!-- navigate to register -->
		<br> <a href="register.jsp">Don't have an account?<br>Register
			here.
		</a>
	</div>

</body>
</html>