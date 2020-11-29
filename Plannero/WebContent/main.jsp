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
	padding-top: 50px;
	vertical-align: bottom;
	margin-left: auto;
	margin-right: auto;
	border-collapse: separate;
}

input.calendarButton {
	width: 50%;
	border: none;
	border-radius: 4px;
	padding: 10px 20px;
	text-align: middle;
	font-family: tahoma;
	padding: 10px 20px;
}
</style>
</head>
<body style="background-color: #435567">
	<h1 style="font-family: tahoma; color: white; text-align: center">Plannero</h1>

	<div
		style="width: 100%; height: 100%; position: absolute; vertical-align: middle; text-align: center;">
		<form action="calendar.jsp">
			<input class="calendarButton" type="submit" value="Go to Calendar" />
		</form>
	</div>

	<table class="headings">
		<tr>
			<th><h2
					style="font-family: tahoma; color: white; text-align: middle">Daily
					Tasks</h2></th>
			<th><h2
					style="font-family: tahoma; color: white; text-align: middle">Upcoming
					Events</h2></th>
		</tr>
	</table>
</body>
</html>