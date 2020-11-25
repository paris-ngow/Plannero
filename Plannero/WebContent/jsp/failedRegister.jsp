<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plannero</title>
</head>
<body>
<body style="background-color:#435567">
<h1><p style="font-family:tahoma; color:white; text-align:center">Plannero</p></h1>
	<p style="font-family:tahoma; color:red; text-align:center">Register Unsuccessful. Try again</p>
	<form method="post" action="/Plannero/RegisterHandler">
		<label for="fname" style="font-family:tahoma; color:white">First Name:</label>
		<input type="text" id="fname" name="fname" align="middle"></input><br>
		<label for="lname" style="font-family:tahoma; color:white">Last Name:</label>
		<input type="text" id="lname" name="lname"></input>
		<br></br>
		<label for="email" style="font-family:tahoma; color:white">Email:</label>
		<input type="text" id="email" name="email"></input><br>
		<label for="password" style="font-family:tahoma; color:white">Password:</label>
		<input type="text" id="password" name="password"></input>
		<br>
		<input type="submit" value="Submit"></input>
 	</form>
</body>
</body>
</html>
