
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<%-- <script type="text/javascript" src="js/vastu.js"></script> --%>

<script type="text/javascript">
	var centerX, centerY, context, canvas;
	var radius = 240;
	var forward = true;
	var outerLayer = [ "SA", "ME", "KE", "VE", "SU", "MO", "MA", "RA", "JU",
			"SA", "ME", "KE", "VE", "SU", "MO", "MA", "RA", "JU", "SA", "ME",
			"KE", "VE", "SU", "MO", "MA", "RA", "JU" ];
	var layer6 = [ "MO", "SU", "ME", "VE", "ME", "JU", "SA", "SA", "JU", "MA",
			"VE", "ME" ];
	var layer5 = [ "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Saggi",
			"Capri", "Aqua", "Pisces", "Aries", "Taurus", "Gemini" ];
	var layer4 = [ "Rajas", "Tamas", "Satav", "Rajas", "Tamas", "Satav",
			"Rajas", "Tamas", "Satav", "Rajas", "Tamas", "Satav" ];
	var layer3 = [ "Water", "Fire", "Earth", "Air", "Water", "Fire", "Earth",
			"Air", "Water", "Fire", "Earth", "Air" ];
	var layer2 = [ "Prakriti", "Purush", "Prakriti", "Purush", "Prakriti",
			"Purush", "Prakriti", "Purush", "Prakriti", "Purush", "Prakriti",
			"Purush" ];
	var layer1 = [ "4", "5", "6", "7", "8", "9", "10", "11", "12", "1", "2",
			"3" ];

	var signArr = {
		"Aries" : 0,
		"Taurus" : 1,
		"Gemini" : 2,
		"Cancer" : 3,
		"Leo" : 4,
		"Virgo" : 5,
		"Libra" : 6,
		"Scorpio" : 7,
		"Saggi" : 8,
		"Capr" : 9,
		"Aqua" : 10,
		"Pisces" : 11
	};

	$(document).ready(function() {
		canvas = document.getElementById('myCanvas');
		context = canvas.getContext('2d');
		centerX = canvas.width / 2;
		centerY = canvas.height / 2;
		drawCircle();
	});

	function drawCircle() {
		var circleRadius = radius;
		drawLineInInnerCircle();
		for ( var i = 1; i < 9; i++) {
			if (i == 1) {
				circleRadius = circleRadius - 40;
				drawLineInOuterCircle();
			} else if (i == 2) {
				writeTextInCircle(circleRadius, 6);
				circleRadius = circleRadius - 30;
			} else if (i == 3) {
				writeTextInCircle(circleRadius, 5);
				circleRadius = circleRadius - 30;
			} else if (i == 4) {
				writeTextInCircle(circleRadius, 4);
				circleRadius = circleRadius - 30;
			} else if (i == 5) {
				writeTextInCircle(circleRadius, 3);
				circleRadius = circleRadius - 30;
			} else if (i == 6) {
				writeTextInCircle(circleRadius, 2);
				circleRadius = circleRadius - 30;
			} else if (i == 7) {
				writeTextInCircle(circleRadius, 1);
				circleRadius = circleRadius - 30;
			} else {
				circleRadius = circleRadius - 30;
			}
		}
		showSignOnPlanet(forward);
	}

	function drawLineInInnerCircle() {
		var interval = 30;
		var arcSize = degreesToRadians(interval);
		var i = 0;
		for ( var startingAngle = 0; startingAngle <= 360;) {
			context.beginPath();
			context.moveTo(centerX, centerY);
			context.arc(centerX, centerY, radius,
					degreesToRadians(startingAngle),
					degreesToRadians(startingAngle) + arcSize, false);
			context.closePath();
			context.stroke();
			context.restore();
			startingAngle = startingAngle + interval;
			i++;
		}
	}

	function drawLineInOuterCircle() {

		var outerAngleInterval = 13.50;
		var arc = degreesToRadians(outerAngleInterval);
		var i = 0;
		for ( var startingAngle = 0; startingAngle < 360;) {
			context.beginPath();
			context.moveTo(centerX, centerY);
			if (i % 2 == 0) {
				context.fillStyle = "Tan";
			} else {
				context.fillStyle = "Silver";
			}
			context.arc(centerX, centerY, radius,
					degreesToRadians(startingAngle),
					degreesToRadians(startingAngle) + arc, false);
			context.fill();
			context.save();
			context.fillStyle = "black";
			context.translate(centerX, centerY);
			context.rotate(degreesToRadians(startingAngle) + arc / 2);
			context.textAlign = "center";
			context.textBaseline = "top";
			context.translate(radius - 20, 0);
			context.rotate(degreesToRadians(90));
			//context.fillText(outerLayer[i], 0, 0);
			context.restore();
			startingAngle = startingAngle + outerAngleInterval;
			i++;
		}
	}

	function writeTextInCircle(circleRadius, layer) {
		var outerAngleInterval = 30;
		var arc = degreesToRadians(outerAngleInterval);
		var i = 0;
		for ( var startingAngle = 0; startingAngle < 360;) {
			context.beginPath();
			context.moveTo(centerX, centerY);
			var value;
			if (layer == 6) {
				if (i % 2 == 0) {
					context.fillStyle = "WhiteSmoke";
				} else {
					context.fillStyle = "PeachPuff";
				}
				value = layer6[i];
			} else if (layer == 5) {
				if (i % 2 == 0) {
					context.fillStyle = "pink";
				} else {
					context.fillStyle = "grey";
				}
				value = layer5[i];
			} else if (layer == 4) {
				if (i % 2 == 0) {
					context.fillStyle = "lightblue";
				} else {
					context.fillStyle = "green";
				}
				value = layer4[i];
			} else if (layer == 3) {
				if (i % 2 == 0) {
					context.fillStyle = "pink";
				} else {
					context.fillStyle = "grey";
				}
				value = layer3[i];
			} else if (layer == 2) {
				if (i % 2 == 0) {
					context.fillStyle = "LightSeaGreen";
				} else {
					context.fillStyle = "white";
				}
				value = layer2[i];
			} else if (layer == 1) {
				if (i % 2 == 0) {
					context.fillStyle = "lightPink";
				} else {
					context.fillStyle = "grey";
				}
				value = layer1[i];
			}
			context.arc(centerX, centerY, circleRadius,
					degreesToRadians(startingAngle),
					degreesToRadians(startingAngle) + arc, false);
			context.fill();
			context.save();
			context.fillStyle = "black";
			context.translate(centerX, centerY);
			context.rotate(degreesToRadians(startingAngle) + arc / 2);
			context.textAlign = "center";
			context.textBaseline = "top";
			context.translate(circleRadius - 7, 0);
			context.rotate(degreesToRadians(90));
			context.fillText(value, 0, 0);
			context.restore();
			startingAngle = startingAngle + outerAngleInterval;
			i++;
		}
	}

	function showSignOnPlanet(forward) {
		var signName = 'Leo';
		var angleDegree = 5;
		if (signArr[signName] != null) {
			var angle = 0;
			if (forward) {
				alert(signArr[signName]);
				angle = signArr[signName] * 30 - 90;
				alert(angle);
			}
			angle = angle + angleDegree;
			var arcSize = degreesToRadians(angle);
			context.beginPath();
			context.moveTo(centerX, centerY);
			context.arc(centerX, centerY, radius + 20, degreesToRadians(angle),
					degreesToRadians(angle), false);
			context.closePath();
			context.strokeStyle = 'blue';
			context.stroke();
			context.save();
			context.fillStyle = "black";
			context.translate(centerX, centerY);
			context.rotate(degreesToRadians(angle));
			context.textAlign = "center";
			context.textBaseline = "top";
			context.translate(radius + 35, 0);
			context.rotate(degreesToRadians(90));
			context.font = "16px sans-serif";
			context.fillText("sun", 0, 0);
			context.restore();
		}
	}

	function degreesToRadians(degrees) {
		return (degrees * Math.PI) / 180;
	}
</script>

<div class="row">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<div class="col-sm-12" id="innerHeadingDiv">
			<div class="container-fluid"
				style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
				<div class="row-fluid">
					<div class="span8">
						<p class="header" align="center">Kundli Circle</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>
<div class="container-fluid">
	<div class="container">
		<div class="row">
			<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12">
				<canvas id="myCanvas" width="1200" height="580"></canvas>
			</div>
		</div>
	</div>
</div>
