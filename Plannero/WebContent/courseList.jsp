<%@ page import="java.util.ArrayList"%>
<%@ page import="cp317.Course"%>
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
	function validateAddCourse() {
		//regex for alphanumeric name
		const validName = /^[a-zA-Z0-9&()#\-\s]+$/;
		//regex for id
		const validID = /^[a-zA-Z0-9\-\s]+$/;
		
		//course name
		var name = document.forms["addCourse"]["cName"].value;
		//course id
		var id = document.forms["addCourse"]["cID"].value;
		//course start time
		var start = document.forms["addCourse"]["cStart"].value;
		//course end time
		var end = document.forms["addCourse"]["cEnd"].value;
		//course days
		var daysOfWeek = document.getElementsByName("cDays");
		// iterator to check day(s) selected
		var days = 0;

		//check if course name is alphanumeric
		if (!name.match(validName)) {
			alert("Only use alphanumeric characters (including (), &, #) for the course name.");
			return false;
		} 

		//check if course id is alphanumeric
		if (!id.match(validID)) {
			alert("Only use alphanumeric characters for the course ID.");
			return false;
		} else {
			//check if course id is 5 characters
			if (id.length != 5) {
				alert("Enter a 5 character ID. (ex. CP317)")
				return false;
			}
		}
		
		//check if start time is before end time
		if (start > end || start == end) {
			alert("Start time should be before end time.");
			return false;
		} 
		
		//look through all checkboxes
		for (var i = 0; i < checkboxs.length; i++) {
			//see if a day is checked
			if(daysOfWeek[i].checked) {
				days++;
			}
		}
		
		if (days === 0) {
			return false;
		}
		
		//passed all test cases
		return true;

	}
</script>
<style>
/* establish split screen */
.split {
	height: 100%;
	width: 25%;
	position: fixed;
}

/* format left side of split screen */
.left {
	margin-right: auto;
	text-align: left;
	padding-top: 20px;
	padding-left: 20%;
	left: 0;
	color: white;
	font-family: tahoma;
	padding-left: 20%;
}

/* format right side of split screen*/
.right {
	padding-top: 20px;
	padding-right: 20%;
	right: 0;
	text-align: right;
	font-family: tahoma;
	color: white;
}

/* format list of courses*/
.courseList {
	background-color: white;
	padding: 5px;
	text-align: center;
}

/* format add course */
.addCourse {
	background-color: white;
	padding: 5px;
	text-align: center;
}

/* format view course button position */
.backButtonFormat {
	width: 100%;
	vertical-align: middle;
	text-align: center;
}

/* format back button */
.backButton {
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

.backButton:hover {
	background-color: #DFDFDF;
}

/* format fieldset for lecture days in add course form*/
fieldset {
	padding-top: 1px;
	border: hidden;
}

/* format button to submit data regarding tasks */
.formButton {
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

/* form button hover */
.formButton:hover {
	background-color: #5c6e80;
}

/* format logout button position*/
.logoutButtonFormat {
	text-align: right;
}

/* format button to logout of session  */
.logoutButton {
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

/* logout button hover */
.logoutButton:hover {
	background-color: #DFDFDF;
}

/* format heading text */
.headingTextStyle {
	font-family: tahoma;
	color: white;
	text-align: center;
	font-size: 50px;
}

/* format heading text */
.subheadingTextStyle {
	padding-bottom: 10px;
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
		<!-- View courses button -->
		<div class="logoutButtonFormat">
			<form action="/Plannero/LogoutHandler">
				<input class="logoutButton" type="submit" value="Logout" />
			</form>
		</div>
	
		<!-- title heading -->
		<h1 class="headingTextStyle">Plannero</h1>

		<!-- back button -->
		<div class="backButtonFormat">
			<form action="/Plannero/EventHandler">
				<input class="backButton" type="submit" value="Back" />
			</form>
		</div>

		<!-- container holding course list -->
		<div class="split left">
			<div class="courseList">
				<!-- sub heading for course list -->
				<h2 class="subheadingTextStyle">Courses</h2>

				<!-- form holding courses to view -->
				<form action="/Plannero/CoursePageHandler">
					<!-- get courses -->
					<%
						ArrayList<Course> courses = (ArrayList<Course>) request.getAttribute("courses");
					if (courses != null) {
						for (Course course : courses) {
					%>
					
					<!-- display course id and course name -->
					<input type="radio" id=<%=course.getSqlID()%> name="course"
						value=<%=course.getSqlID()%> required> <label
						class="bodyTextStyle" for=<%=course.getSqlID()%>><%=course.getCourseID() + ": " + course.getCourseName()%></label><br>
					<%
						}
					}
					%>
					<!-- submit request to view course details -->
					<input class="formButton" type="submit" name="view" value="View Course" />
				</form>
			</div>
		</div>

		<div class="split right">
			<div class="addCourse">
				<!-- sub heading  -->
				<h2 class="subheadingTextStyle">Add Course</h2>

				<!-- form to add course -->
				<form name="addCourse" method="post" action="CourseHandler"
					onsubmit="return validateAddCourse()">

					<!-- course name input -->
					<label class="formTextStyle" for="cName">Name:</label> <input
						type="text" id="cName" name="cName" required></input><br>

					<!-- course id input -->
					<label class="formTextStyle" for="cID">Course ID:</label> <input
						type="text" id="cID" name="cID" size="16" required></input><br>

					<!-- course location input -->
					<label class="formTextStyle" for="cLocation">Location:</label> <input
						type="text" id="cLocation" name="cLocation" size="18" required></input><br>

					<!-- course lecture start time input -->
					<label class="formTextStyle" for="cStart">Start Time:</label> <input
						type="time" id="cStart" name="cStart" size="136" required></input><br>

					<!-- course lecture end time input -->
					<label class="formTextStyle" for="cEnd">End Time:</label> <input
						type="time" id="cEnd" name="cEnd" size="16" required></input><br>

					<!-- course lecture days input -->
					<fieldset>
						<legend class="formTextStyle">Lecture Days:</legend>
						<!-- Monday -->
						<input type="checkbox" name="cDays" value="Monday">
						<label class="bodyTextStyle" for="cDays">Monday</label><br> 
						
						<!-- Tuesday -->
						<input type="checkbox" name="cDays" value="Tuesday">
						<label class="bodyTextStyle" for="cDays">Tuesday</label><br> 
						
						<!-- Wednesday -->
						<input type="checkbox" name="cDays" value="Wednesday">
						<label class="bodyTextStyle" for="cDays">Wednesday</label><br> 
						
						<!-- Thursday -->
						<input type="checkbox" name="cDays" value="Thursday">
						<label class="bodyTextStyle" for="cDays">Thursday</label><br> 
						
						<!-- Friday -->
						<input type="checkbox" name="cDays" value="Friday">
						<label class="bodyTextStyle" for="cDays">Friday</label><br> 
					</fieldset>

					<!-- submit button -->
					<input class="formButton" type="submit" value="Add" name="add"></input>
					<br>
				</form>
			</div>
		</div>
	</div>
</body>
</html>