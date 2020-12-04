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
function validateAddCourse() {
	const invalid = /^[a-zA-Z0-9\-\s]+$/;
	var name = document.forms["addCourse"]["cName"].value;
	var id = document.forms["addCourse"]["cID"].value;
	
	if (!name.match(invalid)) {
		alert("Only use alphanumeric characters for the course name.");
		return false;
	} else {
		if(name.length < 3) {
			alert("Enter a valid name. (ex. Software Engineering)")
			return false;
		}
	}
	
	if (!id.match(invalid)) {
		alert("Only use alphanumeric characters for the course ID.");
		return false;
	} else {
		if (id.length != 5) {
			alert("Enter a valid ID. (ex. CP317)")
			return false;
		}
	}
	
	return true;
	
}
</script>
<style>
.split {
	height: 100%;
	width: 25%;
	position: fixed;
}

.left {
	padding-left: 10%;
	left: 0;
}

.right {
	margin-right: auto;
	text-align: right;
	padding-top: 1%;
	padding-bottom: 2%;
	padding-right: 10%;
	right: 0;
	color: white;
	font-family: tahoma;
}

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

.addCourse {
	padding-top: 20px;
	text-align: right;
	font-family: tahoma;
	color: white;
}
</style>
</head>
<body style="background-color: #435567">
	<div>
		<h1 style="font-family: tahoma; color: white; text-align: center">Plannero</h1>

		<div class="left">
			<form action="/Plannero/TaskHandler">
				<input class="backButton" type="submit" value="Back" />
			</form>
		</div>
		
		<div class="split left">
			
		</div>

		<div class="split right">
			<h2>Courses</h2>
			<form action="/Plannero/CoursePageHandler">
				<!-- get courses -->
				<%
					ArrayList<Course> courses = (ArrayList<Course>) request.getAttribute("courses");
				if (courses != null) {
					for (Course course : courses) {
				%>
				<input type="radio" id=<%=course.getSqlID()%> name="course"
					value=<%=course.getSqlID()%> required> <label class="courses"
					for=<%=course.getSqlID()%>><%=course.getCourseID() + ": " + course.getCourseName()%></label><br>
				<%
					}
				}
				%>
				<input type="submit" name="view" value="View Course" />
			</form>

			<div class="addCourse">
				<h3 style="font-family: tahoma; color: white"><b>Add
						Course</b></h3>
				<form name="addCourse" method="post" action="CourseHandler" onsubmit="return validateAddCourse()">
					<label for="cName" style="font-family: tahoma; color: white">Course
						Name:</label> <input type="text" id="cName" name="cName" required></input><br>
					<label for="cID" style="font-family: tahoma; color: white">Course
						ID:</label> <input type="text" id="cID" name="cID" size="13" required></input> <input
						type="submit" value="Add" name="add"></input> <br>
				</form>
			</div>
		</div>
	</div>
</body>
</html>