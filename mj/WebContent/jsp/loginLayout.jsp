<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.4.5.js"></script>
<script src="js/look.js"></script>

<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/kundlichart.css">
<link rel="stylesheet" type="text/css" href="css/default.css" />

<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) { /* Enable use of floated navbar text */
	
}

@media only screen and (min-height: 500px) {
	
}

@media only screen and (max-height: 500px) {

#headerTable
{
height:50px; 
}	
}

@media only screen and (min-width: 750px) {

#headerTable
{
height:90px; 
}
}

 .ui-header-fixed, .ui-footer-fixed
{
    background-color: #F8F8F8 ;
	border-bottom: 0px solid;
}

 #modList
{
 background-color: #B95F00;
 color: white; 
 text-shadow: 0 0px 0 #F3F3F3; 
 border-bottom: 1px solid orange;
}

#modList:hover
{
background-color: #E8E8E8;
color: black;
text-shadow: 0 0px 0 #F3F3F3;
}
#common-panel
{
background-color: #B95F00; 
}
.form-horizontal .control-label
{
color: #686868 ;
font-weight: bold;
}

.ui-btn-icon-left::after, .ui-btn-icon-right::after, .ui-btn-icon-top::after, .ui-btn-icon-bottom::after, .ui-btn-icon-notext::after
{
background-color: transparent;
}

.ui-bar-b, .ui-page-theme-b .ui-bar-inherit, html .ui-bar-b .ui-bar-inherit, html .ui-body-b .ui-bar-inherit, html body .ui-group-theme-b .ui-bar-inherit
{
background-color: transparent;
border:0px;
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
        background: url('vastuImages/loading.gif') 50% 50% no-repeat rgb(249,249,249);
 }


</style>
<script>
function preloader(){
            document.getElementById("loader").style.display = "none";
            document.getElementById("content").style.display = "block";
        }
        window.onload = preloader;

$(window).load(function() {
//      $("#loader").fadeOut("slow");
         $("#loader").hide();  
})
</script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kundli</title>
</head>



<body>

<div id="loader"></div>
<div id="content">


	<div data-role="page" id="VastuLogin">
		<div data-role="panel" id="common-panel" data-display="overlay" data-theme="a" style="margin-top: 70px;">
			<ul data-role="listview" id="sortable">
				<li data-icon="delete"><a href="#" data-rel="close"
					onclick="closeApp()" id="modList">Close</a></li>
                                <s:if test='{#session.containsKey("8") || #session.containsKey("7")}'>
                                <li id="adminLabl">ADDMINISTRATION</li>
                                </s:if>                         
				<s:if test='#session.containsKey("8")'>
					<li><a href="userMgt.action" id="modList">User Management</a></li>
				</s:if>
				<s:if test='#session.containsKey("7")'>
					<li><a href="roleMgt.action" id="modList">Role Management</a></li>
				</s:if>
                                <li id="kundliLabl">KUNDLI</li>
                                <li><a href="Generate.action"id="modList">Generate Kundli</a></li>

<%--				<s:if test='#session.containsKey("11")'>
					<li><a href="lagnaChart.action" id="modList">Lagna Chart</a></li>
				</s:if>
				<s:if test='#session.containsKey("12")'>
					<li><a href="planetryDet.action" id="modList">Planetry Position in Detail</a></li>
				</s:if>
				<s:if test='#session.containsKey("13")'>
					<li><a href="cuspalDet.action" id="modList">Cuspal in Detail</a></li>
				</s:if>
				<s:if test='#session.containsKey("14")'>
					<li><a href="dashaChart.action" id="modList">Dasha Chart</a></li>
				</s:if> --%>
				<s:if test='#session.containsKey("25")'>
					<li ><a href="changePass.action" id="modList">Change Password</a></li>
				</s:if>
				<li><a href="Logout.action" id="modList">Logout</a></li>
			</ul>
		</div>

		<div data-role="header" data-theme="b">
		 <div class="row" style="height: 50px;margin-top:20px;background-color: orange">
      </div>


	
		<div class="col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-1 col-sm-offset-5 col-xs-3 col-xs-offset-5"  style="padding:0px;margin-top: -66px;">
        	<img class="img-responsive" src="vastuImages/mjk-logo.png">
        	<br/>
        	<p class="orangeHeader" >MahaJyothish Kundli</p> 
    	 </div>
			 
						<a href="#common-panel" data-icon="bars" data-iconpos="notext"	data-transition="fade" class="ui-btn-left" data-theme="f" style="margin-top: 5px;background-color: transparent;border: 0px"></a> 
		</div>
	
			
			<div class="col-sm-12 col-xs-12">
			<tiles:insertAttribute name="body"></tiles:insertAttribute>
		</div>
<!-- 	</div> -->
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
</div>
</body>
</html>
