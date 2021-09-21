<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML>
<s:hidden id="linkId" name="id" value="%{id}"></s:hidden>

<s:hidden id="tob" value="%{#session.tob}"></s:hidden>

	<script type="text/javascript" src="js/vastu_anticlockwise.js"></script>
	<s:set name="isCircleClockWise" value="43"></s:set>


<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript">

var level=1;
var id=0;
var planetLevel='';
$(document).ready(function(){
		var astroHouseTable = document.getElementById('astroHouseTabl').value;
        var astroPlanetTable = document.getElementById('astroPlanetTabl').value;
        $("#cuspAspectTable").html(astroHouseTable);
        $("#cuspPlanetTable").html(astroPlanetTable);
                
		$("#click").click(function(){
			console.log("###### clicked #######");
			var c = document.getElementById("myCanvas");
			var ctx = c.getContext("2d");
			ctx.beginPath();
			ctx.arc(95,50,40,0,2*Math.PI);
			ctx.stroke();
			var img    = c.toDataURL("image/png");
			document.getElementById("baseImage").value=img.split(",")[1];
			var datastring = $("#CropImageSend").serialize();
			$.ajax({
				type: "post",
				url: "ConvertToImage",
		        dataType: 'text/html',
		        data: datastring,
	          	success:   function(response) {
	            	alert("Success----->"+response);
	            },error: function() {                }
	
	        });
		});

		var isBTR='<s:property value="%{#session.isBTR}"></s:property>';
		//	alert("isBTR "+isBTR);
		
	
		$("#goBackMain").click(function(){
			$("#vimshottariTable").show();
			$("#vimshottariTableInner").hide();
			$("#vimshottariHeader").html("");
			$("#plntHeader").html("");
			$("#vimshottariHeader").html("Main Dasha");
			for(var i=0;i<9;i++) {
				$("#antar"+i).hide();
			}
			level=1;
		}); 
	
		var tid;	
	
		$('#vimshottariTable').on('click', '.clickable-row', function(){
		 	tid=$(this).attr("id");
		 	console.log("inside vimshottariTable");
		 	console.log("tid==>"+tid);
		 	$("#vimshottariTable").hide();
			$("#vimshottariHeader").html("");
			$("#vimshottariHeader").html("Antar Dasha");
			$(".antarTable").hide();
			planetLevel=$(".td1"+tid).html();
			console.log("planetLevel==>"+planetLevel);
			$("#plntHeader").html("");
	        $("#plntHeader").html(planetLevel);
			for(var i=0;i<9;i++){
				$("#antar"+i).hide();
			}
			$("#antar"+tid).show();
			level=level+1;

	 	});
	

		$('#vimshottariTableInner').on('click', '.clickable-row', function(){
			id=$(this).attr("id");
		 	var strt=$(".vtd2"+id).html();
			var end=$(".vtd3"+id).html();
			var plnt=$(".vtd1"+id).html();
			getDasha(id,strt,end,plnt);
		});
	
		$('.antarTable').on('click', '.clickable-row', function(){
			id=$(this).attr("id");
			var strt=$("#atd"+tid+id+"1").html().trim();
			var end=$("#atd"+tid+id+"2").html().trim();
			var plnt=$("#atd"+tid+id+"0").html().trim();
			if(strt!="" && strt!=null && end!="" && end!=null)
			{
				$(".antarTable").hide();
				$("#vimshottariTableInner").show();
				console.log('id['+id+'] ,start ['+strt+'], end ['+end+'], plnt['+plnt+']');
				getDasha(id,strt,end,plnt);
			}
		});
	 
	
		var id=$("#linkId").val();

		if(id==11){
			$("#vastuCircle").hide();
			$("#cuspalChart").hide();
			$("#LagnaChart").show();
			$("#dashaDetail").hide();
			$("#planetDetail").hide();
                        $("#aspectChart").hide();
			$("#Pdf").hide();
		}
		if(id==13){
			$("#vastuCircle").hide();
			$("#cuspalChart").show();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
			$("#planetDetail").hide();
            $("#aspectChart").hide();
			$("#Pdf").hide();
		}
		if(id==14){
			$("#vastuCircle").hide();
			$("#cuspalChart").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").show();
			$("#planetDetail").hide();
            $("#aspectChart").hide();
			$("#Pdf").hide();
		}
		
		
		
		if(id==12){
			$("#vastuCircle").hide();
			$("#cuspalChart").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
			$("#planetDetail").show();
             $("#aspectChart").hide();
			$("#Pdf").hide();
		}
		
		if(id==15){
			$("#vastuCircle").hide();
			$("#cuspalChart").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
			$("#planetDetail").hide();
            $("#aspectChart").hide();
			$("#Pdf").show();
		}
		
		if(id==16){
			$("#vastuCircle").show();
			$("#cuspalChart").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
			$("#planetDetail").hide();
            $("#aspectChart").hide();
			$("#Pdf").hide();
		}
                
        if(id == 17){
                $("#vastuCircle").hide();
                $("#cuspalChart").hide();
                $("#LagnaChart").hide();
                $("#dashaDetail").hide();
                $("#planetDetail").hide();
                $("#Pdf").hide();
                $("#aspectChart").show();
         }
			
		$("#lagnaLi").click(function(){
			$("#headerChartBTR").hide();
            $("#headerChart").show();
			$("#vastuCircle").hide();
			$("#planetDetail").hide();
			$("#cuspalChart").hide();
			$("#mahaJyotishScriptCuspal").hide();
            $("#kundliChartCuspal").hide();
            $("#rahuKetuCuspal").hide();
            $("#cuspDetailDiv").hide();
			$("#LagnaChart").show();
			$("#dashaDetail").hide();
            $("#aspectChart").hide();
            $("#transitplanetCuspDetl").hide();
			$("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		
		});
	
		$("#CuspalLi").click(function(){
			$("#headerChartBTR").hide();
            $("#headerChart").show();
			$("#vastuCircle").hide();
			$("#planetDetail").hide();
			$("#cuspalChart").show();
			$("#mahaJyotishScriptCuspal").show();
            $("#kundliChartCuspal").show();
            $("#rahuKetuCuspal").show();
            $("#cuspDetailDiv").show();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
            $("#aspectChart").hide();
            $("#transitplanetCuspDetl").hide();
			$("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		});
	
		$("#BTRLi").click(function(){
			$("#headerChartBTR").show();
			$("#headerChart").hide();
        	$("#vastuCircle").hide();
        	$("#planetDetail").hide();
            $("#cuspalChart").show();
			$("#mahaJyotishScriptCuspal").hide();
			$("#kundliChartCuspal").hide();
			$("#rahuKetuCuspal").hide();
			$("#cuspDetailDiv").show();
            $("#LagnaChart").hide();
            $("#dashaDetail").hide();
            $("#aspectChart").hide();
            $("#transitplanetCuspDetl").hide();
            $("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
        });	

		$("#DashaLi").click(function(){
			$("#headerChartBTR").hide();
	        $("#headerChart").show();
			$("#vastuCircle").hide();
			$("#planetDetail").hide();
			$("#cuspalChart").hide();
			$("#mahaJyotishScriptCuspal").hide();
	        $("#kundliChartCuspal").hide();
	        $("#rahuKetuCuspal").hide();
	        $("#cuspDetailDiv").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").show();
	        $("#aspectChart").hide();
	        $("#transitplanetCuspDetl").hide();
			$("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		});
	

		$("#PlanetLi").click(function(){
			$("#vastuCircle").hide();
			$("#planetDetail").show();
			$("#headerChartBTR").hide();
	        $("#headerChart").show();
			$("#cuspalChart").hide();
			$("#mahaJyotishScriptCuspal").hide();
            $("#kundliChartCuspal").hide();
            $("#rahuKetuCuspal").hide();
            $("#cuspDetailDiv").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
            $("#aspectChart").hide();
           	$("#transitplanetCuspDetl").hide();
			$("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		});

		$("#PdfLi").click(function(){
			$("#headerChartBTR").hide();
		    $("#headerChart").show();
		   	$("#vastuCircle").hide();
			$("#planetDetail").hide();
			$("#cuspalChart").hide();
			$("#mahaJyotishScriptCuspal").hide();
            $("#kundliChartCuspal").hide();
            $("#rahuKetuCuspal").hide();
            $("#cuspDetailDiv").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
			$("#Pdf").show();
            $("#aspectChart").hide();
            $("#transitplanetCuspDetl").hide();
			$("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		});
		
		$("#kundliCircle").click(function(){
			$("#headerChartBTR").hide();
	        $("#headerChart").show();
		    $("#vastuCircle").show();
		   	$("#planetDetail").hide();
			$("#cuspalChart").hide();
			$("#mahaJyotishScriptCuspal").hide();
	        $("#kundliChartCuspal").hide();
	        $("#rahuKetuCuspal").hide();
	        $("#cuspDetailDiv").hide();
			$("#LagnaChart").hide();
			$("#dashaDetail").hide();
			$("#Pdf").hide();
	        $("#aspectChart").hide();
	        $("#transitplanetCuspDetl").hide();
			$("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		});
		
		$("#aspectCh").click(function(){
			$("#headerChartBTR").hide();
        	$("#headerChart").show();
      		$("#aspectChart").show();
           	$("#vastuCircle").hide();
           	$("#planetDetail").hide();
            $("#cuspalChart").hide();
			$("#mahaJyotishScriptCuspal").hide();
            $("#kundliChartCuspal").hide();
            $("#rahuKetuCuspal").hide();
            $("#cuspDetailDiv").hide();
            $("#LagnaChart").hide();
            $("#dashaDetail").hide();
            $("#Pdf").hide();
            $("#transitplanetCuspDetl").hide();
            $("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		});
	
		$("#transitPlntCuspDetl").click(function(){
			$("#headerChartBTR").hide();
	        $("#headerChart").show();
           	$("#aspectChart").hide();
           	$("#vastuCircle").hide();
           	$("#planetDetail").hide();
            $("#cuspalChart").hide();
			$("#mahaJyotishScriptCuspal").hide();
            $("#kundliChartCuspal").hide();
            $("#rahuKetuCuspal").hide();
            $("#cuspDetailDiv").hide();
            $("#LagnaChart").hide();
            $("#dashaDetail").hide();
            $("#Pdf").hide();
            $("#transitplanetCuspDetl").show();
            $("#common-panel").attr("class","ui-panel ui-panel-position-left ui-panel-display-overlay ui-body-a ui-panel-animate ui-panel-closed");
		});

		canvas = document.getElementById('myCanvas');
    	context = canvas.getContext('2d');
		var image = new Image();
		var isCircleClockWise='<s:property value="%{#isCircleClockWise}"></s:property>';
		console.log('isCircleClockWise ['+isCircleClockWise+']');
		
			image.src = "vastuImages/Chakra_anticlockwise.jpg";
		
		console.log('here ---------- ')
	
		/*isTransitKundli = "<s:property value='%{#session.isTransitKundli}'/>";
		if(isTransitKundli == 1){
	    		calculateTransitPlanetDetails();
		}*/
		image.onload = function() {
			context.drawImage(image, 200, 200,600,600);
			context.restore();
			centerX = canvas.width / 2;
			centerY =(canvas.height / 2);
			radius  = 270;
			drawCircle1();
			var clientName="<s:property value='%{#session.kundliName}'/>";
			//alert("aaaaaaaaaaaaaaaaaaaaaaaaaaaa"+%{#session.kundliName});
			context.font = "16px sans-serif";
			context.fillText(clientName,10,50);
			context.restore();
			var cn = document.getElementById('myCanvasList');
			listcontext = cn.getContext('2d');
			//drawCircle();
			//showPlanet();

			var img    = canvas.toDataURL("image/png");
         	//var listimg= cn.toDataURL("image/png");
            document.getElementById("baseImage").value=img.split(",")[1];
            //document.getElementById("listImage").value= listimg.split(",")[1];
			var dateTime =  new Date();
	        var month = dateTime.getMonth()+1;
	        var kundliCircleName="<s:property value='%{#session.userName}'/>"+"_post"+dateTime.getFullYear()+month+dateTime.getDate()+dateTime.getHours()+dateTime.getMinutes()+dateTime.getSeconds()+".jpg";
            document.getElementById("kundliCir").value=kundliCircleName;
            document.getElementById("kundliCirName").value=kundliCircleName;
			//==============================================================
			var isTransitKundli = "<s:property value='%{#session.isTransitKundli}'/>";
            document.getElementById("isTransitKundli").value=isTransitKundli;
            if(isTransitKundli == 1){
	            calculateTransitPlanetDetails();
	            drawTransitCircle();
	            showTransitPlanetDetail(forward);
	            $('#circleTransitKundli').attr('checked', true);
	
	            img    = canvas.toDataURL("image/png");
	            document.getElementById("baseTransitImage").value=img.split(",")[1];
			}
			//================================================================
            var datastring = $("#CropImageSend").serialize();
			//alert("datastring :: "+datastring);
			var result="";
			$.ajax({
                type: "post",
	 			url: "ConvertToImage",                       
				dataType: 'text/html',
				data: datastring,
				success:   function(data) {},
                error: function() {}

            });

		//============Image With Transit Details==========================
		/*	var isTransitKundli = "<s:property value='%{#session.isTransitKundli}'/>";
			document.getElementById("isTransitKundli").value=isTransitKundli;
			if(isTransitKundli == 1){
				calculateTransitPlanetDetails();
				drawTransitCircle();
	            		showTransitPlanetDetail(forward);
				$('#circleTransitKundli').attr('checked', true);	
	
				var img    = canvas.toDataURL("image/png");
	                	document.getElementById("baseImage").value=img.split(",")[1];
	
	                	var datastring = $("#CropImageSend").serialize();
	                	var result="";
	
	                  	$.ajax({
	
	                    	 	type: "post",
	                        	url: "ConvertToImage",
	                        	dataType: 'text/html',
	                        	data: datastring,
	                        	success:   function(data) {
	                		},
	                		error: function() {
	                		}
	
	                	});
			}//isTransit*/



		} /*image onload*/
		/*var isTransitKundli = "<s:property value='%{#session.isTransitKundli}'/>";
		if(isTransitKundli == 1){
		    calculateTransitPlanetDetails();
		}*/
		$("#circleTransitKundli").click(function () {
       		if ($(this).is(":checked")) {
           		drawTransitCircle();
            	showTransitPlanetDetail(forward);
            } else {
              	context.clearRect(0, 0, canvas.width, canvas.height);
	       		var image = new Image();
	       		
	       		
	    			image.src = "vastuImages/Chakra_anticlockwise.jpg";
	    		

                image.onload = function() {
                        context.drawImage(image, 200, 200,600,600);
                        drawCircle1();
				}
             	// drawCircle();
            }
        });


		var dob=document.getElementById("dob").value;
		var dobSplit=dob.split('-');
		document.getElementById("year").value=dobSplit[0];
		document.getElementById("month").value=dobSplit[1];
		document.getElementById("date").value=dobSplit[2];

		var tob=document.getElementById("tob").value;
        var tobSplit=tob.split(':');
        document.getElementById("hour").value=tobSplit[0];
        document.getElementById("minute").value=tobSplit[1];
        document.getElementById("second").value=tobSplit[2];

});   ///end of ready function
	
	/* function addURL(element)
        {
                $(element).attr('href', function() {
                	return this.href + '?kundliCircleName='+document.getElementById("kundliCir").value;
        	});
        } */
        function addURL()
        {
        	var kundliCircleName=$('#kundliCir').val();
        	var circleTransitKundli=$('#circleTransitKundli').val();
        	console.log("kundliCircleName==>"+kundliCircleName+" circleTransitKundli===>"+circleTransitKundli);	
        	url = "downloadKundliCircle?kundliCircleName="+kundliCircleName+"&circleTransitKundli="+circleTransitKundli;
        	console.log("url===>"+url);	
        	window.location=url;
        }


	function validate(){

		var D =document.getElementById('date').value;
        	var M =document.getElementById('month').value;
        	var Y = document.getElementById('year').value;
       	 	var date =D.length;
        	var month = M.length;
        	var year =  Y.length;
        	var hour =document.getElementById('hour').value.length;
        	var minute = document.getElementById('minute').value.length;
        	var second =  document.getElementById('second').value.length;
		if(D=="" || D==null){
			$("#alertMsg").html("<h5>Please enter date</h5>");
        		$("#openAlert").click();
        		return false;
		}
		if(M=="" || M==null){
                        $("#alertMsg").html("<h5>Please enter month</h5>");
                        $("#openAlert").click();
                        return false;
                }
		if(Y=="" || Y==null){
                        $("#alertMsg").html("<h5>Please enter year</h5>");
                        $("#openAlert").click();
                        return false;
                }
		if(D>31){
                        $("#alertMsg").html("<h5>Please enter valid date</h5>");
                        $("#openAlert").click();
                        return false;
                }
		if(M>12){
                        $("#alertMsg").html("<h5>Please enter valid month</h5>");
                        $("#openAlert").click();
                        return false;
                }
		if(year< 4){
        		$("#alertMsg").html("<h5>Please insert date in yyyy format</h5>");
        		$("#openAlert").click();
        		return false;

    		}
		 if(D=="00"){
        		$("#alertMsg").html("<h5>Date cannot be zero</h5>");
        		$("#openAlert").click();
        		return false;

    		}
     		if(M=="00"){
        		$("#alertMsg").html("<h5>Month cannot be zero</h5>");
        		$("#openAlert").click();
        		return false;

    		}
     		if(Y=="0000"){
        		$("#alertMsg").html("<h5>Year cannot be zero</h5>");
        		$("#openAlert").click();
        		return false;

    		}
		if(hour=="" || hour==null){
        		$("#alertMsg").html("<h5>Please enter hour</h5>");
        		$("#openAlert").click();
        		return false;
    		}

    		if(minute=="" || minute==null){
        		$("#alertMsg").html("<h5>Please enter minute</h5>");
        		$("#openAlert").click();
        		return false;
    		}
    		if(second=="" || second==null){
        		$("#alertMsg").html("<h5>Please enter second</h5>");
        		$("#openAlert").click();
        		return false;
    		}
		$("#showLoader").click();
   		return true;
	}	



function getDasha(id,strt,end,plnt)
{

	if(level<5)
	{
		planetLevel=planetLevel+"-"+plnt;
        $("#plntHeader").html("");
        $("#plntHeader").html(planetLevel);
		if(level==2)
		{
			$("#vimshottariHeader").html("");
			$("#vimshottariHeader").html("Pratyantar Dasha");
		}
		if(level==3)
		{		
			$("#vimshottariHeader").html("");
			$("#vimshottariHeader").html("Shukshma Dasha");
		}
		if(level==4)
		{		
			$("#vimshottariHeader").html("");
			$("#vimshottariHeader").html("Pran Dasha");		
		}
	
		//ajax hit and response
	 	var url = "?startTime="+strt.trim()+"&endTime="+end.trim()+"&parent="+plnt.trim()+"&planet="+plnt.trim();
        console.log("inside getDasha startTime="+strt+" end time="+end+" parent="+plnt+" planet="+plnt);
<%--  		url = "<%=response.encodeURL("getVimshottariDasha")%>"+url; --%>
		url = "getVimshottariDasha"+url;
		var json="";
		$.ajax({
 			url: url,
 			async: false,
 			success: function(result){
 			json=result;
 			console.log(result);
 			}
 		});
		var parsed = JSON.parse(json);
		var table = document.getElementById('vimshottariTableInner');
		$(table).empty();
		rowCount = table.rows.length;
		
		for(var x in parsed)
		{
  			//alert(parsed[x]);
  			//alert(parsed[x]["start"]);	
			var row = table.insertRow(rowCount);
			row.className="clickable-row";
			row.id=x;
			var cell1 = row.insertCell(0);
			cell1.innerHTML=parsed[x]["planet"];
			cell1.className="vtd1"+x;
			cell1.width="25%";
			cell1 = row.insertCell(1);
	  		cell1.innerHTML=parsed[x]["start"];
	  		cell1.className="vtd2"+x;
			cell1.width="25%";
	  		cell1 = row.insertCell(2);
	  		cell1.innerHTML=parsed[x]["end"];
	  		cell1.className="vtd3"+x;
			cell1.width="25%";
	  		rowCount=rowCount+1;
		}
		level=level+1;
	}
	
}
	


function sendEmail()
{
    var kundliImage=document.getElementById("kundliCirName").value;
    var email = document.getElementById("email").value;
    var specialKundli = true;
    var dashaDetail = false; 
    var houseDetail =false;
    var kundli = false;
    
    console.log("kundli==>"+kundli+"specialKundli==>"+specialKundli+"houseDetail==>"+houseDetail+"dashaDetail==>"+dashaDetail+"kundliImage==>"+kundliImage);
    var genType = document.getElementById("genType").value;
    if(email=="" || email == null)
    {
      $("#emaiAlertMsg").html("<h5>Please enter email</h5>");
      return false;
    }
	else
	{
      	var filter =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    	if (!filter.test(email)) 
	    {	
            $("#emaiAlertMsg").html("<h5>Please provide a valid email address</h5>");
	    return false;
   		}			
	}
    var url = "?genType="+genType+"&userInfo.email="+email+"&userInfo.dashaDetail="+dashaDetail+"&userInfo.houseDetail="+houseDetail+"&kundliCircleName="+kundliImage+"&userInfo.specialKundli="+specialKundli+"&userInfo.kundli="+kundli; 
    url = "<%=response.encodeURL("KundliActionGenerate.action")%>"+url;
    $("#closeEmailModel").click();
    $("#alertMsg").html("<h5>Kundli has been sent to you<h5>");
    $("#openAlert").click();
     $.ajax({
	    type: "POST",
	    url: url,
	    async: true,
	    success: function(result){
	    	var successUrl = "generateKundli.action"; 
	    	window.location.href = successUrl;
	    }
	    });

 
}	
	
</script>
<div style="margin: auto; width: 50%;padding: 50px;">
 

		<div class="modal-body" style="padding: 40px 50px;">
			<h5 id="emaiAlertMsg" style="color: red;"></h5>
			<div class="form-group">
				<label for="eamil"><span
					class="glyphicon glyphicon-envelope"></span> Enter Your Email</label>
				<%-- <s:textfield id="email" class="form-control"  value=""></s:textfield> --%>
				<input type="text" class="form-control" id="email" class="form-control"  value="">
			</div>


            <input type="hidden" id="genType" name="genType" value="E" />
			<button type="submit" class="btn btn-success btn-block"
				style="background-color: #FFA500; color: white;"
				onclick="return sendEmail()">
				<span class="glyphicon glyphicon-envelope"></span> Send
			</button>
		</div>
	</div>
	</div>











<div id="headerChart" style="margin-top: 100px;display:none;">

<div class="table-responsive"  id="tableDiv">
        <table id="dataTable" width="100%" border="0" cellspacing="0" border="1"
                cellpadding="0" class="ui-table ui-responsive">

     <thead>
     <tr>
         <th scope="col" width="25%">Name</th>
         <th scope="col" width="25%">DOB</th>
         <th scope="col" width="25%">TOB</th>
         <th scope="col" width="25%">POB</th>
     </tr>
     </thead>
     <tbody>
        <tr>
            <td><%= session.getAttribute("kundliName")%></td> 
            <td><%= session.getAttribute("dob")%></td>
            <td><%= session.getAttribute("tob")%></td>
            <td><%= session.getAttribute("pob")%></td>        
       </tr>
     </tbody>
</table>
</div>
</div>


<div id="headerChartBTR" style="margin-top: 100px; display:none;">
<div class="table-responsive"  id="tableDiv">
	<s:hidden id="dob" value="%{#session.dob}"></s:hidden>
	<s:hidden id="tob" value="%{#session.tob}"></s:hidden>
        <table id="dataTable" width="100%" border="0" cellspacing="0" border="1"
                cellpadding="0" class="ui-table ui-responsive">

     <thead>
     <tr>
         <th scope="col" width="20%">Name</th>
         <th scope="col" width="20%">DOB</th>
         <th scope="col" width="20%">TOB</th>
         <th scope="col" width="20%">POB</th>
	 <th width="20%"></th>
     </tr>
     </thead>
     <tbody>
        <tr>
	    <form id="formBtr" action="submitKundliDetailsBTR.action" data-ajax="false" method="post" onsubmit ="return validate()" >
	    <s:hidden id="reqId" name="generateKundli.requestId"></s:hidden>
	    <s:hidden id="isBTR" name="generateKundli.isBTR" value="Yes"></s:hidden>
	    <td><%= session.getAttribute("kundliName")%></td>
	    <td align="left">
		<s:textfield id="date" name="generateKundli.date" maxlength="4"  cssClass="dob" cssStyle ="width:42px; display:inline;font-size:12px;"></s:textfield>
                <s:textfield id="month" name="generateKundli.month" maxlength="2"  cssClass="dob" cssStyle ="width:42px; display:inline;font-size:12px;"></s:textfield>
                <s:textfield id="year" name="generateKundli.year" maxlength="4"  cssClass="dob" cssStyle ="width:60px; display:inline;font-size:12px;"></s:textfield>
            </td>

	    <td align="left">
		<s:textfield id="hour" name="generateKundli.hour" maxlength="2" cssClass="tob" cssStyle ="width:42px; display:inline;font-size:12px;"></s:textfield>
                <s:textfield id="minute" name="generateKundli.minute" maxlength="2"  cssClass="tob" cssStyle ="width:42px;display:inline;font-size:12px;"></s:textfield>
                <s:textfield id="second" name="generateKundli.second" maxlength="2"  cssClass="tob" cssStyle ="width:42px;display:inline;font-size:12px"></s:textfield>
	    </td>
            <td><%= session.getAttribute("pob")%></td>
	    <td><button class="form-control" style="background-color: #FFA500; color: white;width:100px;" id="update" type="submit">Update</button></td>
       	    </form>
	</tr>
     </tbody>
</table>
</div>
</div>


<div id="LagnaChart" style="display: none">
<div class="kundli-chart">
	<img class="img-responsive" src="vastuImages/kundli-box.png">
	
	<s:iterator value="birthKundli" id="birthKundli"
				status="iterStatus">
          <div class='rs<s:property value="#birthKundli.key"/>'>
                <span class='in<s:property value="#birthKundli.key"/>'> <s:property value="#birthKundli.value.signNumber"/></span> <strong><s:property value="#birthKundli.value.planetName"/></strong>
        <s:if test='%{#session.isTransitKundli=="1"}'>
                <s:if test='%{#birthKundli.value.transitPlanetName!=""}'>
                        <strong id="trans"> <s:property value="#birthKundli.value.transitPlanetName"/><sup>T</sup></strong>
                </s:if>
        </s:if>
        </div>
	</s:iterator>
</div>


	<h4 style="text-align: center;width: 100%">MahaJyotish Script</h4>
	<div class="table-responsive"  id="tableDiv" > 
	<table id="dataTable" width="100%" border="0" cellspacing="0"
		cellpadding="0" class="ui-table ui-responsive">
		<thead>
			<tr>
				<th scope="col" width="10%">Planet</th>
				<th scope="col" width="10%">Source</th>
				<th scope="col" width="15%">Result(NL)</th>
				<th scope="col" width="10%">Adtnl H</th>
				<th scope="col" width="20%">SL</th>
				<th scope="col" width="20%">NL(SL)</th>
				<th scope="col" width="15%">MDL upto(current)</th>
<!-- 				<th scope="col">MD upto</th> -->
			</tr>
		</thead>
		<tbody>
			<s:iterator value="astrobean.natalStrengthChart" id="myAstro"
				status="iterStatus">
				<tr>
					<s:iterator value="myAstro">
						<td><s:property />
						</td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
	
	<div class="table-responsive"  id="tableDiv">
	<table id="dataTable" width="100%" border="0" cellspacing="0"
		cellpadding="0" style="margin-top: 25px;" class="ui-tableui-responsive">
		<thead>
			<tr>
				<th scope="col" colspan="2">Rahu</th>
				<th scope="col" colspan="2">Ketu</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><s:property value="astrobean.rahuSource"/></td>
				<td><table width="100%" height="100%">
				<tr><td>A:<s:property value="astrobean.aspectRahu"/></td></tr>
				<tr><td>C:<s:property value="astrobean.conjucRahu"/></td></tr>
				<tr><td>S:<s:property value="astrobean.signLordRahu"/></td></tr>
				<tr><td>R:<s:property value="astrobean.rashiLordRahu"/></td></tr>
				</table></td>
				<td><s:property value="astrobean.ketuSource"/></td>
				<td><table  width="100%" height="100%">
				<tr><td>A:<s:property value="astrobean.aspectKetu"/></td></tr>
				<tr><td>C:<s:property value="astrobean.conjucKetu"/></td></tr>
				<tr><td>S:<s:property value="astrobean.signLordKetu"/></td></tr>
				<tr><td>R:<s:property value="astrobean.rashiLordKetu"/></td></tr>
				</table></td>
			</tr>
		</tbody>
	</table></div>

	<h4 style="margin-top: 50px;text-align: center;width: 100%" >Planet Details</h4>
	<div class="table-responsive"  id="tableDiv">
	<table id="dataTable" width="100%" border="0" cellspacing="0" border="1"
		cellpadding="0" class="ui-tableui-responsive">
		<thead>
			<tr>
				<th scope="col" width="12%">Planet</th>
				<th scope="col" width="12%">R/C</th>
				<th scope="col" width="12%">Sign</th>
				<th scope="col" width="12%">Degree</th>
				<s:if test='#session.containsKey("20")'>
					<th scope="col" width="12%">Nakshatra</th>
					<th scope="col" width="12%">Padam</th>
				</s:if>
				<th scope="col" width="12%">RL</th>
				<th scope="col" width="12%">NL</th>
				<th scope="col" width="12%">SL</th>
			</tr>
		</thead>
		<tbody>
		<%-- <s:iterator value="{'Lagna','Moon','Mars','Rahu','Jupiter','Saturn','Mercury','Ketu','Venus','Sun'}" id="planetD"> --%>
		<s:iterator value="{'Lagna','Moon','Mars','Rahu','Jupiter','Saturn','Mercury','Ketu','Venus','Sun','Uranus','Neptune','Pluto'}" id="planetD">
		<s:iterator value="astrobean.planetDetailHashTable" id="planetDetail">
                        <%--<s:if test="#planetDetail.value.planetName!='Lagna'">--%>
                        <s:if test="#planetDetail.value.planetName==#planetD">
			<tr>
				<td><s:property value="#planetDetail.value.planetName" /></td>
				<td><s:property value="#planetDetail.value.RC" /></td>
				<td><s:property value="#planetDetail.value.signName" /></td>
				<td><s:property value="#planetDetail.value.degree" /></td>
				<s:if test='#session.containsKey("20")'>
			      		<td><s:property value="#planetDetail.value.nakshatra" /></td>
					<td><s:property value="#planetDetail.value.padam" /></td>
				</s:if>
				<td><s:property value="#planetDetail.value.RL" /></td>
				<td><s:property value="#planetDetail.value.NL" /></td>
				<td><s:property value="#planetDetail.value.SL" /></td>
			</tr>
                      	</s:if>
			</s:iterator>
		</s:iterator>
		</tbody>
	</table>
	</div>
</div>


<div id="cuspalChart" style="display: none;">

<div class="kundli-chart" id="kundliChartCuspal"  style="display: none;">
	<img class="img-responsive" src="vastuImages/kundli-box.png">
	
	<s:iterator value="cuspKundli" id="cuspK" status="iterStatus">
        	<div class='rs<s:property value="#cuspK.key"/>'>
                	<span id=cus class='in<s:property value="#cuspK.key"/>'><s:property value="#cuspK.value.signNumber"/></span>

                	<strong><s:property value="#cuspK.value.planetName"/></strong>
               		<s:if test='%{#session.isTransitKundli=="1"}'>
				<s:if test='%{#cuspK.value.transitPlanetName!=""}'>
                                	<strong id="trans"> <s:property value="#cuspK.value.transitPlanetName"/><sup>T</sup></strong>
                        	</s:if>
               		</s:if>
        	</div>
	</s:iterator>
	
</div>



<div id="mahaJyotishScriptCuspal"  style="display: none;"> 
	<h4 style="text-align: center;width: 100%">MahaJyotish Script</h4>
	<div class="table-responsive"  id="tableDiv">
	<table id="dataTable" width="100%" border="0" cellspacing="0"
		cellpadding="0" class="ui-table ui-responsive">
		<thead>
			<tr>
				<th scope="col" width="10%">Planet</th>
				<th scope="col" width="10%">Source</th>
				<th scope="col" width="15%">Result(NL)</th>
				<th scope="col" width="10%">Adtnl H</th>
				<th scope="col" width="20%">SL</th>
				<th scope="col" width="20%">NL(SL)</th>
				<th scope="col" width="15%">MDL upto(current)</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="astrobean.natalStrengthChart" id="myAstro"
				status="iterStatus">
				<tr>
					<s:iterator value="myAstro">
						<td><s:property />
						</td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table></div>
</div>
	<div id="rahuKetuCuspal"  style="display: none;">	
	<div class="table-responsive"  id="tableDiv" >
	<table id="dataTable" width="100%" border="0" cellspacing="0"
		cellpadding="0" style="margin-top: 25px;" class="ui-table ui-responsive">
		<thead>
			<tr>
				<th scope="col" colspan="2">Rahu</th>
				<th scope="col" colspan="2">Ketu</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><s:property value="astrobean.rahuSource"/></td>
				<td><table width="100%" height="100%">
				<tr><td>A:<s:property value="astrobean.aspectRahu"/></td></tr>
				<tr><td>C:<s:property value="astrobean.conjucRahu"/></td></tr>
				<tr><td>S:<s:property value="astrobean.signLordRahu"/></td></tr>
				<tr><td>R:<s:property value="astrobean.rashiLordRahu"/></td></tr>
				</table></td>
				<td><s:property value="astrobean.ketuSource"/></td>
				<td><table width="100%" height="100%">
				<tr><td>A:<s:property value="astrobean.aspectKetu"/></td></tr>
				<tr><td>C:<s:property value="astrobean.conjucKetu"/></td></tr>
				<tr><td>S:<s:property value="astrobean.signLordKetu"/></td></tr>
				<tr><td>R:<s:property value="astrobean.rashiLordKetu"/></td></tr>
				</table></td>
			</tr>
		</tbody>
	</table>
	</div>
	</div>
</div>






<div  id="cuspDetailDiv"  style="display: none;">
	<h4 style="text-align: center;width: 100%">Cusp Details</h4>
	<div class="table-responsive" id="tableDiv"  >
	<table id="cuspDetailTabl" width="100%" border="0" cellspacing="0"
		cellpadding="0" class="ui-table ui-responsive">
		<thead>
			<tr>
				<th scope="col" width="12%">House Cusp</th>
				<th scope="col" width="12%">Sign</th>
				<th scope="col" width="12%">Degree</th>
				<s:if test='#session.containsKey("20")'>
					<th scope="col" width="12%">Nakshatra</th>
                                        <th scope="col" width="12%">Padam</th>	
				</s:if>
				<th scope="col" width="12%">RL</th>
				<th scope="col" width="12%">NL</th>
				<th scope="col" width="12%">SL</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="astrobean.houseDetailHashTable" id="cuspDetail"
				status="iterStatus">
				<tr>
					<s:iterator value="cuspDetail">
						<td id="house<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.key"/></td>
						<td id="sign<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.value.signName"/></td>
						<td id="degree<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.value.degree"/></td>
						<s:if test='#session.containsKey("20")'>
					      		<td><s:property  value="#cuspDetail.value.nakshatra"/></td>
							<td><s:property  value="#cuspDetail.value.padam"/></td>
						</s:if>
						<td><s:property  value="#cuspDetail.value.RL"/></td>
						<td><s:property  value="#cuspDetail.value.NL"/></td>
						<td><s:property  value="#cuspDetail.value.SL"/></td>
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>
	</table></div>

	</div> 
<%--</div>--%> 


<!-- Dasha Details -->

<div id="dashaDetail" style="display: none">
	<h4 style="text-align: center; margin-top: 15px; margin-left: 20px;">Vimshottari Dasha Chart</h4>
	<table style="margin-top: 30px;" align="center" id="dashaHeaderTable">
		<tr>
			<td><label id="vimshottariHeader"> Main Dasha</label></td>
		</tr>
		<tr>
			<td width="50%"><label id="plntHeader" style="color: red;"></label></td>
		</tr>
	</table>
	<div class="table-responsive" id="tableDiv">
		<table id="dataTable" class="ui-table ui-responsive" width="100%" border="0" cellspacing="0" border="1">
			<thead>
				<tr>
					<th scope="col" width="25%">Planets</th>
					<th scope="col" width="25%">Start</th>
					<th scope="col" width="25%">End</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="3">
						<table id="vimshottariTable" width="100%" border="0" cellspacing="0" border="0" cellpadding="0">
							<s:iterator value="vimshottari" id="planetDetail" status="stats">
								<tr class='clickable-row' data-href='' id='<s:property value="%{#stats.index}"/>'>
									<td class='td1<s:property value="%{#stats.index}"/>' width="25%"><s:property value="#planetDetail.key" /></td>
									<td class='td2<s:property value="%{#stats.index}"/>' width="25%"><s:property value="#planetDetail.value.startTime" /></td>
									<td class='td3<s:property value="%{#stats.index}"/>' width="25%"><s:property value="#planetDetail.value.endTime" /></td>
								</tr>
							</s:iterator>
						</table> 
						<s:iterator value="antraDashamap" id="antra" status="status">
							<table class="antarTable" id="antar<s:property value="%{#status.index}"/>" width="100%" border="0" cellspacing="0" cellpadding="0" style="display: none;">
								<tbody>
									<s:iterator value="#antra.value" id="dataIterator" status="st">
										<tr id='<s:property value="%{#st.index}"/>' class='clickable-row' data-href=''>
											<s:iterator value="#dataIterator.split('#')" id="innerData" status='sts'>
												<td width="25%" id='atd<s:property value="%{#status.index}"/><s:property value="%{#st.index}"/><s:property value="%{#sts.index}"/>'>
													<s:property />
												</td>
											</s:iterator>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:iterator>
						<table id="vimshottariTableInner" width="100%" border="0" cellspacing="0" border="0" style="display: none;" cellpadding="0">
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<button class="form-control" style="background-color: #FFA500; color: white" id="goBackMain">Go to Main Dasha</button>
	</div>
</div>




<!-- Planet details -->
 
 
 <div id="planetDetail" style="display: none">
	<h4 style="text-align: center;width: 100%;margin-top: 15px" >Planet Details</h4>
	<div class="table-responsive"  id="tableDiv">
	<table id="planetTable" width="100%" border="0" cellspacing="0" border="1"
		cellpadding="0" class="ui-table ui-responsive">
		<thead>
			<tr>
				<th scope="col" width="12%">Planet</th>
				<th scope="col" width="12%">R/C</th>
				<th scope="col" width="12%">Sign</th>
				<th scope="col" width="12%">Degree</th>
				<s:if test='#session.containsKey("20")'>
					<th scope="col" width="12%">Nakshatra</th>
					<th scope="col" width="12%">Padam</th>
				</s:if>
				<th scope="col" width="12%">RL</th>
				<th scope="col" width="12%">NL</th>
				<th scope="col" width="12%">SL</th>
			</tr>
		</thead>
		<tbody>
		
		<s:iterator value="astrobean.planetDetailHashTable" id="planetDetail" status="rowstatus">
                        <s:if test="#planetDetail.value.planetName!='Lagna'">
			<tr>
				<td id="pl<s:property value="#rowstatus.index"/>"><s:property value="#planetDetail.value.planetName" /></td>
				<td><s:property value="#planetDetail.value.RC" /></td>
				<td id="sgn<s:property value="#rowstatus.index"/>"><s:property value="#planetDetail.value.signName" /></td>
				<td id="deg<s:property value="#rowstatus.index"/>"><s:property value="#planetDetail.value.degree" /></td>
				<s:if test='#session.containsKey("20")'>
			     		<td><s:property value="#planetDetail.value.nakshatra" /></td>
					<td><s:property value="#planetDetail.value.padam" /></td>
				</s:if>
				<td><s:property value="#planetDetail.value.RL" /></td>
				<td><s:property value="#planetDetail.value.NL" /></td>
				<td><s:property value="#planetDetail.value.SL" /></td>
			</tr>
                        </s:if>
			</s:iterator>
		</tbody>
	</table></div>

         <h4 style="margin-top: 50px;text-align: center;width: 100%" >Cusp Details</h4>
        <div class="table-responsive" id="tableDiv"  >
        <table id="cuspDetailTabl" width="100%" border="0" cellspacing="0"
                cellpadding="0" class="ui-table ui-responsive">
                <thead>
                        <tr>
                                <th scope="col" width="12%">House Cusp</th>
                                <th scope="col" width="12%">Sign</th>
                                <th scope="col" width="12%">Degree</th>
				<s:if test='#session.containsKey("20")'>
					<th scope="col" width="12%">Nakshatra</th>
                                        <th scope="col" width="12%">Padam</th>
				</s:if>
                                <th scope="col" width="12%">RL</th>
                                <th scope="col" width="12%">NL</th>
                                <th scope="col" width="12%">SL</th>
                        </tr>
                </thead>
                <tbody>
                        <s:iterator value="astrobean.houseDetailHashTable" id="cuspDetail"
                                status="iterStatus">
                                <tr>
                                        <s:iterator value="cuspDetail">
                                                <td id="house<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.key"/></td>
                                                <td id="sign<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.value.signName"/></td>
                                                <td id="degree<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.value.degree"/></td>
						<s:if test='#session.containsKey("20")'>
                                              		<td><s:property  value="#cuspDetail.value.nakshatra"/></td>
                                                	<td><s:property  value="#cuspDetail.value.padam"/></td>
						</s:if>
                                                <td><s:property  value="#cuspDetail.value.RL"/></td>
                                                <td><s:property  value="#cuspDetail.value.NL"/></td>
                                                <td><s:property  value="#cuspDetail.value.SL"/></td>
                                        </s:iterator>
                                </tr>
                        </s:iterator>
                </tbody>
        </table></div>

</div>

<div id="aspectChart" style="display: none">
        <h4 style="text-align: center;width: 100%;margin-top: 50px" >Aspect On House <img src="vastuImages/arrow-down.png"/> </h4>
        <div class="table-responsive"  id="cuspAspectTable">
        </div>

        <h4 style="margin-top: 50px;text-align: center;width: 100%" >Aspects On Planets <img src="vastuImages/arrow-down.png"/></h4>
        <div class="table-responsive" id="cuspPlanetTable"  >
       </div>

</div>

 <div id="transitplanetCuspDetl" style="display: none">
        <h4 style="text-align: center;width: 100%;margin-top: 15px" >Transit Planet Details</h4>
        <div class="table-responsive"  id="tableDiv">
        <table id="transitPlanetTable" width="100%" border="0" cellspacing="0" border="1"
                cellpadding="0" class="ui-table ui-responsive">
                <thead>
                        <tr>
                                <th scope="col" width="12%">Planet</th>
                                <th scope="col" width="12%">R/C</th>
                                <th scope="col" width="12%">Sign</th>
                                <th scope="col" width="12%">Degree</th>
				<s:if test='#session.containsKey("20")'>
                               		<th scope="col" width="12%">Nakshatra</th>
                                	<th scope="col" width="12%">Padam</th>
				</s:if>
                                <th scope="col" width="12%">RL</th>
                                <th scope="col" width="12%">NL</th>
                                <th scope="col" width="12%">SL</th>
                        </tr>
                </thead>
                <tbody>

                <s:iterator value="transitAstroBean.planetDetailHashTable" id="planetDetail" status="rowstatus">
                        <s:if test="#planetDetail.value.planetName!='Lagna'">
                        <tr>
                                <td id="transitPl<s:property value="#rowstatus.index"/>"><s:property value="#planetDetail.value.planetName" /></td>
                                <td><s:property value="#planetDetail.value.RC" /></td>
                                <td id="transitSgn<s:property value="#rowstatus.index"/>"><s:property value="#planetDetail.value.signName" /></td>
                                <td id="transitDeg<s:property value="#rowstatus.index"/>"><s:property value="#planetDetail.value.degree" /></td>
				<s:if test='#session.containsKey("20")'>
                             		<td><s:property value="#planetDetail.value.nakshatra" /></td>
                                	<td><s:property value="#planetDetail.value.padam" /></td>
				</s:if>
                                <td><s:property value="#planetDetail.value.RL" /></td>
                                <td><s:property value="#planetDetail.value.NL" /></td>
                                <td><s:property value="#planetDetail.value.SL" /></td>
                        </tr>
                        </s:if>
                        </s:iterator>
                </tbody>
        </table></div>

<h4 style="margin-top: 50px;text-align: center;width: 100%" >Transit Cusp Details</h4>
        <div class="table-responsive" id="tableDiv"  >
        <table id="transitCuspTable" width="100%" border="0" cellspacing="0"
                cellpadding="0" class="ui-table ui-responsive">
                <thead>
                        <tr>
                                <th scope="col" width="12%">House Cusp</th>
                                <th scope="col" width="12%">Sign</th>
                                <th scope="col" width="12%">Degree</th>
				<s:if test='#session.containsKey("20")'>
					<th scope="col" width="12%">Nakshatra</th>
                                        <th scope="col" width="12%">Padam</th>
				</s:if>
                                <th scope="col" width="12%">RL</th>
                                <th scope="col" width="12%">NL</th>
                                <th scope="col">SL</th>
                        </tr>
                </thead>
                <tbody>
                        <s:iterator value="transitAstroBean.houseDetailHashTable" id="cuspDetail"
                                status="iterStatus">
                                <tr>
                                        <s:iterator value="cuspDetail">
                                                <td id="transitHouse<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.key"/></td>
                                                <td id="transitSign<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.value.signName"/></td>
                                                <td id="transitDegree<s:property value="#iterStatus.index"/>"><s:property  value="#cuspDetail.value.degree"/></td>
						<s:if test='#session.containsKey("20")'>
                                              		<td><s:property  value="#cuspDetail.value.nakshatra"/></td>
                                                	<td><s:property  value="#cuspDetail.value.padam"/></td>
						</s:if>
                                                <td><s:property  value="#cuspDetail.value.RL"/></td>
                                                <td><s:property  value="#cuspDetail.value.NL"/></td>
                                                <td><s:property  value="#cuspDetail.value.SL"/></td>
                                        </s:iterator>
                                </tr>
                        </s:iterator>
                </tbody>
        </table></div>

</div>




<div id="vastuCircle" style="display: none">
 <div class="row">
 <div class="col-sm-2"></div>
        <div class="col-sm-8">
                <div class="col-sm-12" id="innerHeadingDiv" style="margin-top: 0px;">
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
<div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
             <s:if test='%{#session.isTransitKundli=="1"}'>
                <table width="100%"  height="100%">
                <tr>
                <td width="85%" align="right" id="cir">Transit Kundli</td>
                <td width="20%" align="left" style="padding-left: 10px;"><input type="checkbox"  id="circleTransitKundli"></input></td>

                </tr>
                </table>
             </s:if>
        </div>
        <div class="col-sm-2"><a href='getKundliCircle.action' data-ajax="false" onclick="addURL()">Download</a></div>
</div>
<div class="span11" style="overflow: auto">
<%--<div style="display:none;">
<img id="chakra" height="600" width="600" src="vastuImages/kundli_circle.jpg">
<div>--%>
<div class="container">
	<div class="row">
        <div class="col-md-2 col-sm-2"></div>
        <div class="col-md-8 col-sm-2" style="text-align: center;">
        <canvas id="myCanvasList" width="800" height="50" ></canvas>
        </div>
        <div class="col-md-2"></div>
      </div>

      <div class="row">
        <div class="col-md-1 col-sm-1 "></div>
        <div class="col-md-8 col-sm-8" style="text-align: center; margin-top: -30px;">
        <canvas id="myCanvas" width="1000" height="1000" style="background:white;"></canvas>
        </div>
        <div class="col-md-2 col-sm-2"></div>
      </div>
</div>
</div>


 </div>

 </div>


<s:form id="CropImageSend" method="post">
<s:hidden name="imageName" id="imageName" value='%{#session.userName}_post.jpg'></s:hidden>
<%--<s:hidden name="listimageName" id="listimageName" value='list%{#session.userName}_post.jpg'></s:hidden>
<s:hidden name="listImage" id="listImage"></s:hidden>--%>
<s:hidden name="baseImage" id="baseImage"></s:hidden>
<s:hidden name="baseTransitImage" id="baseTransitImage"></s:hidden>
<s:hidden name="kundliCir" id="kundliCir"></s:hidden>
<s:hidden name="isTransitKundli" id="isTransitKundli"></s:hidden>
<s:hidden name="astrobean.astroHouseTable" id="astroHouseTabl"></s:hidden>
<s:hidden name="astrobean.astroPlanetTable" id="astroPlanetTabl"></s:hidden>
								</s:form>
<%-- <div><s:property value="astrobean.astroHouseTable"></s:property></div> --%>