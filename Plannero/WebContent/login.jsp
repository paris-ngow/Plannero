<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plannero</title>
<style>
a:link, a:visited {
	width: 50%;
	border: none;
	border-radius: 4px;
	padding: 30px 30px;
	text-align: middle;
	font-family: tahoma;
	color: white;
	padding: 10px 20px;
}

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

</style>
</head>
<body style="background-color: #435567">
	<!-- title -->
	<h1>
		<p style="font-family: tahoma; color: white; text-align: center">Plannero</p>
	</h1>
	<!-- heading -->
	<h2>
		<p style="font-family: tahoma; color: white; text-align: center">Log-in</p>
	</h2>

	<!-- login input form -->
	<div
		style="width: 100%; height: 100%; position: absolute; vertical-align: middle; horizontal-align: center; text-align: center;">
		<form method="post" action="LoginHandler">
			<label for="fname" style="font-family: tahoma; color: white">First
				Name:</label> <input type="text" id="fname" name="fname" align="middle"></input><br>
			<label for="lname" style="font-family: tahoma; color: white">Last
				Name:</label> <input type="text" id="lname" name="lname"></input> <br></br>
			<label for="email" style="font-family: tahoma; color: white">Email:</label>
			<input type="text" id="email" name="email"></input><br> <label
				for="password" style="font-family: tahoma; color: white">Password:</label>
			<input type="password" id="password" name="password"></input> <br>
			
			<input type="submit" value="Submit"></input><br>
		</form>
		<br>

		<a href="register.jsp">Don't have an account? Register here.</a>
	</div>

</body>
</html>