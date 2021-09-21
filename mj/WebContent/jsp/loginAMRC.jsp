<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
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
function preloader(){
            document.getElementById("loader").style.display = "none";
            document.getElementById("content").style.display = "block";
        }
        window.onload = preloader;

$(window).load(function() {
         $("#loader").hide();
});

	$( document ).ready(function() {
		$("#loader").hide();

		
		 var msg=document.getElementById('msg').value;
		 var newMsg = '${message}';
		 console.log(newMsg+" - ");
                if(newMsg != null && newMsg != ''){
                        msg = newMsg
                }
	    	if(msg == null || msg ==""){  	
	    	}
	    	else{
                	$("#alertMsg").html("<h5>"+msg+"<h5>");
                	$("#openAlert").click();
	    		msg="";
	    	} 

		$("#createac").click(function () {
                	$("#register_new_user").modal({ backdrop: "static" });
            	});
	
 
	});
	
	function register()
	{
 		window.location.href = 'register.action';
	}
	
    function openModel()
    {
        $("#frgtPassAlertMsg").html("");
    	$("#openModel").click();
    }
	
    function validateModelParam(){
    	//alert("inside model param")
    	var email = document.getElementById('email').value;
    	
    	if(email=="" || email==null){
		$("#frgtPassAlertMsg").html("<h5>Please enter user email<h5>");
	        return false;
    	}
    	else
    	   {
    	      	var filter =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    	    	if (!filter.test(email)) 
    		    {	
                     $("#frgtPassAlertMsg").html("<h5>Please provide a valid email address<h5>");
    		    return false;
    	   		}	
    		}
    	return true;
    }
    
	function validate(form)
	{
//		var name= form.elements.name.value;
		    	var name = document.getElementById('name').value;


		if(name=="" || name==null){
			$("#alertMsg").html("<h5>Please enter user email<h5>");
	        $("#openAlert").click();

		        return false;
	    	}
	    	else
	    	   {
	    	      	var filter =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    	    	if (!filter.test(name)) 
	    		    {	
	                     $("#alertMsg").html("<h5>Please provide a valid email address<h5>");
	                     $("#openAlert").click();

	    		    return false;
	    	   		}	
	    		}
	var pass= form.elements.password.value;
	
	/* if(name=="" || name==null)
	{
        $("#alertMsg").html("<h5>Please enter user email<h5>");
        $("#openAlert").click();
	return false;
	} */
	
	if(pass=="" || pass==null)
	{
        $("#alertMsg").html("<h5>Please enter password<h5>");
        $("#openAlert").click();
	return false;
	}
//	document.getElementById("name").value=name.toLowerCase();
	
	$("#showLoader").click();
	return true;
	}



function activationCheck(){
var email = "";
var contactCode = "";
var jsonObj;
var authResponse = "";
var jsonCode = 0;
var respJson ;

email = document.getElementById("registerEmail").value;
contactCode = document.getElementById("custCode").value;
alert("Email:["+email+"],   contactCode:["+contactCode+"].");
var url = "ZohoAuthentication?email="+email+"&contactCode="+contactCode;

	$.ajax({
       		type : 'POST',
        	url     : url,
         	success : function(result){
           		alert(result);
			jsonObj = JSON.parse(result);
			respJson = JSON.stringify(jsonObj.response).split(":")[0].trim().includes("result");
			

			if(respJson){
				authResponse = jsonObj.response.result.Contacts.row.FL[5].content;
			}
			else{
				jsonCode = jsonObj.response.nodata.code;
			}

			alert(authResponse);
			
			if(jsonCode == 4422){
                                document.getElementById("activationFlag").value = 100;
                        }
			else if(authResponse == "MAHAJYOTISH"){
				document.getElementById("activationFlag").value = 102;
			}
			else{
				document.getElementById("activationFlag").value = 101;
			}
			
			document.newClientForm.submit();
          	}

     	});


}
	
	
</script>
</head>
<body>
<s:hidden name="message" id="msg"></s:hidden>
<s:hidden name="identity" id="identity" value="2"></s:hidden>
<div id="msg1" style="display:none;"><%=request.getAttribute("message")%></div>

<div id="loader"></div>
<div id="content">

<div class="row">

	<div data-role="header" data-theme="b" data-position="overlay" >
		<div class="row" id="headerStrip"></div>

		<div
			class="col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-1 col-sm-offset-5 col-xs-3 col-xs-offset-5">
			<img id="headerImg" src="vastuImages/mjk-logo.png">
			<p id="orangeHeader">MahaJyotish Kundli App</p>
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
							<p class="header" align="center">Enter Your Login Details</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-4 hidden-xs"></div>
	</div>



	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-5" id="formDiv">

			<form action='<c:url value="loginAMRC.action"/>' data-ajax="false"
				method="post" onsubmit=" return validate(this)" role="form"
				class="form-horizontal" style="margin-top: 20px; padding: 10px;">
				<s:hidden id="msg" value="%{message}" />
				<div class="form-group">
					<label for="name" class="col-sm-3" style="font-weight: bold;padding-top:12px;"><span
						class="glyphicon glyphicon-envelope"></span><small> Email</small>:</label>
					<div class="col-sm-9">
						<input type="text" id="name" name="userId" class="form-control"
							placeholder="Email" style="width:100%;">
					</div>

				</div>

				<div class="form-group">
					<%--<label for="password" class="col-sm-5 control-label"><span--%>
					<label for="password" class="col-sm-3" style="font-weight: bold;padding-top:12px;"><span
						class="glyphicon glyphicon-eye-open"></span><small> Password</small>:</label>
					<div class="col-sm-9">
						<input type="password" id="password" name="password"
							class="form-control" placeholder="Password">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-3"></div>
					<div class="col-sm-4 ui-content">
					<input type="hidden" name="sessionId" value="-1"/>
						<button type="submit" id="place" class="btn btn-success btn-block"
							data-ajax="false"
							style="background-color: #FFA500; color: white;">
							<span class="glyphicon glyphicon-off"></span>Sign in
						</button>
					</div>
					<div class="col-sm-4 ui-content">

						<button type="button" id="place" class="btn btn-success btn-block" data-ajax="false" style="background-color: #FFA500; color: white;" onclick="register()">Signup</a></button>
					</div>
				</div>
 				<div class="form-group">
 				<%--	<div class="col-sm-6">
						<div class="form-group pull-right" style="margin:10px 0px;">
                            				<a href="#" data-toggle="modal" id="createac">
                                				<span class="glyphicon glyphicon-user"></span>
                                				Create an Account
                            				</a>
                        			</div>


					</div>--%>
					<div class="col-sm-6"></div>
 					<div class="col-sm-6 ui-content"> 
 						<table width="100%">
 						<tr> 
 							<td align="right"> 
							<a href="#" onclick="openModel()"><small>Forgot Password?</small></a> 
 							</td> 
 						</tr>
						<tr style="height:30px">
                                                        <td align="right">
                                                        <a href='<c:url value="query.action"/>'
                                                                class="" data-ajax="false"><small>Trouble in Login?<small></a>
                                                        </td>
                                                </tr> 
					</table> 
 					</div> 
 				</div> 
			</form>

		</div>
		<div class="col-sm-2"></div>
	</div>


	<div class="modal fade" id="register_new_user" style="display: none;">
                <div class="modal-dialog">

                    <div class="modal-content">
                        <div class="modal-header" style="border-bottom:none;">
                            <button type="button" class="close" data-dismiss="modal" style="width:10px;">×</button>
                            <h4 class="modal-title text-primary" style="text-align: center;">Register for New User</h4>
                        </div>
                        <div class="modal-body">
                            <form id="newClientForm" name="newClientForm" action="registerNewUser.action" method="post" data-ajax="false">
				<s:hidden id="activationFlag" name="activationFlag" ></s:hidden>

                                <div class="form-horizontal">
                                    <div class="box-body" style="padding:0px 15px;">
                                        <div class="form-group">
                                            <label for="First Name" class="col-sm-3 control-label">Customer Code</label>
                                            <div class="col-sm-9">
                                                <input class="form-control" id="custCode" name="customerCode" placeholder="Enter..." type="text">
                                            </div>
                                        </div>
					<div class="form-group">
                                            <label for="Email ID" class="col-sm-3 control-label">Email ID</label>
                                            <div class="col-sm-9">
                                                <input class="form-control " name="emailId"  id="registerEmail" placeholder="Enter..." type="email" >
                                            </div>
                                        </div>
				</div>
				<div class="box-footer" style="border-top:none;text-align: center;">
                                	<input type="button" class="btn btn-danger btn-flat" data-dismiss="modal" style="margin-right:5px;width:100px;border-radius:0px;color: #fff;background-color:#d9534f;" value = "Cancel"  >
                                        <input type="button" value="Submit"  class="btn btn-success btn-flat" style="margin-right:5px;width:100px;border-radius:0px;color: #fff;background-color:#5cb85c;" onclick="return activationCheck()"  >
                               	</div>
				</div>
				</form>
			</div>
                    </div>

                </div>
        </div>	







	<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
		data-target="#myModal" style="display: none;" id="openModel"></button>
	<div class="modal fade" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="padding: 10px 25px;">
					<button type="button" class="close" data-dismiss="modal" style="width: 20px;">&times;</button>
					<h5>
						<span class="glyphicon glyphicon-eye-open"></span> Forgot Password
					</h5>
				</div>
				<div class="modal-body" style="padding: 40px 50px;">
					<form action='<c:url value="forgotPass.action"/>' data-ajax="false"
						method="post" onsubmit=" return validateModelParam()">
                                                <h5 id="frgtPassAlertMsg" style="color: red;"></h5>
						<div class="form-group">
							<label for="eamil"><span
								class="glyphicon glyphicon-envelope"></span> Email</label> <input
								type="text" class="form-control" id="email" name="userInfo.email"
								placeholder="Enter email">
						</div>
						<button type="submit" class="btn btn-success btn-block" style="background-color: #FFA500; color: white;">
							<span class="glyphicon glyphicon-off"></span> Submit
						</button>
					</form>
				</div>
			</div>
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
					<button type="button" class="btn btn-default" style="background-color: #FFA500; color: white; width: 80px;" data-dismiss="modal">OK</button>
				</div>
			</div>
		</div>
	</div>
       
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
