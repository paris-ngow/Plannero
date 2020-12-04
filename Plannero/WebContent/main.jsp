<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collections"%>
<%@ page import="cp317.Event"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plannero</title>
<script>
function validateAddTask() {
	const invalid = /^[0-9a-zA-Z]+$/i;
	var name = document.forms["addTask"]["eName"].value;
	
	if (!name.match(invalid)) {
		alert("Alphanumeric characters only!");
		return false;
	} else {
		return true;
	}
	
}

function validateDeleteTask() {
	var task = document.forms["deleteTask"]["task"].value;
	
	if (!task || 0 === task.length) {
		alert("Select a task to delete");
		return false;
	} else {
		return true;
	}
	
}
</script>
<style>
.split {
	height: 100%;
	width: 25%;
	position: fixed;
}

.events {
	padding-right: 20%;
	text-align: right;
	right: 0;
}

.tasks {
	left: 0;
	text-align: left;
	padding-left: 20%;
}

.calendarButtonFormat {
	width: 100%;
	vertical-align: middle;
	text-align: center;
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

.addEvent {
	margin-left: auto;
	text-align: left;
	padding-top: 15px;
	font-family: tahoma;
	colour: white;
}

.taskList {
	margin-left: auto;
	text-align: left;
	font-family: tahoma;
	color: white;
	padding-bottom: 20px;
}

.eventList {
	margin-right: auto;
	text-align: right;
	line-height: 5px;
	font-family: tahoma;
	color: white;
}

</style>
</head>
<body style="background-color: #435567">
	<div>
		<h1 style="font-family: tahoma; color: white; text-align: center">Plannero</h1>

		<div class="calendarButtonFormat">
			<form action="/Plannero/CourseHandler">
				<input class="calendarButton" type="submit" value="Go to Calendar" />
			</form>
		</div>

		<div class="split tasks">
			<h2 style="font-family: tahoma; color: white">Daily Tasks</h2>

			<div class="taskList">
				<form name="deleteTask" method="post" action="/Plannero/TaskHandler" onsubmit="return validateDeleteTask()">
					<!-- get events -->
					<%
						ArrayList<Event> tasks = (ArrayList<Event>) request.getAttribute("tasks");
					
					if (tasks != null) {
						for (Event task : tasks) {
					%>
					<input type="radio" id=<%=task.getEventID()%> name="task"
						value=<%=task.getEventID()%> required> <label
						for=<%=task.getEventID()%>><%=task.getEventName()%></label><br>
					<%
						}
					}
					%>
					<input type="submit" value="Delete Selected Task" name="delete"></input><br>
				</form>
			</div>

			<div class="addEvent">
				<h3 style="font-family: tahoma; color: white"><b>Add
						Task</b></h3>
				<form name="addTask" method="post" action="TaskHandler" onsubmit="return validateAddTask()">
					<label for="eName" style="font-family: tahoma; color: white">Event
						Name:</label> <input type="text" id="eName" name="eName" required></input><input
						type="submit" value="Add" name="add"></input><br>
				</form>
			</div>
		</div>

		<div class="split events">
			<h2 style="font-family: tahoma; color: white">Upcoming Events</h2>
			
			<div class="eventList">
			<%
				ArrayList<Event> events = (ArrayList<Event>) request.getAttribute("events");
				Collections.sort(events);
				java.util.Date now = new java.util.Date();
				
			if (events != null) {
				int numEvents = 0;
				for (Event event : events) {
					
					if (numEvents > 5) { break;}
					else {numEvents++;}
					
					if (event.getEventDate().after(now)) {
			%>

			<p><i><%=event.getEventName()%></i></p>
			<p><%="Course ID: " + event.getCourseID()%></p>
			<p><%="Date: " + event.getEventDate()%></p>
			<br>

			<%
					}
				}
			}
			%>
			</div>
		</div>

	</div>
</body>
</html>
