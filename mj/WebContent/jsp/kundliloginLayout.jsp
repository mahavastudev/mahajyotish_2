<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="vastuImages/favicon.png">
<title>MahaJyotish Kundli</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="css/jquery-ui.min.css">
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/kundlichart.css">
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script type="text/javascript" src="js/jquery.mobile-1.4.5.js"></script>
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

.ui-header-fixed,.ui-footer-fixed {
	background-color: #F8F8F8;
	border-bottom: 0px solid;
}

.form-horizontal .control-label {
	color: #686868;
	font-weight: bold;
}

.ui-btn-icon-left::after,.ui-btn-icon-right::after,.ui-btn-icon-top::after,.ui-btn-icon-bottom::after,.ui-btn-icon-notext::after
	{
	background-color: transparent;
}
.modal {
    position: fixed;
    top: 25% !important;
}

 div#content {
    display: none;
    }
    #loadingImg
        { position:fixed; }

     #loader {
        position: fixed;
        left:0 ;
        top: 0;
        width: 100%;
        height:100%;
        z-index: 9999;
        background: url('vastuImages/loading.gif') 50% 50% no-repeat ;
 }



</style>
<script>
$(document).ready(function(){
    $("#email").addClass("form-control");
   // $('#loaderDiv').modal({ backdrop: 'static', keyboard: false });
});
function preloader(){
            document.getElementById("loader").style.display = "none";
            document.getElementById("content").style.display = "block";
        }
        window.onload = preloader;

$(window).load(function() {
//      $("#loader").fadeOut("slow");
         $("#loader").hide();  
});
function opendwnldKundliModel(){
        $("#openDownloadAlert").click();
}

function openEmailModel(){
     $("#emaiAlertMsg").html("");
     $("#openEmailKundli").click();
}

function sendEmail()
{
    var kundliImage=document.getElementById("kundliCirName").value;
    var email = document.getElementById("email").value;
    var dashaDetail = document.getElementById("dasha").value; 
    var houseDetail = document.getElementById("house").value;
    var specialKundli = document.getElementById("specialKundli").value;
    var kundli = document.getElementById("kundli").value;
    
    if(dashaDetail != null){
        dashaDetail = document.getElementById("dasha").checked;
    }
    if(houseDetail != null){
        houseDetail = document.getElementById("house").checked;
    }
    if(specialKundli != null){
    	specialKundli = document.getElementById("specialKundli").checked;
    }
    if(kundli != null){
    	kundli = document.getElementById("kundli").checked;
    }
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
    if(!(specialKundli || kundli || houseDetail || dashaDetail))
    {
        $("#emaiAlertMsg").html("<h5>Please select one option</h5>");
        return false;
    } 
    var url = "?genType="+genType+"&userInfo.email="+email+"&userInfo.dashaDetail="+dashaDetail+"&userInfo.houseDetail="+houseDetail+"&kundliCircleName="+kundliImage+"&userInfo.specialKundli="+specialKundli+"&userInfo.kundli="+kundli; 
    url = "<%=response.encodeURL("GenerateKundliFromDetails.action")%>"+url;
    $("#closeEmailModel").click();
    $("#alertMsg").html("<h5>Kundli has been sent to you<h5>");
    $("#openAlert").click();
     $.ajax({
	    type: "POST",
	    url: url,
	    success: function(result){     
	    }
	    });

 
}

function validateDownloadKundli(){
	if ($('#dashaDetl').is(":checked"))
	{
		$("#dashaDetl").val("true");
	}
        
	if ($('#houseDetl').is(":checked"))
     {
                $("#houseDetl").val("true");
    }
       
	if($("#specialDetl").is(":checked")){
		$("#specialDetl").val("true");
	}
	kundliImage=document.getElementById("kundliCirName").value;
	document.getElementById("kundliCircleName").value=kundliImage;

/*	var userId="<s:property value='%{#session.userName}'/>";
	$.ajax({
       		url : "checkKundliCircle",
               	type : "POST",
               	async : false,
             	data : {"userId" : userId},
            	success : function(data) {
  			result=data;	
              	}
      	});
	var test=result.toString().trim();
	if(test=='Y'){
		$("#closeDownloadModel").click();
   		return true;	
	}else{
		$("#alertMsg").html("<h5>Unable to download file. Please Try again<h5>");
                $("#openAlert").click();
		$("#closeDownloadModel").click();
		return false;
	}*/
  	$("#closeDownloadModel").click();
 	return true; 
}



function downloadPDFReq(param){
	//generateType
	//window.location='GenerateKundliFromDetails.action?genType=V&kundliCircleName='+$("#kundliCirName").val()+'&'+param;
	console.log("param==========>"+param);
    $("#houseDetl").val("false");
    $("#specialDetl").val("false");
    $("#dashaDetl").val("false");
    $('#normalKundli').val("false");
    $('#houseDetl').prop('checked', false);
    $('#normalKundli').prop('checked', false);
$('#specialDetl').prop('checked', false);
$('#dashaDetl').prop('checked', false);


    if(param=='userInfo.houseDetail=true'){
                    console.log("house");
                    $("#houseDetl").val("true");
                    $('#houseDetl').prop('checked', true);
    }
    if(param=='normal=true'){
                    $('#normalKundli').val("true");
                    console.log("normal");
                    $('#normalKundli').prop('checked', true);
    }

    if(param=='userInfo.specialKundli=true'){

                    console.log("special");
            $("#specialDetl").val("true");
            $('#specialDetl').prop('checked', true);
    }

    if(param=='userInfo.dashaDetail=true'){
            console.log("dasha");
                    $("#dashaDetl").val("true");
            $('#dashaDetl').prop('checked', true);
    }
    $('.downloadPDF_V').click();
}

function saveKundliDetail(){
        var uId= "<s:property value='%{#session.userName}'/>";
        var name = "<s:property value='%{#session.kundliName}'/>";
        var cityName = "<s:property value='%{#session.pob}'/>";
        var cityCode = "<s:property value='%{#session.cityCode}'/>";
        var dob = "<s:property value='%{#session.dob}'/>";
        var tob= "<s:property value='%{#session.tob}'/>";
        var lat= "<s:property value='%{#session.lat}'/>";
        var longitude= "<s:property value='%{#session.long}'/>";
        var country= "<s:property value='%{#session.country}'/>";
        var state= "<s:property value='%{#session.state}'/>";
        var isTransitKundli = "<s:property value='%{#session.isTransitKundli}'/>";
        var isTransitLocation="<s:property value='%{#session.transitLocation}'/>";
        var transitCountry;var transitState;var transitTags;
        var transitLatitude;var transitLongitud;var transitCityCodes
        if(isTransitKundli==0)
        {
          var transitDob ="";
          var transitTob ="";
          isTransitLocation=0;
          transitCountry="";
          transitState="";
          transitTags="";
          transitLatitude="";
          transitLongitude="";
          transitCityCodes="";
        }
        else
        {
        	var transitDob = "<s:property value='%{#session.transitDob}'/>";
            var transitTob = "<s:property value='%{#session.transitTob}'/>";	
        }
        if(isTransitLocation==0)
        {
        	console.log("aaaaaaaaaaaaaaaaaaa");
        	transitCountry="";
            transitState="";
            transitTags="";
            transitLatitude="";
            transitLongitude="";
            transitCityCodes="";
        }
        else
        {
        	console.log("bbbbbbbbbbbbbbbbbbbbbb");
        	transitCountry="<s:property value='%{#session.transitCountry}'/>";
    		transitState="<s:property value='%{#session.transitState}'/>";
    		transitTags="<s:property value='%{#session.transitCity}'/>";
    		transitLatitude="<s:property value='%{#session.transitLatitude}'/>";
    		transitLongitude="<s:property value='%{#session.transitLongitude}'/>";
    		transitCityCodes="-00000000";
        }
        var reqId=$("#reqId").val();
        var remarks="<s:property value='%{#session.remarks}'/>";
        var occupation="<s:property value='%{#session.occupation}'/>";
        var accountId="<s:property value='%{#session.accountId}'/>";
        var contactId="<s:property value='%{#session.contactId}'/>";
        console.log("isTransitLocation==>"+isTransitLocation+" isTransitKundli==>"+isTransitKundli+" transitTob==>"+transitTob+" transitDob==>"+transitDob);
        var url = "?name="+name+"&city="+cityName+"&cityCode="+cityCode+"&dob="+dob+"&tob="+tob+"&transitDob="+transitDob+"&transitTob="+transitTob+"&isTransitKundli="+isTransitKundli+"&latitude="+lat+"&longitude="+longitude+"&country="+country+"&state="+state+"&userId="+uId+"&requestId="+reqId+"&remarks="+remarks+"&occupation="+occupation+"&accountId="+accountId+"&contactId="+contactId+"&transitLocation="+isTransitLocation+"&transitCountryCode="+transitCountry+"&transitStateCode="+transitState+"&transitCity="+transitTags+"&transitLatitude="+transitLatitude+"&transitLongitude="+transitLongitude; 
        url = "<%=response.encodeURL("saveKundli")%>"+url;
	if (confirm('Do You want to Update Kundli?')) {
		$.ajax({
                	type: "POST",
                 	url: url,
                 	success: function(result){
                 		$("#saveKundliInfo").hide();
		 		var splitStr=result.split(",");
				$("#reqId").val(splitStr[1]);
                 		$("#alertMsg").html("<h5>"+splitStr[0]+"<h5>");
                 		$("#openAlert").click();
                 	}
      		});
	}
    }
    




</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kundli</title>
</head>



<body>
<s:hidden name="kundliCirName" id="kundliCirName"></s:hidden>
<div id="loader"></div>
<div id="content">


	<div data-role="page" id="VastuLogin">
		<div data-role="panel" id="common-panel" data-display="overlay" data-position-fixed="true"
			data-theme="a">
			<ul data-role="listview" id="sortable">
				<li data-icon="delete"><a href="#" data-rel="close"
					onclick="closeApp()" id="modList">Close Menu</a></li>
                                <s:if test='%{#session.containsKey("3") || #session.containsKey("7")}'>
                                <li id="lstStyle">ADMINISTRATION</li>
                                </s:if>
				<s:if test='#session.containsKey("3")'>
					<li><a href='<c:url value="userMgt.action"/>' id="modList"
						data-ajax="false">User Management</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("7")'>
					<li><a href='<c:url value="roleMgt.action"/>' id="modList"
						data-ajax="false">Role Management</a>
					</li>
				</s:if>

			<%--	<li id="lstStyle">LOG-BOOK</li>
                                <li>
                                <a href='<c:url value="addEvent.action"/>' id="modList" data-ajax="false">Add Event</a>
                                </li>

                                <li >
                                <a href='<c:url value="viewEvent.action"/>' id="modList" data-ajax="false">View Event</a>
                                </li>

                               <li >
                                <a href='<c:url value="logBookProf.action"/>' id="modList" data-ajax="false">Manage Log-Book Profile</a>
                                </li>--%>

                                <li id="lstStyle">KUNDLI</li>
                                <li><a href='<c:url value="Generate.action"/>' id="modList"
                                        data-ajax="false">Generate Kundli</a>
                                 </li>
                                 <s:if test='%{#session.isSave=="N"}'>
                                 <li id="saveKundliInfo"><a href='#' id="modList" onclick="saveKundliDetail()" data-ajax="false">Save Kundli</a>
                                </li>
                                </s:if>

				<s:if test='#session.containsKey("11")'>
					<li id="lagnaLi"><a href='#' id="modList" data-ajax="false">Lagna Chart</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("12")'>
					<li id="PlanetLi"><a href='#' id="modList" data-ajax="false">Planetry & Cuspal Details</a>
					</li>
				</s:if>
                                <s:if test='%{#session.isTransitKundli=="1"}'>
                                        <li id="transitPlntCuspDetl"><a href='#' id="modList" data-ajax="false">Transit Planetry & Cuspal Details</a>
                                        </li>
                                </s:if>
				<s:if test='#session.containsKey("13")'>
					<li id="CuspalLi"><a href='#' id="modList" data-ajax="false">Cuspal in Detail</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("14")'>
					<li id="DashaLi"><a href='#' id="modList" data-ajax="false">Vimshottari Dasha</a>
					</li>
				</s:if>
                                <s:if test='#session.containsKey("17")'>
                                        <li id="aspectCh"><a href='#' id="modList" data-ajax="false">Aspect Chart</a>
                                        </li>
                                </s:if>
				<s:if test='#session.containsKey("16")'>
					<li id="kundliCircle"><a href='#' id="modList" data-ajax="false">Kundli Circle</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("19")'>
                                        <li id="BTRLi"><a href='#' id="modList" data-ajax="false">BTR</a>
                                        </li>
                                </s:if>
				<s:if test='#session.containsKey("15")'>
					<li>
					<a href='#' id="modList" onclick="openEmailModel()">Email PDF</a>
					</li>
				</s:if>
				   			<s:if test='#session.containsKey("B")'>
				   								<!-- <li id="lstStyle">Download PDF</li> -->
				   								<%-- href='<c:url value="GenerateKundliFromDetails.action?genType=V"/>' --%>
				   								<s:if test='#session.containsKey("42")'>	
				   								<li><a onclick="downloadPDFReq('normal=true')" href="#"
                                                        id="modList" data-ajax="false">Download Kundli</a>
                                                </li>
                                                </s:if>
												<s:if test='#session.containsKey("36")'>
	                                                <li>
	                                                <a href='#'
	                                                        id="modList" data-ajax="false"
	                                                        onclick="downloadPDFReq('userInfo.specialKundli=true')">Download Basic Kundli</a>
	                                                </li>
                                        		</s:if>
                                        		<s:if test='#session.containsKey("1")'>
	                                                <li>
	                                                <a href='#'
	                                                        id="modList" data-ajax="false"
	                                                        onclick="downloadPDFReq('userInfo.dashaDetail=true')">Download Dasha Kundli</a>
	                                                </li>
                                        		</s:if>
                                        		<s:if test='#session.containsKey("2")'>
	                                                <li>
	                                                <a href='#'
	                                                        id="modList" data-ajax="false"
	                                                        onclick="downloadPDFReq('userInfo.houseDetail=true')">Download House Kundli</a>
	                                                </li>
                                        		</s:if>				   			
                                        <%-- <s:if test='{#session.containsKey("1") || #session.containsKey("2") || #session.containsKey("36")}'>
                                                <li><a href='#' id="modList" data-ajax="false"
                                                        onclick="opendwnldKundliModel()">Download PDF</a>
                                                </li>
                                        </s:if>
                                        <s:else>
                                                <li><a
                                                        href='<c:url value="GenerateKundliFromDetails.action?genType=V"/>'
                                                        id="modList" data-ajax="false">Download Pdf</a>
                                                </li>
                                        </s:else> --%>
                                </s:if>

				<li id="lstStyle">LOG-BOOK</li>
                                <li>
                                <a href='<c:url value="addEvent.action"/>' id="modList" data-ajax="false">Add Event</a>
                                </li>

                                <li >
                                <a href='<c:url value="viewEvent.action"/>' id="modList" data-ajax="false">View Event</a>
                                </li>

                               <li >
                                <a href='<c:url value="logBookProf.action"/>' id="modList" data-ajax="false">Manage Log-Book Profile</a>
                                </li>				



				<%-- <li><a href='<c:url  value="changePass.action"/>' id="modList"
					data-ajax="false">Change Password</a>
				</li> --%>

					<s:if test='#session.containsKey("25")'>
						<li><a href='<c:url value="changePass.action"/>' id="modList"
							data-ajax="false">Change Password</a></li>
					</s:if>

					<li><a href='<c:url value="Logout.action?message=1"/>' id="modList"
					data-ajax="false">Logout</a>
				</li>
			</ul>
		</div>

<div data-role="header" data-theme="b" data-position="fixed">
    <div class="row" id="headerStrip"></div>
          <div class="row col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-1 col-sm-offset-5 col-xs-3 col-xs-offset-5">
           <img id="headerImg" src="vastuImages/mjk-logo.png">
           <p id="orangeHeader">MahaJyotish Kundli</p>
          </div>
          <a href="#common-panel" data-icon="bars" data-transition="fade" class="ui-btn-left" data-theme="f" style="box-shadow: 0 0px 0px rgba(0,0,0,.15);background-color: transparent; border: 0px" id="kundliMenu"> Menu</a>
</div> 
</div>

               
<div class="col-sm-12 col-xs-12">
                <tiles:insertAttribute name="body"></tiles:insertAttribute>
        </div>
</div>


<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
		data-target="#alertModel" style="display: none;" id="openAlert"></button>
	<div class="modal fade" id="alertModel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="padding: 10px 25px;">
					<h4 id="alertMsg">
					</h4>
				</div>
				<div class="modal-footer">
				 <button type="button" class="btn btn-default" style="background-color: #FFA500; color: white;" data-dismiss="modal">OK</button>
				</div>
			</div>
		</div>
	</div>



<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
                data-target="#loaderDiv" style="display: none;" id="showLoader" data-backdrop="static" data-keyboard="false"></button>
        <div class="modal fade" id="loaderDiv">
                <div class="modal-dialog">
                        <div class="modal-content" style="background-color: transparent;-moz-box-shadow: 0px 0px 0px 0px 0ccc;-webkit-box-shadow: 0px 0px 0px 0px #ccc;box-shadow: 0px 0px 0px 0px #ccc;border: 0px">
                                <div id="loader" style="display: block;width:100%;height:200px;"></div>
                        </div>
                </div>
        </div>
        


<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
                data-target="#dwnldKundli" style="display: none;" id="openDownloadAlert"></button>
<div class="modal fade" id="dwnldKundli">
        <div class="modal-dialog">
                <div class="modal-content">
                        <div class="modal-header" style="padding: 10px 25px;">
                                <button type="button" class="close" id="closeDownloadModel" data-dismiss="modal" style="width: 20px;">&times;</button>
                                <h5>
                                        <span class="glyphicon glyphicon-download-alt"></span> Download Kundli </h5>
                        </div>

                        <div class="modal-body" style="padding: 40px 50px;">
              <form action='<c:url value="GenerateKundliFromDetails.action"/>'
						data-ajax="false" method="get" onsubmit="return validateDownloadKundli()">
				
				<s:hidden name="kundliCircleName" id="kundliCircleName"></s:hidden>
				
				<s:if test='#session.containsKey("36")'>
				<table><tr><td>
					<label for="dashaDetail"><span
						class="glyphicon glyphicon-asterisk"></span> Basic Kundli </label></td><td>
				<input type="checkbox" id="specialDetl" name="userInfo.specialKundli" style="margin-left:10px;margin-top: -2px;">
				</td></tr></table>

				</s:if>
						
				<s:if test='#session.containsKey("1")'>
				<table><tr><td>
					<label for="dashaDetail"><span
						class="glyphicon glyphicon-asterisk"></span> Dasha Detail </label></td><td>
				<input type="checkbox" id="dashaDetl" name="userInfo.dashaDetail"  style="margin-left:10px;margin-top: -2px;">
				</td></tr></table>

				</s:if>
				<s:if test='#session.containsKey("2")'>
				<table style="margin-bottom: 15px;"><tr>
				    <td>
					<label for="houseDetail"><span
						class="glyphicon glyphicon-home"></span> House Detail </label></td>
				    <td>
				<input type="checkbox" id="houseDetl" name="userInfo.houseDetail" style="margin-left:10px;margin-top: -2px;">
				</td></tr></table>
				</s:if>
				
				<input type="hidden" id="generateType" name="genType" value="V"/>
				
				<button type="submit" class="btn btn-success btn-block downloadPDF_V" style="background-color: #FFA500; color: white;">
				  <span class="glyphicon glyphicon-download-alt"></span> Download
				</button>  
                            </form>         
                        </div>
                </div>
        </div>
</div>

<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
                data-target="#emailKundliModel" style="display: none;" id="openEmailKundli"></button>
<div class="modal fade" id="emailKundliModel">
        <div class="modal-dialog">
                <div class="modal-content">
                        <div class="modal-header" style="padding: 10px 25px;">
                                <button type="button" class="close" id="closeEmailModel" data-dismiss="modal" style="width: 20px;">&times;</button>
                                <h5><span class="glyphicon glyphicon-envelope"></span> Email Kundli</h5>
                        </div>

                        <div class="modal-body" style="padding: 40px 50px;">   
                            <h5 id="emaiAlertMsg" style="color: red;"></h5>     
			    			<div class="form-group">
					    		<label for="eamil"><span class="glyphicon glyphicon-envelope"></span> Email</label> 
	                           	<s:textfield id="email" value="%{#session.emailId}"></s:textfield>
				    		</div>
				    		
				    		<s:if test='#session.containsKey("42")'>
							    <table><tr><td>
								    <label for="basicKundli"><span class="glyphicon glyphicon-asterisk"></span> Kundli </label></td><td>
				                            <input  type="checkbox" id="kundli" style="margin-left:10px;margin-top: -2px;">
							    </td></tr></table>
			    		   </s:if>
			    		   <s:else>
                                <input type="hidden" id="kundli" name="kundli"/>
                           </s:else>
				    		
				    		
			    			<s:if test='#session.containsKey("36")'>
							    <table><tr><td>
								    <label for="dashaDetail"><span
									    class="glyphicon glyphicon-asterisk"></span> Basic Kundli </label></td><td>
				                            <input  type="checkbox" id="specialKundli" style="margin-left:10px;margin-top: -2px;">
							    </td></tr></table>
			    		</s:if>
			    		<s:else>
                           <input type="hidden" id="specialKundli" name="specialKundli"/>
                        </s:else>
                        
					    <s:if test='#session.containsKey("1")'>
						    <table><tr><td>
							    <label for="dashaDetail"><span class="glyphicon glyphicon-asterisk"></span> Dasha Detail </label></td><td>
			                    <input  type="checkbox" id="dasha" style="margin-left:10px;margin-top: -2px;">
						    </td></tr></table>
					    </s:if>
                        <s:else>
                           <input type="hidden" id="dasha" name="dasha"/>
                        </s:else>

					    <s:if test='#session.containsKey("2")'>
						    <table style="margin-bottom: 15px;"><tr>
							<td>
							    <label for="houseDetail"><span class="glyphicon glyphicon-home"></span> House Detail </label></td>
							<td>
			                   	<input  type="checkbox" id="house" style="margin-left:10px;margin-top: -2px;">
						    </td></tr></table>
					    </s:if>
                        <s:else>
                           <input type="hidden" id="house" name="house"/>
                        </s:else>

				    	<input type="hidden" id="genType" name="genType" value="E"/>
				    	<button type="submit" class="btn btn-success btn-block" style="background-color: #FFA500; color: white;" onclick="return sendEmail()">
				      		<span class="glyphicon glyphicon-envelope"></span> Send
				    	</button>         
                   </div>
                </div>
        </div>
</div>


</body>
</html>
