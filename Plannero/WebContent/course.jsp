<%@ page import="java.util.ArrayList"%>
<%@ page import="cp317.Course"%>
<%@ page import="cp317.Event"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plannero</title>
<style>
.eventList {
	margin-right: auto;
	text-align: right;
	padding-top: 1%;
	padding-bottom: 2%;
	color: white;
	font-family: tahoma;
}

.addEvent {
	padding-bottom: 20px;
	margin-right: auto;
	text-align: right;
	font-family: tahoma;
	color: white;
}

.split {
	height: 100%;
	width: 25%;
	position: fixed;
}

.left {
	padding-left: 20%;
	left: 0;
}

.right {
	padding-right: 20%;
	right: 0;
}

.heading {
padding-top: 60px;
font-family: tahoma; 
color: white; 
text-align: center;
}

input.button {
	width: 20%;
	border: none;
	border-radius: 8px;
	padding: 10px 20px;
	text-align: middle;
	font-family: tahoma;
}

.text {
font-family: tahoma; 
color: white
}
</style>
</head>
<body style="background-color: #435567">
	<div>
		<h1 style="font-family: tahoma; color: white; text-align: center">Plannero</h1>

		<div class="split left">
			<form action="/Plannero/CourseHandler">
				<input class="button" type="submit" value="Back" />
			</form>
		</div>

		<div class="split right" style="float: right;">
			<form method="post" action="/Plannero/CoursePageHandler">
				<input class="button" type="submit" name="deleteCourse" value="Delete Course" />
			</form>
		</div>

		<%
			Course course = (Course) request.getAttribute("course");
		if (course != null) {
		%>
		<h2 class="heading"><%=course.getCourseID() + ": " + course.getCourseName()%></h2>

		<div class="split left">
			<h3 style="font-family: tahoma; color: white;">Course
				Information</h3>
			<p style="font-family: tahoma; color: white;"><%="Course Name: " + course.getCourseName()%></p>
			<p style="font-family: tahoma; color: white;"><%="Course ID: " + course.getCourseID()%></p>
		</div>

		<div class="split right">
			<h3 style="font-family: tahoma; color: white; text-align: right">Course Events</h3>
			<div class="addEvent">
			<h4 style="font-family: tahoma; color: white; text-align: right">Add Event</h34>
				<form method="post" action="CoursePageHandler">
					<label class="text" for="eName">Name:</label> <input type="text" id="eName" name="eName" required></input> <br>
					<label class="text" for="date" >Date: </label> <input type="date" id="date" name="date" required></input><br>
					
					<input type="submit" value="Add" name="add"></input><br>
				</form>

			</div>

			<div class="eventList">
				<form method="post" action="/Plannero/CoursePageHandler">
					<!-- get events -->
					<%
						ArrayList<Event> events = (ArrayList<Event>) request.getAttribute("events");
					if (events != null) {
						for (Event event : events) {
					%>
					<input type="radio" id=<%=event.getEventID()%> name="event"
						value=<%=event.getEventID()%> required> <label
						for=<%=event.getEventID()%>><%=event.getEventName()%></label><br>
					<%
						}
					}
					%>
					<input type="submit" value="Delete Selected Task" name="deleteEvent"></input><br>

				</form>
			</div>
		</div>

		<%
			}
		%>
	</div>
</body>
</html>