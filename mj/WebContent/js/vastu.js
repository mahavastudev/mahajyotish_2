var centerX, centerY, context, canvas;
var forward = true;
var radius;
var Ylength = 0;
var isOverlap = 0;
var angleArr = [];
var angleMap = {};
var previous = 0;

var outerLayer = [ "SA", "ME", "KE", "VE", "SU", "MO", "MA", "RA", "JU", "SA",
		"ME", "KE", "VE", "SU", "MO", "MA", "RA", "JU", "SA", "ME", "KE", "VE",
		"SU", "MO", "MA", "RA", "JU" ];
var outerLayerColor = [ "#9bd6da", "#add3bd", "#ceb4aa", "#ffefd4", "#fac494",
		"#aee3e8", "#f7a395", "#959494", "#fff999", "#9bd6da", "#add3bd",
		"#ceb4aa", "#ffefd4", "#fac494", "#aee3e8", "#f7a395", "#959494",
		"#fff999", "#9bd6da", "#add3bd", "#ceb4aa", "#ffefd4", "#fac494",
		"#aee3e8", "#f7a395", "#959494", "#fff999" ];
var layer6Color = [ "#aee3e8", "#fac494", "#add3bd", "#ffefd4", "#f7a395",
		"#fff999", "#9bd6da", "#9bd6da", "#fff999", "#f7a395", "#ffefd4",
		"#add3bd" ];
var layer6 = [ "MO", "SU", "ME", "VE", "MA", "JU", "SA", "SA", "JU", "MA",
		"VE", "ME" ];
var layer5Color = [ "#aee3e8", "#fac494", "#add3bd", "#ffefd4", "#f7a395",
		"#fff999", "#9bd6da", "#9bd6da", "#fff999", "#f7a395", "#ffefd4",
		"#add3bd" ];
var layer5 = [ "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Saggi", "Capri",
		"Aqua", "Pisces", "Aries", "Taurus", "Gemini" ];
var layer4Color = [ "#e1ba9e", "#cbccce", "#ffffff", "#e1ba9e", "#cbccce",
		"#ffffff", "#e1ba9e", "#cbccce", "#ffffff", "#e1ba9e", "#cbccce",
		"#ffffff" ];
var layer4 = [ "Rajas", "Tamas", "Satav", "Rajas", "Tamas", "Satav", "Rajas",
		"Tamas", "Satav", "Rajas", "Tamas", "Satav" ];
var layer3Color = [ "#aee3e8", "#f7a395", "#fff999", "#add3bd", "#aee3e8",
		"#f7a395", "#fff999", "#add3bd", "#aee3e8", "#f7a395", "#fff999",
		"#add3bd" ];
var layer3 = [ "Water", "Fire", "Earth", "Air", "Water", "Fire", "Earth",
		"Air", "Water", "Fire", "Earth", "Air" ];
var layer2Color = [ "#dadcdc", "#bbbdbe", "#dadcdc", "#bbbdbe", "#dadcdc",
		"#bbbdbe", "#dadcdc", "#bbbdbe", "#dadcdc", "#bbbdbe", "#dadcdc",
		"#bbbdbe" ];
var layer2 = [ "Prakriti", "Purush", "Prakriti", "Purush", "Prakriti",
		"Purush", "Prakriti", "Purush", "Prakriti", "Purush", "Prakriti",
		"Purush" ];
var layer1Color = [ "#aee3e8", "#fac494", "#add3bd", "#ffefd4", "#f7a395",
		"#fff999", "#9bd6da", "#9bd6da", "#fff999", "#f7a395", "#ffefd4",
		"#add3bd" ];
var layer1 = [ "4", "5", "6", "7", "8", "9", "10", "11", "12", "1", "2", "3" ];

var planetArr = {
	"Moon" : "Mo",
	"Ketu" : "Ke",
	"Mercury" : "Me",
	"Sun" : "Su",
	"Venus" : "Ve",
	"Mars" : "Ma",
	"Jupiter" : "Ju",
	"Saturn" : "Sa",
	"Rahu" : "Ra",
	"Uranus" : "Ur",
	"Neptune" : "Np",
	"Pluto" : "Pl"
};

var houseArr = {
	1 : "I",
	2 : "II",
	3 : "III",
	4 : "IV",
	5 : "V",
	6 : "VI",
	7 : "VII",
	8 : "VIII",
	9 : "IX",
	10 : "X",
	11 : "XI",
	12 : "XII"
};

var signArr = {
	"Aries" : 0,
	"Taurus" : 1,
	"Gemini" : 2,
	"Cancer" : 3,
	"Leo" : 4,
	"Virgo" : 5,
	"Libra" : 6,
	"Scorpio" : 7,
	"Sagittarius" : 8,
	"Capricorn" : 9,
	"Aquarius" : 10,
	"Pisces" : 11
};

var transitHouseCls = {
	1 : "rs4",
	2 : "rs1",
	3 : "rs3",
	4 : "rs6",
	5 : "rs8",
	6 : "rs11",
	7 : "rs9",
	8 : "rs12",
	9 : "rs10",
	10 : "rs7",
	11 : "rs5",
	12 : "rs2"
};

var plntArrColr = {
	"Moon" : "#aee3e8",
	"Sun" : "#fac494",
	"Mercury" : "#add3bd",
	"Venus" : "#ffefd4",
	"Mars" : "#f7a395",
	"Jupiter" : "#fff999",
	"Saturn" : "#9bd6da",
	"Ketu" : "#ceb4aa",
	"Rahu" : "#959494"
};

var planetMap = {};

function drawCircle1() {
	//console.log("inside drawCircle1");
	showCuspDetail(forward);
	angleArr = [];
	angleMap = {};
	showHouseDetailOnPlanet(forward);

}

function drawCircle() {
	var circleRadius = radius;
	drawLineInInnerCircle();
	for (var i = 1; i < 9; i++) {
		if (i == 1) {
			circleRadius = circleRadius - 45;
			drawLineInOuterCircle();
		} else if (i == 2) {
			writeTextInCircle(circleRadius, 6);
			circleRadius = circleRadius - 30;
		} else if (i == 3) {
			writeTextInCircle(circleRadius, 5);
			circleRadius = circleRadius - 30;
		} else if (i == 4) {
			/*	writeTextInCircle(circleRadius, 4);
				circleRadius = circleRadius - 30;*/
		} else if (i == 5) {
			/*    if(radius>200){
			       writeTextInCircle(circleRadius, 3);
			       circleRadius = circleRadius - 30;
			    }*/
		} else if (i == 6) {
			/* if(radius>200){
			    writeTextInCircle(circleRadius, 2);
			    circleRadius = circleRadius - 30;
			 }*/
		} else if (i == 7) {
			/* if(radius>200){
			   writeTextInCircle(circleRadius, 1);
			   circleRadius = circleRadius - 30;
			 }*/
		} else {
			circleRadius = circleRadius - 30;
		}
	}
	showCuspDetail(forward);
	showHouseDetailOnPlanet(forward);
}

function drawLineInInnerCircle() {
	var interval = 30;
	var arcSize = degreesToRadians(interval);
	var i = 0;
	for (var startingAngle = 0; startingAngle <= 360;) {
		context.beginPath();
		context.moveTo(centerX, centerY);
		context.arc(centerX, centerY, radius, degreesToRadians(startingAngle),
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
	var tempRad = radius - 40;
	var tempAngle = 0;
	//	var tempY=centerY-100;
	for (var startingAngle = 0; startingAngle < 360;) {
		context.beginPath();
		context.moveTo(centerX, centerY);
		tempAngle = tempAngle + outerAngleInterval;
		//context.lineTo(tempAngle,40);
		context.fillStyle = outerLayerColor[i];
		context.arc(centerX, centerY, radius, degreesToRadians(startingAngle),
				degreesToRadians(startingAngle) + arc, false);
		//	context.stroke();
		//		context.fillStyle="White";
		context.fill();
		context.save();
		context.fillStyle = "black";
		context.translate(centerX, centerY);
		context.rotate(degreesToRadians(startingAngle) + arc / 2);
		context.textAlign = "center";
		context.textBaseline = "top";
		context.translate(radius - 20, 0);
		context.rotate(degreesToRadians(90));
		context.fillText(outerLayer[i], 0, 0);
		context.restore();

		startingAngle = startingAngle + outerAngleInterval;
		i++;
	}
}

function writeTextInCircle(circleRadius, layer) {
	var outerAngleInterval = 30;
	var arc = degreesToRadians(outerAngleInterval);
	var i = 0;
	for (var startingAngle = 0; startingAngle < 360;) {
		context.beginPath();
		context.moveTo(centerX, centerY);
		var value;
		if (layer == 6) {
			context.fillStyle = layer6Color[i];
			value = layer6[i];
		} else if (layer == 5) {
			context.fillStyle = layer5Color[i];
			value = layer5[i];
		} else if (layer == 4) {
			//		context.fillStyle = layer4Color[i];
			value = layer4[i];
		} else if (layer == 3) {
			//	context.fillStyle = layer3Color[i];
			value = layer3[i];
		} else if (layer == 2) {
			//	context.fillStyle = layer2Color[i];
			value = layer2[i];
		} else if (layer == 1) {
			//	context.fillStyle = layer1Color[i];
			value = layer1[i];
		}
		context.arc(centerX, centerY, circleRadius,
				degreesToRadians(startingAngle),
				degreesToRadians(startingAngle) + arc, false);
		//	context.stroke();
		context.fill();
		context.save();
		//		//context.clearRect(0,0,width,height);
		context.fillStyle = "black";
		context.translate(centerX, centerY);
		context.rotate(degreesToRadians(startingAngle) + arc / 2);
		context.textAlign = "center";
		context.textBaseline = "top";
		context.translate(circleRadius - 7, 0);
		context.rotate(degreesToRadians(90));

		context.font = "12px sans-serif";
		context.fillText(value, 0, 0);
		context.restore();
		startingAngle = startingAngle + outerAngleInterval;
		i++;
	}
}

function showCuspDetail1(forward) {
	var x = 375;
	var y = 390;

	context.beginPath();
	context.moveTo(x, y);
	context.lineTo(x, 300);
	context.stroke();
	context.save();

}

function showCuspDetail(forward) {
	var table = document.getElementById("planetTable");
	rowCount = table.rows.length;
	var lineRadius = radius + 75;
	//var lineRadius=radius + 25;
	var textRadius = radius + 80;
	var aspectStr = "";
	var psign = "";
	for (var i = 1; i < rowCount; i++) {
		var pname = $("#pl" + i).html();
		psign = planetArr[pname];

		for (var j = 2; j <= 8; j++) {
			if ($("#tdPlanetAspect" + j + pname).html() != "") {
				// aspectStr=aspectStr+planetArr[$("#tdPlanetAspect"+j+"0").html()]+" "+$("#tdPlanetAspect"+j+pname).html()+",";
				var tempstr = $("#tdPlanetAspect" + j + pname).html()
						.split("(");
				aspectStr = aspectStr
						+ planetArr[$("#tdPlanetAspect" + j + "0").html()]
						+ " " + tempstr[0] + ",";

			}
		}
		
		//Edited by gaurav Sharma to sort on degree on descending order on 14 June 2020
		aspectStr=getSortAspectString(aspectStr);

		var sign = $("#sgn" + i).html();
		var time = $("#deg" + i).html();
		var timeArr = time.split(":");
		var degree = convertTimeInDegree(time);
		if (pname != "") {
			if (signArr[sign] != null) {
				var pos = parseInt(signArr[sign]);
				angle = (pos * 30) - 90;
				if (angle < 0) {
					angle = angle + 360;
				}
				angle = angle + degree;
				var arcSize = degreesToRadians(angle);
				context.beginPath();
				context.moveTo(centerX, centerY);
				context.setLineDash([ 0, 0 ]);
				if (i > 1)
					checkAngle(angle);
				if (isOverlap == 1) {
					//		if(Ylength!=0){
					/*lineRadius=lineRadius+(Ylength*13)+20;
					textRadius=textRadius+(Ylength*13)+20;*/
					//edited By gaurav Sharma rotate circle anticlock wise on 18 June 2020, add two new lines , due to red line going out of canvas
					/*lineRadius = Number(previous) + 5;
					textRadius = Number(previous) + 5;*/
					lineRadius = Number(previous) + 0;
					textRadius = Number(previous) + 0;
					/*		}	
							else{
								lineRadius=lineRadius+20;
					                                textRadius=textRadius+20;
							
							}*/
				} else {
					lineRadius = radius + 75;
					textRadius = radius + 80;

				}
				previous = lineRadius;
				context
						.arc(centerX, centerY, lineRadius,
								degreesToRadians(angle),
								degreesToRadians(angle), false);
				angleArr.push(angle);
				context.strokeStyle = 'red';
				context.closePath();
				context.stroke();
				context.save();
				context.translate(centerX, centerY);
				context.rotate(degreesToRadians(angle));
				context.translate(textRadius, 0);
				context.rotate(degreesToRadians(90));

				// context.arc(0,0, 10,0, 2 * Math.PI, false);
				context.textAlign = "center";
				//  context.textBaseline = "top";
				//       context.translate(textRadius, 0);
				context.fillStyle = "red";
				context.font = "16px sans-serif";
				var text = psign + "(" + timeArr[0] + ":" + timeArr[1] + "/"
						+ (pos + 1) + ")";
				// context.fillText(text, 0, 0);
				context.fillText(psign, 0, 0);
				if (aspectStr != "") {
					var temp = aspectStr.split(",");
					var y = -13;
					context.fillStyle = "black";
					context.font = "14px sans-serif";
					for (var k = 0; k < temp.length - 1; k++) {
						context.fillText(temp[k] + String.fromCharCode(176), 0,
								y);
						y = y - 13;
					}
					y = Math.abs(y);
					previous = Number(previous) + Number(y);
					angleMap[angle] = [ temp.length - 1 + "_" + previous ];
				} else {
					previous = Number(previous) + 20;
					angleMap[angle] = [ 0 + "_" + previous ];
				}
				context.restore();

				/*  context.fillStyle = plntArrColr[pname];
				  context.fill();

				  context.restore();*/
			}
		}
		aspectStr = "";
		isOverlap = 0;
		Ylength = 0;
		lineRadius = radius + 75;
		textRadius = radius + 80;
		previous = 0;
	}
}

function checkAngle(angle) {
	var maxLength = 0;
	var tempMap = {};
	var count = 0;
	for (var i = 0; i < angleArr.length; i++) {
		if (angleArr[i] != angle) {
			var testVar = angleArr[i] - angle;
			testVar = Math.abs(testVar);
			if (testVar < 14) {
				var temp = angleMap[angleArr[i]];
				var tempArr = temp.toString().split("_");
				if (previous < temp.toString().split("_")[1]) {
					previous = temp.toString().split("_")[1];
					maxLength = temp.toString().split("_")[0];
				}
				isOverlap = 1;
				Ylength = maxLength;

			}
		}
	}
	return true;

}

function showHouseDetailOnPlanet(forward) {
	var table = document.getElementById("cuspDetailTabl");
	rowCount = table.rows.length;
	var house = "";
	var aspectStr = "";
	var lineRadius = radius + 10, textRadius = radius + 25;
	for (var i = 0; i < rowCount - 1; i++) {
		var houseNO = $("#house" + i).html();
		var sign = $("#sign" + i).html();
		var time = $("#degree" + i).html();
		house = houseArr[houseNO];
		for (var j = 1; j <= 7; j++) {
			if ($("#tdHouseAspect" + j + houseNO).html() != "") {
				//aspectStr=aspectStr+planetArr[$("#tdHouseAspect"+j+"0").html()]+" "+$("#tdHouseAspect"+j+houseNO).html()+",";
				var tempStr = $("#tdHouseAspect" + j + houseNO).html().split(
						"(");
				aspectStr = aspectStr
						+ planetArr[$("#tdHouseAspect" + j + "0").html()] + " "
						+ tempStr[0] + ",";

			}
		}

		//Edited by gaurav Sharma to sort on degree on descending order on 14 June 2020
		aspectStr=getSortAspectString(aspectStr);
		
		var degree = convertTimeInDegree(time);
		var timeArr = time.split(":");

		if (signArr[sign] != null) {
			var pos = parseInt(signArr[sign]);
			angle = (pos * 30) - 90;
			if (angle < 0) {
				angle = angle + 360;
			}
			angle = angle + degree;
			degree = (degree).toFixed();
			planetMap[houseNO] = angle;
			var arcSize = degreesToRadians(angle);
			context.beginPath();
			context.moveTo(centerX, centerY);
			context.setLineDash([ 0, 0 ]);
			context.lineWidth = 1;
			context.arc(centerX, centerY, lineRadius, degreesToRadians(angle),
					degreesToRadians(angle), false);
			context.strokeStyle = 'blue';
			context.closePath();
			context.stroke();
			context.save();
			context.fillStyle = "blue";
			context.translate(centerX, centerY);
			context.rotate(degreesToRadians(angle));
			context.textAlign = "center";
			context.textBaseline = "top";
			context.translate(textRadius, 0);
			context.rotate(degreesToRadians(90));
			context.font = "18px sans-serif";
			var text = house + "(" + timeArr[0] + ":" + timeArr[1] + "/"
					+ (pos + 1) + ")";
			//context.fillText(text, 0, 0);
			context.fillText(house, 0, 0);
			if (aspectStr != "") {
				var temp = aspectStr.split(",");
				var y = -17;
				context.fillStyle = "black";
				context.font = "14px sans-serif";
				for (var k = 0; k < temp.length - 1; k++) {

					context.fillText(temp[k] + String.fromCharCode(176), 0, y);
					y = y - 13;
				}
			}
			context.restore();
		}
		aspectStr = "";
	}

}

function showTransitPlanetDetail(forward) {
	var table = document.getElementById("transitPlanetTable");
	rowCount = table.rows.length;
	var psign = "";
	//var lineRadius=radius + 50,textRadius=radius + 58;
	var lineRadius = radius + 130, textRadius = radius + 138;
	for (var i = 1; i < rowCount; i++) {
		var pname = $("#transitPl" + i).html();
		psign = planetArr[pname];
		var sign = $("#transitSgn" + i).html();
		var time = $("#transitDeg" + i).html();
		var degree = convertTimeInDegree(time);
		if (pname != "") {
			if (signArr[sign] != null) {
				var pos = parseInt(signArr[sign]);
				angle = (pos * 30) - 90;
				if (angle < 0) {
					angle = angle + 360;
				}
				angle = angle + degree;
				var arcSize = degreesToRadians(angle);
				context.beginPath();
				context.moveTo(centerX, centerY);
				context.setLineDash([ 5, 15 ]);
				context
						.arc(centerX, centerY, lineRadius,
								degreesToRadians(angle),
								degreesToRadians(angle), false);
				context.strokeStyle = 'black';
				context.closePath();
				context.stroke();
				context.save();
				context.fillStyle = "black";
				//	context.fillStyle = plntArrColr[pname];
				context.translate(centerX, centerY);
				context.rotate(degreesToRadians(angle));
				context.translate(textRadius, 0);
				context.rotate(degreesToRadians(90));

				context.textAlign = "center";
				context.fillStyle = "black";
				context.font = "16px sans-serif";
				context.fillText(psign, 0, 0);
				//      context.arc(0,0, 10,0, 2 * Math.PI, false);
				//    	context.fillStyle = plntArrColr[pname];
				// context.fill();
				context.restore();
			}
		}
	}
}

function calculateTransitPlanetDetails() {
	var table = document.getElementById("transitPlanetTable");
	rowCount = table.rows.length;
	for (var i = 1; i < rowCount; i++) {
		var pname = $("#transitPl" + i).html();
		var sign = $("#transitSgn" + i).html();
		var time = $("#transitDeg" + i).html();
		var degree = convertTimeInDegree(time);
		if (signArr[sign] != null) {
			var pos = parseInt(signArr[sign]);
			angle = (pos * 30) - 90;
			angle = angle + degree;
			if (angle < 0) {
				angle = angle + 360;
			}
			var e_houseAngle = 0;
			for (var j = 1; j <= 12; j++) {
				var s_houseAngle = planetMap[j];
				if (j < 12) {
					e_houseAngle = planetMap[j + 1];
				}
				if (j == 12) {
					e_houseAngle = planetMap[1];
				}
				if (s_houseAngle < e_houseAngle) {
					if (s_houseAngle <= angle && e_houseAngle > angle) {
						var transDetail = $(
								"." + transitHouseCls[j] + ">.ctrans").html();
						$("." + transitHouseCls[j] + ">.ctrans").html(
								transDetail + " " + pname.substring(0, 2)
										+ "<sup>T</sup>");
						break;
					}
				} else {
					if ((s_houseAngle <= angle && 360 >= angle)
							|| (angle >= 0 && angle < e_houseAngle)) {
						var transDetail = $(
								"." + transitHouseCls[j] + ">.ctrans").html();
						$("." + transitHouseCls[j] + ">.ctrans").html(
								transDetail + " " + pname.substring(0, 2)
										+ "<sup>T</sup>");
						break;
					}

				}

			}
		}
	}
}

function drawTransitCircle() {
	context.beginPath();
	context.setLineDash([ 5, 15 ]);
	context.arc(centerX, centerY, radius + 120, 0, 2 * Math.PI, false);
	context.strokeStyle = "black";
	context.stroke();
}

function showPlanet() {
	var x = 20;
	var y = 20;
	for ( var key in plntArrColr) {
		listcontext.beginPath();
		listcontext.arc(x, y, 8, 0, 2 * Math.PI, false);
		//      context.font = "bold 20px Arial";
		//    context.fillText(key,x+20,y+5);
		listcontext.fillStyle = plntArrColr[key];
		listcontext.fill();
		listcontext.save();
		listcontext.fillStyle = "black";
		listcontext.font = "bold 16px Arial";
		listcontext.fillText(key, x + 13, y + 5);
		listcontext.restore();
		x = x + 85;
	}
}

function convertTimeInDegree(time) {
	var degree = time.split(':');
	var decDegrees = 0.0;
	var sec = 0.0;
	var min = 0.0;
	var deg = 0.0;
	min = (degree[1] * (1.0 / 60.0));
	sec = (degree[2] * (1.0 / 3600.0));
	deg = degree[0];
	decDegrees = parseFloat(deg) + parseFloat(min) + parseFloat(sec);
	return decDegrees;
}

function degreesToRadians(degrees) {
	return (degrees * Math.PI) / 180;
}

function getSortAspectString(aspectStr){
	//console.log('aspectStr ['+aspectStr+']');
	if(aspectStr!=""){
		aspectStr = aspectStr.substring(0, aspectStr.length - 1).split(",");
		var arr=[];
		for(i=0 ; i<aspectStr.length ; i++){
			//word =
			arr.push({"data":aspectStr[i], "degree":aspectStr[i].split(" ")[1]});
		}
		arr.sort(function (a, b) {
			  return a.degree - b.degree;
		});
		
		var newAspectString="" ;
		arr.forEach(function(item){
			newAspectString = newAspectString+","+item.data;
		});
		//console.log('newAspectString ['+newAspectString+']');
		newAspectString = newAspectString.substring(1, newAspectString.length);
		return newAspectString+",";
	}
	return aspectStr;
}