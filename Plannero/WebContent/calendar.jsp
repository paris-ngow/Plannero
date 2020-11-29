<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plannero</title>
<style>
table.headings {
	width: 100%;
	border: 5px;
	margin-left: auto;
	margin-right: auto;
	border-collapse: separate;
}

input.backButton {
	width: 10%;
	border: none;
	border-radius: 8px;
	padding: 10px 20px;
	text-align: middle;
	font-family: tahoma;
}
</style>
</head>
<body style="background-color: #435567">
	<h1 style="font-family: tahoma; color: white; text-align: center">Plannero</h1>

	<form action="main.jsp">
		<input class="backButton" type="submit" value="Back" />
	</form>

	<table class="headings">
		<tr>
			<th><h2 style="font-family: tahoma; color: white; text-align: middle">Calendar</h2></th>
			<th><h2
					style="font-family: tahoma; color: white; text-align: middle">Courses</h2></th>
		</tr>
	</table>
</body>
</html>