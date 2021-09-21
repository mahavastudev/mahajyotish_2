<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <script
	src="https://maps.googleapis.com/maps/api/js?sensor=false&libraries=places"></script> --%>

<script type="text/javascript">
 var availableTags=[];
 var stateFlag = 0;
 var map = {};

function transitDateAutoFocus(){

        var transitDate =document.getElementById('transitDate').value.length;
        var transitMonth = document.getElementById('transitMonth').value.length;
        var transitYear =  document.getElementById('transitYear').value.length;

        if(transitDate>1){
                document.getElementById('transitMonth').focus();
        }
        if(transitMonth>1){
                document.getElementById('transitYear').focus();
        }
        if(transitYear>3){
                document.getElementById('transitYear').focus();
        }

}

function transitTimeAutoFocus(){

        var transitHour = document.getElementById('transitHour').value.length;
        var transitMinute = document.getElementById('transitMinute').value.length;
        var transitSecond =  document.getElementById('transitSecond').value.length;

        if(transitHour>1){
                document.getElementById('transitMinute').focus();
        }
        if(transitMinute>1){
                document.getElementById('transitSecond').focus();
        }
}

	

function currentDateTime(){
	var dateTime =  new Date();
	var date = dateTime.getDate();
	var month = dateTime.getMonth()+1;
	var year = dateTime.getFullYear();
	var hour = dateTime.getHours();
	var minute = dateTime.getMinutes();
        var second = dateTime.getSeconds();
	document.getElementById('date').value=date;
	document.getElementById('month').value=month;
	document.getElementById('year').value=year;
	document.getElementById('hour').value=hour;
	document.getElementById('minute').value=minute;
	document.getElementById('second').value=second;
}

$(document).ready(function(){
   	var hasState= $("#state").val();
        if(hasState==-1){

        }

	$("#state").val('07');
        $("#country").val('IN');
      	var cityName = document.getElementById('tags').value;

      $("#name").addClass("form-control");
      $("#tags").addClass("form-control");
      $("#date").addClass("form-control");
      $("#month").addClass("form-control");
      $("#year").addClass("form-control");
      $("#hour").addClass("form-control");
      $("#minute").addClass("form-control");
      $("#second").addClass("form-control");
      $("#country").addClass("form-control");
      $("#state").addClass("form-control");
      $("#stateCity").addClass("form-control");
      $("#remarks").addClass("form-control");
      $("#date").attr("placeholder", "dd");
      $("#month").attr("placeholder", "mm");
      $("#year").attr("placeholder", "yyyy");
      $("#hour").attr("placeholder", "HH");
      $("#minute").attr("placeholder", "mm");
      $("#second").attr("placeholder", "ss");
      $("#tags").attr("autocomplete", "off");

      $( "#country" ).change(function(){   
         
    	  var val = this.value;
          document.getElementById('tags').value="";
    	  var url = "?countryCode="+val; 
	 	  url = "<%=response.encodeURL("countryByState")%>"+url;
 		  $.ajax({	
 			  type: "POST",
 			  url: url,
 			  success: function(result){
                          if(result.trim() == "NA"){
                            stateFlag =1;
                            $("#countryState").html("");
                            $("#stateLable").html("");
                          }else{
                            stateFlag =0;
                            $("#countryState").html(result);
                            $("#stateLable").html("State :");

                          }
 
 			  }
 			});  	  	  
      });

     	$( "#state" ).change(function(){  
          	document.getElementById('tags').value=""; 
    	});


	$( "#stateCity" ).change(function(){
        	document.getElementById('newLat').value="";
          	document.getElementById('newLong').value="";
          	document.getElementById('newCityCode').value="";
          	var val = this.value;
          	var latLongvalue=map[val.toLowerCase()];
          	var latlong=latLongvalue.split(",");
          	document.getElementById('newLat').value=latlong[1];
          	document.getElementById('newLong').value=latlong[0];
          	document.getElementById('newCityCode').value=latlong[2];
     	});

      
	$("#tags").keyup(function(){
	
		var cityCode=document.getElementById('cityCode').value;
		if(cityCode!=""){
			document.getElementById('cityCode').value="";
		}
         	var cityName = document.getElementById('tags').value;
		if(cityName==""){
			availableTags=[];
		}
         	var flagVal = true;
         	if((cityName.length==3) || (cityName.length>2 && availableTags.length==0)){
             		showCityName(cityName.substring(0,3),flagVal);
         	}
		if(cityName.length==6) {
			showCityName(cityName.substring(0,6),flagVal);
		}
     	});
    

	$("#date,#month,#year,#hour,#minute,#second").keypress(function (e)  {
        	if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                	return false;
           	}
       	});

	$("#reset").click(function() {
        	$("#kundliTransit").prop('checked', false);
            	$("#transDate").hide();
             	$("#transTime").hide();
      	});
		$('.dob').keyup(function (){
			var id=this.id;
			if(this.id=='date'){
				var date =document.getElementById('date').value.length;
				if(date>1){
					document.getElementById('month').focus();
				}
			}
			if(this.id=='year'){
                                var year =document.getElementById('year').value.length;
				if(year>3){
                			document.getElementById('year').focus();
        			}
			}

			if(id=='month'){
				var month=document.getElementById("month").value.length;
				if(month>1)
					document.getElementById('year').focus();
			}	
		});

		$('.tob').keyup(function (){


                        var id=this.id;
                        if(id=='hour'){
                                var hour =document.getElementById('hour').value.length;
                                if(hour>1){
                                        document.getElementById('minute').focus();
                                }
                        }
                        if(id=='minute'){
                                var minute =document.getElementById('minute').value.length;
                                if(minute>1){
                                        document.getElementById('second').focus();
                                }
                        }

                });

});

function validate()
{
 	var name =document.getElementById('name').value;
        name = name.trim();
        var alpha = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
 	var countryVal = $("#country").val();
   	var stateVal = $("#state").val();
        var cityName = $("#tags").val();	
	var cityCode=document.getElementById('cityCode').value;
        var D =document.getElementById('date').value;
        var M =document.getElementById('month').value;
        var Y = document.getElementById('year').value;
        var date =D.length;
        var month = M.length;
        var year =  Y.length;
	var hour =document.getElementById('hour').value.length;
	var minute = document.getElementById('minute').value.length;
	var second =  document.getElementById('second').value.length; 
        var ll=map[cityName.toLowerCase()];

	if(ll!=undefined)
	{
	var latlong=ll.split(",");
	$("#lat").val(latlong[1]);
	$("#long").val(latlong[0]);
        $("#cityCode").val(latlong[2]);
	}
        var lat = $("#lat").val();
        var longitude = $("#long").val();
        var cityCode = $("#cityCode").val();

 	
    if(name=="" || name==null)
    {
        $("#alertMsg").html("<h5>Please enter name</h5>");
        $("#openAlert").click();
    	return false;
    }   
    if(!name.match(alpha)) {
        $("#alertMsg").html("<h5>Please enter valid name</h5>");
        $("#openAlert").click();
        return false;
    }

    if(countryVal==-1){
        $("#alertMsg").html("<h5>Please select country</h5>");
        $("#openAlert").click();
	return false;
    }  

   if(stateFlag == 0){
         if(stateVal==-1){
             $("#alertMsg").html("<h5>Please select state</h5>");
             $("#openAlert").click();
             return false;
          }
     }

    if(cityName=="" || cityName==null){
        $("#alertMsg").html("<h5>Please enter city</h5>");
        $("#openAlert").click();    
	return false;
    }
 	 
   if(date=="" || date==null)
    {
        $("#alertMsg").html("<h5>Please enter date</h5>");
        $("#openAlert").click();
    	return false;
    }

    if(D > 31)
    {
        $("#alertMsg").html("<h5>Please enter valid date</h5>");
        $("#openAlert").click();
        return false;
    }

 
	
    if(month=="" || month==null)
    {
        $("#alertMsg").html("<h5>Please enter month</h5>");
        $("#openAlert").click();
    	return false;
    }

    if(M > 12)
    {
        $("#alertMsg").html("<h5>Please enter valid month</h5>");
        $("#openAlert").click();
        return false;
    }

 	
    if(year=="" || year==null)
    {
        $("#alertMsg").html("<h5>Please enter year</h5>");
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
 
 	
    if(hour=="" || hour==null)
    {
        $("#alertMsg").html("<h5>Please enter hour</h5>");
        $("#openAlert").click();
    	return false;
    }
 	
    if(minute=="" || minute==null)
    {
        $("#alertMsg").html("<h5>Please enter minute</h5>");
        $("#openAlert").click();
    	return false;
    }
 	
    if(second=="" || second==null)
    {
        $("#alertMsg").html("<h5>Please enter second</h5>");
        $("#openAlert").click();
    	return false;
    } 
    

    if(availableTags.length >0)
    {
    	var matchCityHtml='<select><option value=-1>Select</option>';
      	var cityHtml='<select><option value=-1>Select</option>';
      	var flg=0,matchFlag = 0;
	for(var i=0; i<availableTags.length; i++){
            if ((availableTags[i].toLowerCase()).indexOf(cityName.toLowerCase()) == 0) {
                matchCityHtml=matchCityHtml+"<option>"+availableTags[i]+"</option>";
                matchFlag=1;
              }
              if(availableTags[i].toLowerCase() == cityName.toLowerCase()){
              flg=1;
              }
             cityHtml=cityHtml+"<option>"+availableTags[i]+"</option>";
	}
	if(flg==0)
	{   
            if(matchFlag == 1){
                $("#stateCity").html(matchCityHtml);
            }
	    else{
               $("#stateCity").html(cityHtml);
            }
            document.getElementById('newLat').value="";	
            document.getElementById('newLong').value="";
            document.getElementById('newCityCode').value="";
	        $("#openModel").click();
	    return false;
	}
    }
    else{
        var flag = 0;
        var flagVal = true;
        if(availableTags.length<=0)
        {   
            showCityName(cityName,flagVal);
            if(availableTags.length>0){
               flag=1;
            }
        }
        if(flag == 0 && cityCode== ""){
	   $("#alertMsg").html("<h5>Please enter correct city</h5>");
           $("#openAlert").click();		
           return false;
        }

    }
$("#showLoader").click();
   return true;
 	
}

function validateNewLatLong(){
  var cityVal = $("#stateCity").val();
  var newLatitude = $("#newLat").val();
  var newLongitude = $("#newLong").val();
  var newCityCode = $("#newCityCode").val();
  if(cityVal == -1){
     $("#alertMsg").html("<h5>Please select city</h5>");
     $("#openAlert").click();
  }
  else if(newLatitude =="" || newLatitude == null){
     $("#alertMsg").html("<h5>Please enter latitude value</h5>");
     $("#openAlert").click();
  }
  else if(newLongitude =="" || newLongitude == null){
     $("#alertMsg").html("<h5>Please enter longitude</h5>");
     $("#openAlert").click();
  }
  else{
     document.getElementById('tags').value=cityVal;
     $("#lat").val(newLatitude);
     $("#long").val(newLongitude);
     $("#newCityCode").val(newCityCode);
     $(".close").click();
  }
}



function showCityName(cityName,flagVal){
          var countryCode = $("#country").val();
          var stateCode = $("#state").val();
          if((cityName.length==3) || (cityName.length>2 && availableTags.length==0)){
                 availableTags=[];
                     map={};
                  var url ="";
                  if(stateFlag ==0){
                    url = "?countryCode="+countryCode+"&stateCode="+stateCode+"&cityCode="+cityName;
                  }else{
                    url = "?countryCode="+countryCode+"&cityCode="+cityName;
                  }

                  url = "<%=response.encodeURL("cityLatLong")%>"+url;
                     $.ajax({
                          type: "POST",
                          url: url,
			  async: flagVal,
                          success: function(result){
                                        var response = result.trim();
                                        console.log("response from server ["+response+"]");
                                var res = response.split(";");
                                count=0;
                                for(var i=0; i<res.length-1; i++){
                                 var cityData= res[i].split(",");
                                 console.log("["+cityData[0]+"]");
                                 console.log("["+cityData[1]+"]");
                                    if(map[cityData[1].toLowerCase()] == null){
                                           availableTags[count]=cityData[1];
                                           count=count+1;
                                           map[cityData[1].toLowerCase()]= cityData[3]+","+cityData[4]+","+cityData[0];
                                  }
                              }
                                console.log("available Tags : "+availableTags);

                          }

                        });

          }
                 $("#tags").autocomplete({
                                source: availableTags,
                                minLength: 2
                                });
        }

</script>


<div class="row">
	<div class="col-sm-2 hidden-xs"></div>
	<div class="col-sm-8  col-xs-12">
		<div class="col-sm-12" id="innerHeadingDiv">
			<div class="container-fluid"
				style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
				<div class="row-fluid">
					<div class="span8">
						<p class="header" align="center">Add Your Daily Event</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>

<div class="row col-sm-12">
	<div class="col-sm-2"></div>
	<div class="col-sm-8" id="formDiv">
		<form class="form-horizontal" id="frm_demo" name="frm_demo"
			data-ajax="false" method="post" onsubmit="return validate()"
			style="margin: 15px">
			<s:hidden id="lat" name="generateKundli.latitude"></s:hidden>
			<s:hidden id="long" name="generateKundli.longitude"></s:hidden>
                        <s:hidden id="cityCode" name="generateKundli.cityCode"></s:hidden>
                        <s:hidden id="userId" value="%{#session.userName}"></s:hidden>
			<s:hidden name="genType" value="EV"></s:hidden>
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">Event Name :</label>
				<div class="col-sm-8">
					<table width="100%" height="100%">
						<tr>
							<td width="90%"><s:textfield id="name"
									name="generateKundli.name" maxlength="100"></s:textfield></td>
							<td width="10%" align="right" style="padding-left: 10px;">
							</td>
						</tr>
					</table>
				</div>
			</div>						
			<div class="form-group">
                                <label for="remarks" class="col-sm-3 control-label">Event Description :</label>
                                <div class="col-sm-8" id="lookup">
                                        <table width="100%" height="100%">
                                                <tr>
                                                        <td Style="width:90%;"><s:textarea id="remarks"
                                                                        name="generateKundli.remarks" cssStyle ="height:80px;"></s:textarea>
                                                                        </td>
                                                        <td width="10%" align="right" style="padding-left: 10px;"></td>
                                                </tr>
                                        </table>
                                </div>
                        </div>
	

			<div class="form-group">
                                <label for="date" class="col-sm-3 control-label">Event Date :</label>
                                <div class="col-sm-8">
                                        <table width="100%" height="100%">
                                                <tr>
                                                        <td width="28%" align="left">
								<s:textfield id="date" name="generateKundli.date" maxlength="2" cssClass="dob" ></s:textfield>
							</td>
							<td width="3%" align="left"></td>
                                                        <td width="28%" align="left">
                                                                <s:textfield id="month" name="generateKundli.month" maxlength="2" cssClass="dob" ></s:textfield>
                                                        </td>
                                                        <td width="3%" align="left"></td>
                                                        <td width="28%" align="left">
                                                                <s:textfield id="year" name="generateKundli.year" maxlength="4" cssClass="dob" ></s:textfield>
                                                        </td>
                                                        <td width="10%" align="right" style="padding-left: 10px;">
								<img style="width: 30px; height: 30px" id="currentTime" src="vastuImages/watch-icon.png" onclick="currentDateTime()" />
							</td>

                                                </tr>
                                        </table>
                                </div>
                                <div class="col-sm-4"></div>
                        </div>
			
			<div class="form-group">
                                <label for="time" class="col-sm-3 control-label">Event Time :</label>
                                <div class="col-sm-8">
                                        <table width="100%" height="100%">
                                                <tr>
                                                        <td width="28%" align="left">
                                                                <s:textfield id="hour" name="generateKundli.hour" maxlength="2" cssClass="tob"></s:textfield>
                                                        </td>
                                                        <td width="3%" align="left"></td>
                                                        <td width="28%" align="left">
                                                                <s:textfield id="minute" name="generateKundli.minute" maxlength="2" cssClass="tob"></s:textfield>
                                                        </td>
                                                        <td width="3%" align="left"></td>
                                                        <td width="28%" align="left">
                                                                <s:textfield id="second" name="generateKundli.second" maxlength="2" cssClass="tob"></s:textfield>
                                                        </td>
                                                        <td width="10%"></td>
                                                </tr>
                                        </table>
                                </div>
                                <div class="col-sm-4"></div>
                        </div>
			
			<div class="form-group">
				<label for="country" class="col-sm-3 control-label">Country
					:</label>
				<div class="col-sm-8">
					<table width="100%" height="100%">
						<tr>
							<td width="90"><s:select id="country" headerKey='-1'
									headerValue='select' list="countryLst" listKey="code"
									listValue="name" name="generateKundli.country"/>
				
							</td>
							<td width="10%" align="right" style="padding-left: 10px;"></td>
							
						</tr>
					</table>
				</div>
			</div>
			 <div class="form-group">
					<label for="State" id="stateLable" class="col-sm-3 control-label">State :</label>
				<div class="col-sm-8">
					<table width="100%" height="100%">
						<tr>
							<td width="90" id="countryState">
                                                        <s:select id="state" headerKey='-1'
                                                                        headerValue='select' list="stateLst" listKey="scode"
                                                                        listValue="sname" name="generateKundli.state"/>
							</td>
							<td width="10%" align="right" style="padding-left: 10px;"></td>
						</tr>
					</table>
				</div>
			</div>  
			<div class="form-group">
				<label for="city" class="col-sm-3 control-label">City :</label>
				<div class="col-sm-8" id="lookup">
					<table width="100%" height="100%">
						<tr>
							<td width="90%"><s:textfield id="tags"
									name="generateKundli.city"></s:textfield>
									</td> 
							<td width="10%" align="right" style="padding-left: 10px;"></td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-3"></div>
				<div class="col-sm-4 ui-content">
					<button type="submit" id="place" class="form-control"
						style="background-color: #FFA500; color: white">Add Event</button>
				</div>
				<div class="col-sm-4 ui-content">
					<button type="reset" value="Reset" class="form-control" id="reset"
						style="background-color: #FFA500; color: white">Reset</button>
				</div>
			</div>
		</form>
	</div>
	<div class="col-sm-2"></div>
</div>

<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
		data-target="#myModal" style="display: none;" id="openModel" data-backdrop="static" data-keyboard="false"></button>
	<div class="modal fade" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="padding: 10px 25px;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>
						<span class="glyphicon glyphicon-map-marker"></span> Select City
					</h4>
				</div>
				<div class="modal-body" style="padding: 40px 50px;">
				<div class="form-group">
					<label for="City"><span class="glyphicon glyphicon-map-marker"></span>
						City</label>
					<select id="stateCity"></select>

				</div>
                                <div class="form-group">
				    <label for="newLat"><span
					    class="glyphicon glyphicon-map-marker"></span> Latitude</label> <input
					    type="text" class="form-control" id="newLat">
				</div>
                                <div class="form-group">
				    <label for="newLong"><span
					    class="glyphicon glyphicon-map-marker"></span> Longitude</label> <input
					    type="text" class="form-control" id="newLong">
				</div>
                                <s:hidden id="newCityCode"></s:hidden>
				<button type="submit" class="btn btn-success btn-block"
					style="background-color: #FFA500; color: white;" onclick="return validateNewLatLong()">
					<span class="glyphicon glyphicon-off"></span> Submit
				</button>
			       </div>
			</div>
		</div>
	</div>


