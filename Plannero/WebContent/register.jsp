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
		var password = document.forms["register"]["password"].value;

		//check if password is at least 8 characters
		if (password.length < 8) {
			alert("Password must be at least 8 characters!");
			return false;
		}

		return true;
	}
</script>
<style>
/* login redirect link format */
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

/* hover login redirect link format */
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
.registerForm {
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
	<div>
		<!-- title -->
		<div class="headingFormat">
			<img src="/Plannero/logos/topLogo.png" alt="Plannero"
				style="width: 250px; height: 178.5px; font-family: tahoma; color: white; font-size: 50px; padding: 20px;">
		</div>

		<!-- register input form -->
		<div class="registerForm">
			<!-- heading -->
			<h2 class="registerTextStyle">Register</h2>

			<!-- declare form block -->
			<form name="register" method="post" action="RegisterHandler"
				onsubmit="return validateRequest()">

				<!-- email input -->
				<label class="bodyTextStyle" for="email">Email:</label> <input
					type="email" id="email" name="email" size="26" required></input><br>

				<!-- password input -->
				<label class="bodyTextStyle" for="password">Password:</label> <input
					type="password" id="password" name="password" size="22" required></input><br>

				<!-- submit button -->
				<input class="button" type="submit" value="Submit"></input><br>
			</form>
			<!-- redirect to login page -->
			<br> <a class="bodyTextStyle" href="login.jsp">Already have
				an account?<br>Log-in here.
			</a>
		</div>
	</div>
</body>
</html>
