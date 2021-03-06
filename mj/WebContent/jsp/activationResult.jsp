<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="refresh" content="3;url=register.action" />
<link rel="icon" href="vastuImages/favicon.png">
<title>MahaJyotish Kundli</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.mobile-1.4.5.js"></script>
<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/kundlichart.css">
<link rel="stylesheet" type="text/css" href="css/default.css" />
<style>
.ui-header-fixed,.ui-footer-fixed {
	background-color: #F8F8F8;
	border-bottom: 0px solid transparent;
}

@media screen and (max-width:500px) {
	.form-horizontal .form-group {
		
	}
}
.modal {
    position: fixed;
    top: 25% !important;
}
.ui-btn-icon-left::after, .ui-btn-icon-right::after, .ui-btn-icon-top::after, .ui-btn-icon-bottom::after, .ui-btn-icon-notext::after
{
background-color: transparent;
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

	$( document ).ready(function() {
	    	var msg=document.getElementById('msg').value;
	    	if(msg == null || msg ==""){  	
	    	}
	    	else{
                	$("#alertMsg").html("<h5>"+msg+"<h5>");
                	$("#openAlert").click();
	    		msg="";
	    	} 

 
	});
	
	
</script>
</head>
<body>
<s:hidden name="message" id="msg"></s:hidden>


<div class="row">

	<div data-role="header" data-theme="b" data-position="overlay" >
		<div class="row" id="headerStrip"></div>

		<div
			class="col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-1 col-sm-offset-5 col-xs-3 col-xs-offset-5">
			<img id="headerImg" src="vastuImages/mjk-logo.png">
			<p id="orangeHeader">MahaJyotish Kundli</p>
		</div>
	</div>
</div>

	<div class="row" style="margin-top:50px;">
		<div class="col-sm-3 hidden-xs"></div>
		<div class="col-sm-5  col-xs-12">
			<div class="col-sm-12" style="margin-top: 0px; margin-bottom: 10px">
				<div class="container-fluid"
					style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
					<div class="row-fluid">
						<div class="span8">
							<p class="header" align="center">Activation Failed</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-4 hidden-xs"></div>
	</div>



	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6" id="formDiv" style="min-height:300px;">
			<div class="span8" style="margin-top:130px;">
                        	<p align="center" style="color: #FFA500;font-size: 30px;">Failed !!</p>
                        </div>
			<div style="margin-left:200px;">
				<h6><%=request.getAttribute("message") %></h6>
			</div>
		</div>
		<div class="col-sm-2"></div>
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
					<button type="button" class="btn btn-default" style="background-color: #FFA500; color: white; width: 80px;" data-dismiss="modal">OK</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
