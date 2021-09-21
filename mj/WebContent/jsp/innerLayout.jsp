<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="vastuImages/favicon.png">
<title>MahaJyotish Kundli</title>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.4.5.js"></script>
<link rel="stylesheet" href="css/jquery-ui.min.css">
<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/bootstrap.min.js"></script>
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

#pdfView
{
height: 450px; 
}
	
}

@media only screen and (min-height: 500px) {
	
}

@media only screen and (max-height: 500px) {

#headerTable
{
height:50px; 
}	

#pdfView
{
height: 450px; 
}
}

@media only screen and (min-width: 750px) {

#headerTable
{
height:90px; 
}


#pdfView
{
height: 450px; 
}

}

 .ui-header-fixed, .ui-footer-fixed
{
    background-color: #F8F8F8 ;
	border-bottom: 0px solid;
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

#VastuLogin{
min-height:auto;
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
function preloader(){
            document.getElementById("loader").style.display = "none";
            document.getElementById("content").style.display = "block";
        }
        window.onload = preloader;

$(window).load(function() {
//      $("#loader").fadeOut("slow");
         $("#loader").hide(); 
});
</script>



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kundli</title>
</head>



<body>
<div id="loader"></div>
<div id="content">


	<div data-role="page" id="VastuLogin">
		<div data-role="panel" id="common-panel" data-display="overlay" data-position-fixed="true" data-theme="a" >
			<ul data-role="listview" id="sortable">
				<li data-icon="delete"><a href="#" data-rel="close"
					onclick="closeApp()" id="modList">Close Menu</a></li>
                                
				<s:if test='%{#session.containsKey("3") || #session.containsKey("7")}'>
                                <li id="lstStyle">ADMINISTRATION</li>
                                </s:if>
				<s:if test='#session.containsKey("3")'>
					<li>
					<a href='<c:url value="userMgt.action"/>' id="modList" data-ajax="false">User Management</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("7")'>
					<li>
					<a href='<c:url value="roleMgt.action"/>' id="modList" data-ajax="false">Role Management</a>
					</li>
				</s:if>


		<%--		<li id="lstStyle">LOG-BOOK</li>
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
                                <li><a href='<c:url value="Generate.action"/>'  id="modList" data-ajax="false">Generate Kundli</a>
                                </li>


<%--
				<s:if test='#session.containsKey("11")'>
					<li>
					<a href='<c:url value="kundliDetail.action?id=11"/>' id="modList" data-ajax="false">Lagna Chart</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("12")'>
					<li>
					<a href='<c:url value="kundliDetail.action?id=12"/>' id="modList" data-ajax="false">Planetry Position in Detail</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("13")'>
					<li>
					<a href='<c:url value="kundliDetail.action?id=13"/>' id="modList" data-ajax="false">Cuspal in Detail</a></li>
				</s:if>
				<s:if test='#session.containsKey("14")'>
					<li>
					<a href='<c:url value="kundliDetail.action?id=14"/>' id="modList" data-ajax="false">Dasha Chart</a></li>
				</s:if>
				<s:if test='#session.containsKey("16")'>
					<li>
					<a href='<c:url value="kundliDetail.action?id=16"/>' id="modList" data-ajax="false">Kundli Circle</a></li>
				</s:if>
				<s:if test='#session.containsKey("15")'>
					<li>
					<a href='<c:url value="emailPdf.action"/>' id="modList" data-ajax="false">Email PDF</a>
					</li>
				</s:if>
				<li>
				<a href='<c:url value="GenerateKundliFromDetails.action?genType=V"/>' id="modList" data-ajax="false" >Download PDF</a>
				</li>   --%>
				
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
								<s:if test='#session.containsKey("25")'>
									<li>
					                <a href='<c:url value="changePass.action"/>' id="modList" data-ajax="false">Change Password</a>
	                                </li>
								</s:if>
                                <li>
                                <a href='<c:url value="Logout.action?message=1"/>' id="modList" data-ajax="false">Logout</a>
                                </li>
			</ul>
		</div>

		
	<div data-role="header" data-theme="b" data-position="fixed">
	 <div class="row" id="headerStrip">
      </div>
	
	<div class="row col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-1 col-sm-offset-5 col-xs-3 col-xs-offset-5">
        <img id="headerImg" src="vastuImages/mjk-logo.png">
        <p id="orangeHeader">MahaJyotish Kundli</p>
     </div>
 			<!--  <a href="#common-panel" data-icon="bars" data-transition="fade" class="ui-btn-left" data-theme="f" style="box-shadow: 0 0px 0px rgba(0,0,0,.15);;background-color: transparent;border: 0px" id="kundliMenu">Menu</a> -->
     </div>
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
				<div class="modal-header" style="padding: 10px 25px;background-color: transparent;">
					<h4 id="alertMsg">
					</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" style="background-color: #FFA500; color: white;" data-dismiss="modal">OK</button>
				</div>
			</div>
		</div>
	</div></div>

<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
                data-target="#loaderDiv" style="display: none;" id="showLoader" data-backdrop="static" data-keyboard="false"></button>
               <div class="modal fade" id="loaderDiv">  
               <div class="modal-dialog">
                    <div class="modal-content" style="background-color: transparent;-moz-box-shadow: 0px 0px 0px 0px #ccc;-webkit-box-shadow: 0px 0px 0px 0px #ccc;box-shadow: 0px 0px 0px 0px #ccc;border: 0px">
                                <div id="loader" style="display: block;width:100%;height:200px;"></div>
                                </div>
                        </div>
                </div>
        </div>



</body>
</html>
