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
<style>
/* establish split screen */
.split {
	height: 100%;
	width: 25%;
	position: fixed;
}

/* set up left side of split screen */
.tasks {
	padding-top: 30px;
	padding-left: 20%;
	left: 0;
	text-align: left;
}

/* format form for deleting tasks */
.taskForm {
	margin-left: auto;
	text-align: center;
	font-family: tahoma;
	color: white;
}

/* format list of tasks */
.taskList {
	background-color: white;
	margin-left: auto;
	padding: 5px;
	text-align: center;
	font-family: tahoma;
	color: white;
}

/* format add task block */
.addTask {
	margin-left: auto;
	text-align: left;
	padding: 15px;
	border: 1px solid white;
	font-family: tahoma;
	colour: white;
}

/* format button to delete data regarding tasks */
.deleteTaskButton {
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

/* delete task button hover */
.deleteTaskButton:hover {
	background-color: #5c6e80;
}

/* format button to add data regarding tasks */
.addTaskButton {
	background-color: #white;
	border: none;
	color: #435567;
	padding: 8px 10px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	transition-duration: 0.4s;
}

/* add task button hover */
.addTaskButton:hover {
	background-color: #DFDFDF;
}

/* set up right side of split screen */
.events {
	padding-top: 30px;
	padding-right: 20%;
	right: 0;
	text-align: right;
}

/* format list of upcoming events */
.eventList {
	background-color: white;
	margin-right: auto;
	padding: 10px;
	text-align: center;
	line-height: 5px;
}

/* format view course button position */
.viewCourseButtonFormat {
	width: 100%;
	vertical-align: middle;
	text-align: center;
}

/* format view course button */
.viewCourseButton {
	width: 50%;
	border: none;
	border-radius: 4px;
	padding: 10px 20px;
	text-align: middle;
	font-family: tahoma;
	font-weight: bold;
	color: #435567;
	padding: 10px 20px;
	display: inline-block;
	font-size: 16px;
	transition-duration: 0.4s;
}

/* view course button hover */
.viewCourseButton:hover {
	background-color: #DFDFDF;
}

/* format heading text */
.headingTextStyle {
	font-family: tahoma;
	color: white;
	text-align: center;
	font-size: 50px;
}

/* format sub heading text */
.subheadingTextStyle {
	font-family: tahoma;
	color: #435567;
	text-align: center;
	font-size: 25px;
}

/* format sub heading text */
.taskFormText {
	font-family: tahoma;
	color: white;
	font-size: 20px;
	font-weight: bold;
}

/* format body text */
.bodyTextStyle {
	font-family: tahoma;
	color: #435567;
	font-size: 16px;
}
</style>
</head>
<body style="background-color: #435567">
	<div>
		<!-- Plannero header -->
		<h1 class="headingTextStyle">Plannero</h1>

		<!-- View courses button -->
		<div class="viewCourseButtonFormat">
			<form action="/Plannero/CourseHandler">
				<input class="viewCourseButton" type="submit" value="View Courses" />
			</form>
		</div>

		<!-- tasks on LEFT -->
		<div class="split tasks">

			<!-- list of tasks -->
			<div class="taskList">
				<!-- sub heading for tasks -->
				<h2 class="subheadingTextStyle">Daily Tasks</h2>

				<!-- list of tasks in a form so the user can delete -->
				<form class="taskForm" name="deleteTask" method="post"
					action="/Plannero/TaskHandler"
					onsubmit="return validateDeleteTask()">
					<!-- get events -->
					<%
						ArrayList<Event> tasks = (ArrayList<Event>) request.getAttribute("tasks");

					if (tasks != null) {
						for (Event task : tasks) {
					%>
					<input type="radio" id=<%=task.getEventID()%> name="task"
						value=<%=task.getEventID()%> required> <label
						class="bodyTextStyle" for=<%=task.getEventID()%>><%=task.getEventName()%></label><br>
					<%
						}
					}
					%>

					<!-- submit request to delete a selected task -->
					<input class="deleteTaskButton" type="submit"
						value="Delete Selected Task" name="delete"></input><br>
				</form>
			</div>

			<!-- space between containers -->
			<div style="padding: 10px";></div>

			<!-- form to add tasks -->
			<div class="addTask">
				<!-- title of form to add a task -->
				<h3 class="taskFormText">Add Task</h3>

				<!-- implement form to add a task -->
				<form name="addTask" method="post" action="TaskHandler"
					onsubmit="return validateAddTask()">

					<!-- task name input -->
					<label for="eName" style="font-family: tahoma; color: white">Event
						Name:</label> <input type="text" id="eName" name="eName" required></input>

					<!-- submit request to add task to the database -->
					<input class="addTaskButton" type="submit" value="Add" name="add"></input><br>
				</form>
			</div>
		</div>

		<div class="split events">
			<div class="eventList">
				<!-- sub heading for events -->
				<h2 class="subheadingTextStyle">Upcoming Events</h2>

				<!-- retrieve events from the database for the user -->
				<%
					ArrayList<Event> events = (ArrayList<Event>) request.getAttribute("events");

				// sort events by date
				Collections.sort(events);

				//get today's date
				java.util.Date now = new java.util.Date();

				//whle events exist
				if (events != null) {
					//count number of events displayed so far
					int numEvents = 0;
					for (Event event : events) {

						//check if number of events is greater than 5 (will only show 5 or less)
						if (numEvents > 5) {
					break;
						} else {
					numEvents++;
						}

						//check if event date is after today's date.. want to show upcoming events
						if (event.getEventDate().after(now)) {
				%>

				<!-- output event name -->
				<p class="bodyTextStyle">
					<b><i><%=event.getEventName()%></i></b>
				</p>
				
				<!-- output event description if the event has one -->
				<%
					if (event.getEventDescription() != null) {
				%>
				<p class="bodyTextStyle">
					<i><%=event.getEventDescription()%></i>
				</p>
				<%
					}
				%>
				
				<!-- output course event is associated with -->
				<p class="bodyTextStyle"><%="Course ID: " + event.getCourseID()%></p>
				<!-- output date event will take place -->
				<p class="bodyTextStyle"><%="Date: " + event.getEventDate()%></p>
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
