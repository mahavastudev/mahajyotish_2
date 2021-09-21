<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <script
	src="https://maps.googleapis.com/maps/api/js?sensor=false&libraries=places"></script> --%>

<script type="text/javascript">
 var availableTags=[];
 var stateFlag = 0;
 var map = {};
function dobAutoFocus(){
	var date =document.getElementById('date').value.length;
	var month = document.getElementById('month').value.length;
	var year =  document.getElementById('year').value.length;
	if(Number(date)>1){	
		document.getElementById('month').focus();
	}
	if(month>1){
		document.getElementById('year').focus();
	}
	if(year>3){
		document.getElementById('year').focus();
	}
}


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


function timeAutoFocus(){
	var hour =document.getElementById('hour').value.length;
	var minute = document.getElementById('minute').value.length;
	var second =  document.getElementById('second').value.length;
	if(hour>1){
		document.getElementById('minute').focus();
	} 
	if(minute>1){
		document.getElementById('second').focus();
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

function transitCurrentDateTime(){
        var dateTime =  new Date();
        var date = dateTime.getDate();
        var month = dateTime.getMonth()+1;
        var year = dateTime.getFullYear();
        var hour = dateTime.getHours();
        var minute = dateTime.getMinutes();
        var second = dateTime.getSeconds();
        if ($('#kundliTransit').is(":checked")){
                document.getElementById('transitDate').value=date;
                document.getElementById('transitMonth').value=month;
                document.getElementById('transitYear').value=year;
                document.getElementById('transitHour').value=hour;
                document.getElementById('transitMinute').value=minute;
                document.getElementById('transitSecond').value=second;
        }
}

$(document).ready(function(){
  
   var hasState= $("#state").val();
        if(hasState==-1){
                $("#countryState").html("");
                $("#stateLable").html("");

        }

   var cityName = document.getElementById('tags').value;

   var isTransitKundli = '<s:property value="generateKundli.isTransitKundli"/>';
   if (isTransitKundli==1){
        $("#kundliTransit").prop('checked', true);
        $("#transDate").show();
        $("#transTime").show();
        isTransitKundli=0;
        document.getElementById('transKundli').value=isTransitKundli;

   }

    
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
      $("#transitDate").addClass("form-control");
      $("#transitMonth").addClass("form-control");
      $("#transitYear").addClass("form-control");
      $("#transitHour").addClass("form-control");
      $("#transitMinute").addClass("form-control");
      $("#transitSecond").addClass("form-control");
      $("#date").attr("placeholder", "dd");
      $("#month").attr("placeholder", "mm");
      $("#year").attr("placeholder", "yyyy");
      $("#hour").attr("placeholder", "HH");
      $("#minute").attr("placeholder", "mm");
      $("#second").attr("placeholder", "ss");
      $("#tags").attr("autocomplete", "off");
      $("#transitDate").attr("placeholder", "dd");
      $("#transitMonth").attr("placeholder", "mm");
      $("#transitYear").attr("placeholder", "yyyy");
      $("#transitHour").attr("placeholder", "HH");
      $("#transitMinute").attr("placeholder", "mm");
      $("#transitSecond").attr("placeholder", "ss");

      $("#remarks").addClass("form-control");
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
	if(cityName.length==6) 
	{
		showCityName(cityName.substring(0,6),flagVal);
	}
     });
    
		$("#saveKundli").click(function(){
	 		var name = $("#name").val();
                        name = name.trim();
                        var alpha = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
			var cityName = $("#tags").val();
			var dob = $("#year").val()+"-"+ $("#month").val()+ "-"+$("#date").val();
	 		var tob = $("#hour").val()+":"+ $("#minute").val()+ ":"+$("#second").val();

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
                        var D = document.getElementById('date').value;
                        var M = document.getElementById('month').value;
                        var Y = document.getElementById('year').value;
                        var date = D.length;
                        var month = M.length;
                        var year = Y.length;
	 		var hour =document.getElementById('hour').value.length;
	 		var minute = document.getElementById('minute').value.length;
	 		var second =  document.getElementById('second').value.length; 
                        var countryVal = $("#country").val();
   	                var stateVal = $("#state").val();
                        var isSaveKundli = "Y";
                        var transitDob = "";
                        var transitTob = "";
                        var transitDate="";
                        var transitMonth="";
                        var transitYear="";
                        var transitHour="";
                        var transitMinute="";
                        var transitSecond="";
                         var isTransitKundli=0;

	 		
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

                    if(year<4){
                        $("#alertMsg").html("<h5>Please insert date in yyyy format</h5>");
                        $("#openAlert").click();
                        return false;
                    }
                    
                    if(D=="00"){
                        $("#alertMsg").html("<h5>Date can't be Zero</h5>");
                        $("#openAlert").click();
                        return false;

                    }               
                        
                    if(M=="00"){
                        $("#alertMsg").html("<h5>Month can't be Zero</h5>");
                        $("#openAlert").click();
                        return false;

                    }
                        
                    if(Y=="0000"){
                        $("#alertMsg").html("<h5>Year can't be Zero</h5>");
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
                    
                     if ($('#kundliTransit').is(":checked"))
                        {
                                
                                isTransitKundli=1;
                                var TD = document.getElementById('transitDate').value;
                                var TM = document.getElementById('transitMonth').value;
                                var TY = document.getElementById('transitYear').value;

                                transitDate = TD.length;
                                transitMonth = TM.length;
                                transitYear = TY.length;

                                transitHour = document.getElementById('transitHour').value.length;
                                transitMinute = document.getElementById('transitMinute').value.length;
                                transitSecond = document.getElementById('transitSecond').value.length;
                                
                                transitDob = $("#transitYear").val()+"-"+ $("#transitMonth").val()+ "-"+$("#transitDate").val();
                                transitTob = $("#transitHour").val()+":"+ $("#transitMinute").val()+ ":"+$("#transitSecond").val();

                                
                                $("#transKundli").val(isTransitKundli);
                                if(transitDate=="" || transitDate==null)
                                {
                                        $("#alertMsg").html("<h5>Please enter date</h5>");
                                        $("#openAlert").click();
                                        return false;
                                }
                                
                                 if(TD > 31)
                                 {
                                     $("#alertMsg").html("<h5>Please enter valid date</h5>");
                                     $("#openAlert").click();
                                     return false;
                                 }


                                if(transitMonth=="" || transitMonth==null)
                                {
                                        $("#alertMsg").html("<h5>Please enter month</h5>");
                                        $("#openAlert").click();
                                        return false;
                                }
                             
                                if(TM > 12)
                                {
                                    $("#alertMsg").html("<h5>Please enter valid month</h5>");
                                    $("#openAlert").click();
                                    return false;
                                 }

        
                                if(transitYear=="" || transitYear==null)
                                {
                                        $("#alertMsg").html("<h5>Please enter year</h5>");
                                        $("#openAlert").click();
                                        return false;
                                } 
    
                                if(transitYear< 4)
                                {
                                        $("#alertMsg").html("<h5>Please insert date in yyyy format</h5>");
                                        $("#openAlert").click();
                                        return false;    
                                } 

                                if(TD=="00"){
                                        $("#alertMsg").html("<h5>Date can't be zero</h5>");
                                        $("#openAlert").click();
                                        return false;
                                }

                                if(TM=="00"){
                                        $("#alertMsg").html("<h5>Month can't be zero</h5>");
                                        $("#openAlert").click();
                                        return false;
                                }

                                if(TY=="0000"){
                                        $("#alertMsg").html("<h5>Year can't be zero</h5>");
                                        $("#openAlert").click();
                                        return false;
                                }

                                
                                 if(transitHour=="" || transitHour==null)
                                {
                                        $("#alertMsg").html("<h5>Please enter hour</h5>");
                                        $("#openAlert").click();
                                        return false;
                                }
        
                                if(transitMinute=="" || transitMinute==null)
                                {
                                        $("#alertMsg").html("<h5>Please enter minute</h5>");
                                        $("#openAlert").click();
                                        return false;
                                }
        
                                if(transitSecond=="" || transitSecond==null)
                                {
                                        $("#alertMsg").html("<h5>Please enter second</h5>");
                                        $("#openAlert").click();
                                        return false;
                                } 
                        }
  
 
		    if(availableTags.length >0)
		    { 
		      var matchCityHtml='<select><option value=-1>Select</option>';
		      var cityHtml='<select><option value=-1>Select</option>';
		      var flg=0,matchFlag =0;
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
                        var flagVal =true;
                        if(availableTags.length<=0)
                        {
                           showCityName(cityName,flagVal);
                           if(availableTags.length>0){
                               flag=1;
                           }
                        }
                        if(flag == 0){
                        $("#alertMsg").html("<h5>Please enter correct city</h5>");
                        $("#openAlert").click();
                        return false;
                        }                 
                    }
                        var uId = $("#userId").val();

                        var url = "?name="+name+"&city="+cityName+"&cityCode="+cityCode+"&dob="+dob+"&tob="+tob+"&transitDob="+transitDob+"&transitTob="+transitTob+"&isTransitKundli="+isTransitKundli+"&latitude="+lat+"&longitude="+longitude+"&country="+countryVal+"&state="+stateVal+"&userId="+uId;

	 		/*var url = "?name="+name+"&city="+cityName+"&cityCode="+cityCode+"&dob="+dob+"&tob="+tob+"&latitude="+lat+"&longitude="+longitude+"&country="+countryVal+"&state="+stateVal+"&userId="+uId;*/ 
	 		url = "<%=response.encodeURL("saveKundli")%>"+url;
	 		$.ajax({
 	 			type: "POST",
 	 			url: url,
 	 			success: function(result){
                                $("#isSave").val(isSaveKundli);
                                $("#alertMsg").html("<h5>"+result+"<h5>");
                                $("#openAlert").click();
 	 			}
 	 			});

		    });
	
	         $("#date,#month,#year,#hour,#minute,#second,#transitDate,#transitMonth,#transitYear,#transitHour,#transitMinute,#transitSecond").keypress(function (e)  {
                        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                        /*  $("#errmsg").html("Digits Only").show().fadeOut("slow"); */
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

$(function() {
$("#kundliTransit").click(function () {
       if ($(this).is(":checked")) {
                $("#transDate").show();
                $("#transTime").show();
            } else {
                $("#transDate").hide();
                $("#transTime").hide();
            }
        });
});



function validate()
{
        var isTransitKundli=0;
	var max = 32;
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
        var transitDate = "";
        var transitMonth = "";
        var transitYear = "";
        var transitHour = "";
        var transitMinute = "";
        var transitSecond = "";

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
    
    if ($('#kundliTransit').is(":checked"))
    {

        isTransitKundli=1;
        $("#transKundli").val(isTransitKundli);
        var TD =  document.getElementById('transitDate').value;
        var TM =  document.getElementById('transitMonth').value;
        var TY =  document.getElementById('transitYear').value;
        transitDate = TD.length;
        transitMonth = TM.length;
        transitYear =  TY.length;

        transitHour =document.getElementById('transitHour').value.length;
        transitMinute = document.getElementById('transitMinute').value.length;
        transitSecond =  document.getElementById('transitSecond').value.length;

        if(transitDate=="" || transitDate==null)
        {
                $("#alertMsg").html("<h5>Please enter date</h5>");
                $("#openAlert").click();
                return false;
        }
        
         if(TD > 31)
        {
                $("#alertMsg").html("<h5>Please enter valid date</h5>");
                $("#openAlert").click();
                return false;
        }


        if(transitMonth=="" || transitMonth==null)
        {
                $("#alertMsg").html("<h5>Please enter month</h5>");
                $("#openAlert").click();
                return false;
        }
        
         if(TM > 12)
        {
                $("#alertMsg").html("<h5>Please enter valid month</h5>");
                $("#openAlert").click();
                return false;
        }

        
        if(transitYear=="" || transitYear==null)
        {
                $("#alertMsg").html("<h5>Please enter year</h5>");
                $("#openAlert").click();
                return false;
        } 
    
        if(transitYear< 4)
        {
                $("#alertMsg").html("<h5>Please insert date in yyyy format</h5>");
                $("#openAlert").click();
                return false;    
        }  
       
         if(TD=="00"){
                $("#alertMsg").html("<h5>Date cannot be zero</h5>");
                $("#openAlert").click();
                return false;
        } 
        if(TM=="00"){
                $("#alertMsg").html("<h5>Month cannot be zero</h5>");
                $("#openAlert").click();
                return false;
        }
        if(TY=="0000"){
                $("#alertMsg").html("<h5>Year cannot be zero</h5>");
                $("#openAlert").click();
                return false;
        }
 
        
        if(transitHour=="" || transitHour==null)
        {
                $("#alertMsg").html("<h5>Please enter hour</h5>");
                $("#openAlert").click();
                return false;
        }
         
        if(transitMinute=="" || transitMinute==null)
        {
                $("#alertMsg").html("<h5>Please enter minute</h5>");
                $("#openAlert").click();
                return false;
        }
        
        if(transitSecond=="" || transitSecond==null)
        {
                $("#alertMsg").html("<h5>Please enter second</h5>");
                $("#openAlert").click();
                return false;
        } 

    }

     

    if(availableTags.length >0)
    {
      var matchCityHtml='<select><option value=-1>Select</option>';
      var cityHtml='<select><option value=-1>Select</option>';
      var flg=0,matchFlag =0;
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
						<p class="header" align="center">Manage Log-Book Profile</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>

<div class="row col-sm-12" align="center">
	<div class="col-sm-2"></div>
	<div class="col-sm-8" id="formDiv">
		<form class="form-horizontal" id="frm_demo" name="frm_demo" background="white"
			  action='<c:url value="editDetails.action" />'
			data-ajax="false" method="post" onsubmit="return validate()"
			style="margin: 15px">
			
			<s:hidden id="generateType" name="genType" value="P"></s:hidden>
			<s:hidden id="lat" name="generateKundli.latitude"></s:hidden>
			<s:hidden id="long" name="generateKundli.longitude"></s:hidden>
                        <s:hidden id="cityCode" name="generateKundli.cityCode"></s:hidden>
                       <%-- <s:hidden id="isSave" name="generateKundli.isSave"></s:hidden>--%>
			<s:hidden id="userId" name="userId" value="%{#session.userName}"></s:hidden>
<%--			<s:hidden id="transKundli" name="generateKundli.isTransitKundli"></s:hidden>--%>
			
			<div class="form-group" style="text-align:center;">

				<table align="center">
				<tr>
				<td>
                               <label for="name" class="col-sm-12 control-label">Name </td><td> <label for="name" class="col-sm-12 control-label">:</td><td> <label for="name" class="col-sm-12 control-label"><s:property value="%{#session.kundliName}"/></label></td>
				</tr>
	
				<tr>
				<td>
				<label for="name" class="col-sm-12 control-label">Country</td><td> <label for="name" class="col-sm-12 control-label">:</td><td> <label for="name" class="col-sm-12 control-label"><s:property value="%{#session.country}"/></label></td></tr>

                                <tr>
                                <td>
                                <label for="name" class="col-sm-12 control-label">State</td><td> <label for="name" class="col-sm-12 control-label">:</td> <td>  <label for="name" class="col-sm-12 control-label"><s:property value="%{#session.state}"/></label></td>
				   </tr>

                                <tr>
                                <td>
				
                                <label for="name" class="col-sm-12 control-label">City</td><td> <label for="name" class="col-sm-12 control-label">:</td>  <td>  <label for="name" class="col-sm-12 control-label">  <s:property value="%{#session.pob}"/></label></td>
				   </tr>
                                <tr>
                                <td>

                          <label for="name" class="col-sm-12 control-label">Date Of Birth  </td> <td> <label for="name" class="col-sm-12 control-label">:</td><td><label for="name" class="col-sm-12 control-label"><s:property value="%{#session.dob}"/></label></td>
				      </tr>

                           <tr>
                            <td>

                                <label for="name" class="col-sm-12 control-label">Time</td> <td> <label for="name" class="col-sm-12 control-label">:</td> <td> <label for="name" class="col-sm-12 control-label"><s:property value="%{#session.tob}"/></label></td>
			   </tr>
                                </table>

                        </div>


		<%--	<div class="form-group">
				<label for="country" class="col-sm-3 control-label">Country
					:</label>
				<div class="col-sm-8">
					<table width="100%" height="100%">
						<tr>
							<td width="90%"><s:select id="country" headerKey='-1'
									headerValue='select' list="countryLst" listKey="code"
									listValue="name" name="generateKundli.country"/>
				
							</td>
							<%--<td width="10%" align="right" style="padding-left: 10px;"><a
								href="##" id="saveKundli" data-ajax="false"><img
									style="width: 30px; height: 30px" src="vastuImages/save.png">
							</a>></td>							                                                        <td width="10%" align="right" style="padding-left: 10px;"></td>

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
						<%--	<td width="10%" align="right" style="padding-left: 10px;"><a
								href="##"><img style="width: 30px; height: 30px"
									id="currentTime" src="vastuImages/watch-icon.png"
									onclick="currentDateTime()" /> </a></td>
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
						    <!-- <td width="100%"><input id="tags" name="generateKundli.city"></td> -->
							<td width="90%"><s:textfield id="tags"
									name="generateKundli.city"></s:textfield>
									</td> 
							<td width="10%" align="right" style="padding-left: 10px;"></td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="form-group">
				<label for="date" class="col-sm-3 control-label">Date Of
					Birth :</label>
				<div class="col-sm-8">
					<table width="100%" height="100%">
						<tr>
							<td width="27%" align="left"><s:textfield id="date"
									name="generateKundli.date" maxlength="2" cssClass="dob"
									></s:textfield></td>
							<td width="4%" align="left"></td>
							<td width="27%" align="left"><s:textfield id="month"
									name="generateKundli.month" maxlength="2" cssClass="dob"
									></s:textfield></td>
							<td width="4%" align="left"></td>
							<td width="27%" align="left"><s:textfield id="year"
									name="generateKundli.year" maxlength="4" cssClass="dob"
								></s:textfield></td>
						
							<td width="10%" align="right" style="padding-left: 10px;"><a
								href="##" id="saveKundli" data-ajax="false"><img
									style="width: 30px; height: 30px" src="vastuImages/save.png">
							</a></td>
						</tr>
					</table>
				</div>
				<div class="col-sm-4"></div>
			</div>
			<div class="form-group">
				<label for="time" class="col-sm-3 control-label">Time :</label>
				<div class="col-sm-8">
					<table width="100%" height="100%">
						<tr>
							<td width="90" align="left"><s:textfield id="hour"
									name="generateKundli.hour" maxlength="2" cssClass="tob"
									></s:textfield></td>
							<td width="4%" align="left"></td>
							<td width="27%" align="left"><s:textfield id="minute"
									name="generateKundli.minute" maxlength="2" cssClass="tob"
									></s:textfield></td>
							<td width="4%" align="left"></td>
							<td width="27%" align="left"><s:textfield id="second"
									name="generateKundli.second" maxlength="2" cssClass="tob"
									></s:textfield></td>
								<td width="10%" align="right" style="padding-left: 10px;"><a
								href="##" data-ajax="false"><img style="width: 30px; height: 30px"
									id="currentTime" src="vastuImages/watch-icon.png"
									onclick="currentDateTime()"/> </a></td>

						</tr>
					</table>
				</div>
				<div class="col-sm-4"></div>
			</div>
                        
                        <s:if test='#session.containsKey("18")'>
                       <%-- <div class="form-group">
                                <label for="transit" class="col-sm-3 control-label" >Add Transit :</label>
                                <div class="col-sm-8">
                                        <table width="100%" height="100%">
                                                <tr>
                                                        <td width="25%" align="left"><input type="checkbox"  id="kundliTransit"></input></td>


                                                </tr>
                                        </table>
                                </div>
                                <div class="col-sm-4"></div>
                        </div>
                        </s:if>


                        <div  class="form-group" id="transDate" style="display: none">
                        <label for="date" class="col-sm-3 control-label">Date :</label>
                                <div class="col-sm-8">
                                        <table width="100%" height="100%">
                                                <tr>
                                                        <td width="25%" align="left"><s:textfield id="transitDate"
                                                                        name="generateKundli.transitDate" maxlength="2"
                                                                        onkeyup="transitDateAutoFocus()"></s:textfield></td>
                                                        <td width="3%" align="left"></td>
                                                        <td width="25%" align="left"><s:textfield id="transitMonth"
                                                                        name="generateKundli.transitMonth" maxlength="2"
                                                                        onkeyup="transitDateAutoFocus()"></s:textfield></td>
                                                        <td width="3%" align="left"></td>
                                                        <td width="25%" align="left"><s:textfield id="transitYear"
                                                                        name="generateKundli.transitYear" maxlength="4"
                                                                        onkeyup="transitDateAutoFocus()"></s:textfield></td>
                                                        <td width="9%"></td>
                                                        <td width="10%" align="right" style="padding-left: 10px;"><a
                                                                href="##"><img style="width: 30px; height: 30px"
                                                                        id="currentTime" src="vastuImages/watch-icon.png"
                                                                        onclick="transitCurrentDateTime()" /> </a></td>

                                                </tr>
                                        </table>
                                </div>
                                <div class="col-sm-4"></div>
                        </div>

                        <div  class="form-group" id="transTime" style="display: none">
                        <label for="time" class="col-sm-3 control-label">Time :</label>
                                <div class="col-sm-8">
                                        <table width="100%" height="100%">
                                                <tr>
                                                        <td width="25%" align="left"><s:textfield id="transitHour"
                                                                        name="generateKundli.transitHour" maxlength="2"
                                                                        onkeyup="transitTimeAutoFocus()"></s:textfield></td>
                                                        <td width="3" align="left"></td>
                                                        <td width="25%" align="left"><s:textfield id="transitMinute"
                                                                        name="generateKundli.transitMinute" maxlength="2"
                                                                        onkeyup="transitTimeAutoFocus()"></s:textfield></td>
                                                        <td width="3" align="left"></td>
                                                        <td width="25%" align="left"><s:textfield id="transitSecond"
                                                                        name="generateKundli.transitSecond" maxlength="2"
                                                                        onkeyup="transitTimeAutoFocus()"></s:textfield></td>
                                                        <td width="19%"></td>
                                                </tr>
                                        </table>
                                </div>
                                <div class="col-sm-4"></div>
                        </div>
<div class="form-group">
				<label for="remarks" class="col-sm-3 control-label">Remarks :</label>
				<div class="col-sm-8">
					<table width="100%" height="100%">
						<tr>
							<td width="90%"><s:textarea id="remarks"
									name="generateKundli.remarks" cols="40" rows="10"></s:textarea></td>
							<td width="10%" align="right" style="padding-left: 10px;"><a
								href='<c:url value="kundliLog.action"/>' data-ajax="false"><img
									style="width: 30px; height: 30px" src="vastuImages/search.png">
							</a></td>
						</tr>
					</table>
				</div>
			</div>
--%>


			<div class="form-group">
				<div class="col-sm-4"></div>
				<div class="col-sm-4 ui-content">
					<button type="submit" id="place" class="form-control"
				 style="background-color: #FFA500; color: white">Edit</button>
				</div>
				<%--<div class="col-sm-4 ui-content">
					<button type="reset" value="Reset" class="form-control" id="reset"
						style="background-color: #FFA500; color: white">Reset</button>
				</div>--%>
			</div>
		</form>
	</div>
	<div class="col-sm-2"></div>
</div>

<%--<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
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
--%>

