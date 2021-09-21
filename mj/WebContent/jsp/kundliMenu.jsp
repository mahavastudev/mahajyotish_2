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
	    success: function(result){     
	    }
	    });

 
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
							<li>
					<a href='#' id="modList" onclick="openEmailModel()">Email PDF</a>
					</li>
				
						</ul>
		</div>

<div data-role="header" data-theme="b" data-position="fixed">
    <div class="row" id="headerStrip"></div>
          <div class="row col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-1 col-sm-offset-5 col-xs-3 col-xs-offset-5">
           <img id="headerImg" src="vastuImages/mjk-logo.png">
           <p id="orangeHeader">MahaJyotish Kundli</p>
          </div>
          <a href="#common-panel" data-icon="bars" data-transition="fade" class="ui-btn-left" data-theme="f" style="box-shadow: 0 0px 0px rgba(0,0,0,.15);background-color: transparent; visibility: hidden; border: 0px" id="kundliMenu"> Menu</a>
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
