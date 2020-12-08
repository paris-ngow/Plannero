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
/* format buttons at the top of the screen */
.navigationButton {
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

/* navigatio button hover */
.navigationButton:hover {
	background-color: #DFDFDF;
}

/* establish split screen */
.split {
	height: 100%;
	width: 25%;
	position: fixed;
}

/* format left side of split */
.left {
	padding-left: 20%;
	left: 0;
}

/* format right side of split */
.right {
	padding-right: 20%;
	right: 0;
}

/* format course information block */
.courseInfo {
	background-color: white;
	padding: 5px;
}

/* format syllabus form */
.syllabusForm {
	padding: 10px;
	border: 1px solid white;
	font-family: tahoma;
	color: white;
	font-size: 18px;
	font-weight: bold;
}

/* format button for syllabus */
.syllabusButton {
	background-color: white;
	border: none;
	color: #435567;
	padding: 8px 10px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	transition-duration: 0.4s;
}
/* format button for syllabus hover */
.syllabusButton:hover {
	background-color: #DFDFDF;
}

/* format event list */
.eventList {
	background-color: white;
	padding-top: 10px;
	padding-bottom: 20px; text-align : center; color : white;
	font-family: tahoma;
	color: white;
	text-align: center
}

/* format add event form */
.addEvent {
	background-color: white;
	padding: 10px;
	text-align: center;
	font-family: tahoma;
	color: white;
}

/* format button to submit data regarding tasks */
.eventButton {
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

/* event button hover */
.eventButton:hover {
	background-color: #5c6e80;
}

/* format heading text */
.headingTextStyle {
	font-family: tahoma;
	color: white;
	text-align: center;
	font-size: 50px;
}

/* format heading text */
.courseNameTextStyle {
	padding-top: 60px;
	font-family: tahoma;
	color: white;
	text-align: center;
	font-size: 30px;
}

/* format heading text */
.subheadingTextStyle {
	font-family: tahoma;
	color: #435567;
	text-align: center;
	font-size: 25px;
}

/* format body text */
.formTextStyle {
	font-family: tahoma;
	color: #435567;
	text-align: center;
	font-size: 16px;
	font-weight: bold;
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
		<!-- heading -->
		<h1 class="headingTextStyle">Plannero</h1>

		<!-- back button -->
		<div class="split left" style="text-align: center">
			<form action="/Plannero/CourseHandler">
				<input class="navigationButton" type="submit" value="Back" />
			</form>
		</div>

		<!-- delete course button -->
		<div class="split right" style="text-align: center">
			<form method="post" action="/Plannero/CoursePageHandler">
				<input class="navigationButton" type="submit" name="deleteCourse"
					value="Delete Course" />
			</form>
		</div>

		<!-- get specific course information stored in system -->
		<%
			Course course = (Course) request.getAttribute("course");
		if (course != null) {
		%>

		<!-- display course name as title -->
		<h2 class="courseNameTextStyle"><%=course.getCourseID() + ": " + course.getCourseName()%></h2>

		<!-- container for course information -->
		<div class="split left">

			<!-- display course details -->
			<div class="courseInfo">
				<h3 class="subheadingTextStyle">Course Information</h3>
				<p class="bodyTextStyle"><%="<strong>Course Name: </strong>" + course.getCourseName()%></p>
				<p class="bodyTextStyle"><%="<strong>Course ID: </strong>" + course.getCourseID()%></p>
				<p class="bodyTextStyle"><%="<strong>Location: </strong>" + course.getCourseLocation()%></p>
				<p class="bodyTextStyle"><%="<strong>Days Of The Week: </strong>" + course.getDays()%></p>
				<p class="bodyTextStyle"><%="<strong>Start Time: </strong>" + course.getStartTime()%></p>
				<p class="bodyTextStyle"><%="<strong>End Time: </strong>" + course.getEndTime()%></p>
				<br>
			</div>

			<!-- space between containers -->
			<div style="padding: 10px"></div>

			<!-- syllabus form to display/upload -->
			<div class="syllabusForm">
				<!-- view syllabus -->
				<form name="viewSyllabus" method="get" action="SyllabusHandler"
					target="_blank">
					<label>Syllabus (PDF ONLY):</label> <input class="syllabusButton"
						type="submit" value="View Syllabus" name="viewSyllabus">
				</form>
				<br>

				<!-- upload syllabus -->
				<form name="uploadSyllabus" method="post" action="SyllabusHandler"
					enctype="multipart/form-data" target="_blank">
					<label>Upload:</label> <input type="file" id="syllabus"
						name="syllabus" accept=".pdf" required> <input
						class="syllabusButton" type="submit" value="Upload"
						name="addSyllabus">
				</form>

			</div>
		</div>
		
		<!-- container for event information for the course -->
		<div class="split right">
			<!-- format add event form -->
			<div class="addEvent">
				
				<!-- heading for add event form -->
				<h3 class="subheadingTextStyle">Add Event</h3>
				
				<!-- implement add event form -->
				<form name="addEvent" method="post" action="CoursePageHandler">
					<!-- name input -->
					<label class="formTextStyle" for="eName">Name:</label> 
					<input type="text" id="eName" name="eName" required></input><br> 
					
					<!-- description input -->
					<label class="formTextStyle" for="eDescription">Description:</label> 
					<input type="text" id="eDescription" name="eDescription" maxlength="50"></input><br>
					
					<!-- date input -->
					<label class="formTextStyle" for="date">Date: </label> 
					<input type="date" id="date" name="date" required></input><br> 
					
					<!-- submit button -->
					<input class="eventButton" type="submit" value="Add" name="add"></input><br>
				</form>
			</div>

			<!-- space between containers -->
			<div style="padding: 10px"></div>

			<!-- format event list for course -->
			<div class="eventList">
				<!-- heading for course events list -->
				<h3 class="subheadingTextStyle">Course Events</h3>
				
				<!-- display list of events for the course in form so user can delete -->
				<form method="post" action="/Plannero/CoursePageHandler">
					
					<!-- get events -->
					<%
						ArrayList<Event> events = (ArrayList<Event>) request.getAttribute("events");
					if (events != null) {
						for (Event event : events) {
					%>
					
					<!-- display specific event -->
					<input type="radio" id=<%=event.getEventID()%> name="event"
						value=<%=event.getEventID()%> required> <label
						class="bodyTextStyle" for=<%=event.getEventID()%>><%=event.getEventName() + ", " + event.getEventDate()%></label><br>
					<%
						}
					}
					%>
					
					<!-- submit button to delete event -->
					<input class="eventButton" type="submit"
						value="Delete Selected Event" name="deleteEvent"></input><br>

				</form>
			</div>
		</div>

		<%
			}
		%>
	</div>
</body>
</html>