<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Random Question Generator</title>
		<link rel="stylesheet" type="text/css" href="style/ProjectTrackerStyle.css" />
		<link href='http://fonts.googleapis.com/css?family=Michroma' rel='stylesheet' type='text/css'>
		
		<script>
		
			/* Run functions after page body is loaded */
			function onBodyLoad() {
				drawQuestionCanvas();
				getLocation();
			}
		
			/* Draw images on to the question canvas */
			function drawQuestionCanvas() {
				var questionCanvas = document.getElementById("questionCanvas");
				var ctx = questionCanvas.getContext("2d");

				// draw gradient
				var grd = ctx.createLinearGradient(0,0,20,0);
				grd.addColorStop(0, "#ABABAB");
				grd.addColorStop(1, "#DEDEDE");
				
				ctx.fillStyle = grd;
				ctx.fillRect(0,0,300,200);
				
				// draw text
				ctx.font = "12pt Arial";
				ctx.fillStyle = "#000000";
				ctx.fillText("${questionBean.questionText}", 20, 20);
				
				// draw line
				ctx.moveTo(20, 25);
				ctx.lineTo(280, 25);
				ctx.stroke();
				
				// draw circle
				ctx.beginPath();
				ctx.arc(150,75,30,0,2*Math.PI);
				ctx.stroke();
			}
			
			/* Get the geo-location of the user. */
			function getLocation() {
				if (navigator.geolocation) {
					navigator.geolocation.getCurrentPosition(showPosition);
			    } else { 
			    	document.getElementById("locationInfo").innerHTML="Geolocation is not supported by this browser.";
			    }
			}
			
			/* Display location information in text and map form */
			function showPosition(position) {
				var latlon = position.coords.latitude + "," + position.coords.longitude;
				var imgURL = "http://maps.googleapis.com/maps/api/staticmap?center=" + latlon +"&zoom=14&size=400x300&sensor=false";
				
				var locationInfo = document.getElementById("locationInfo");
				locationInfo.innerHTML += "Latitude: " + position.coords.latitude + "<br/>Longitude: " + position.coords.longitude + "<br/><br/>";
				locationInfo.innerHTML += "<img src='" + imgURL + "'>";
			}
			
			/* Change the CSS style class of the given element */
			function setStyleClass(element, cssClass) {
				if(element) {
					element.className = cssClass;
				}
			}
		</script>
	</head>
	<body onload="onBodyLoad()">
	
		<div class="randomQuestionHeading">Random Question Generator</div>
		
		<br/>
		
		<div id="questionBox">
			<header>
				${questionBean.index != null ? "You selected question #" : "You didn't select a specific question so a random question was selected." }
				${questionBean.index != null ? questionBean.index : " " }
			</header>
			<section>
				${questionBean.questionText}
			</section>
			<br/>
			<canvas id="questionCanvas"></canvas>
		</div>		
		
		<br/>
		
		<div id="locationInfo">Location Information<br/><br/></div>
		
		<br/>
		<div id="refreshButton" class="refreshButton" 
			onmouseover="setStyleClass(this, 'refreshButton refreshButton_Over')" 
			onmouseout="setStyleClass(this, 'refreshButton refreshButton_Up')" 
			onmousedown="setStyleClass(this, 'refreshButton refreshButton_Down')" 
			onmouseup="setStyleClass(this, 'refreshButton')">Refresh Screen</div>
	</body>
</html>