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
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<style>
.ui-header-fixed, .ui-footer-fixed {
	background-color: #F8F8F8;
	border-bottom: 0px solid transparent;
}

@media screen and (max-width:500px) {
	.form-horizontal .form-group {
		
	}
}

.modal {
	position: absolute;
	padding-top: 5%;
	/* 	top: 25% !important;
 */
}

.ui-btn-icon-left::after, .ui-btn-icon-right::after, .ui-btn-icon-top::after,
	.ui-btn-icon-bottom::after, .ui-btn-icon-notext::after {
	background-color: transparent;
}

div#content {
	display: none;
}

#queries, #queries-button {
	font-weight: normal;
	font-size: 14px;
}

#loadingImg {
	position: fixed;
}

#loader {
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background: url('vastuImages/loading.gif') 50% 50% no-repeat;
}

#myModal {
	overflow-y: hidden;
}
/* .modal-body
{
	overflow-y:auto;
} */
</style>




<script>
	function preloader() {
		document.getElementById("loader").style.display = "none";
		document.getElementById("content").style.display = "block";
	}
	window.onload = preloader;

	$(window).load(function() {
		//      $("#loader").fadeOut("slow");
		$("#loader").hide();
	});

	$(document).ready(function() {
		//$('#myModal').modal({backdrop: 'static', keyboard: false});  
	      $("#description").addClass("form-control");

		$("#datepicker").datepicker();
		$("#loader").hide();
		var msg = document.getElementById('msg').value;
		if (msg == null || msg == "") {
		} else {
			//$("#alertMsg").html("<h5>" + msg + "<h5>");
			//$("#openAlert").click();
			
			
			document.getElementById("errorText").innerHTML = msg;
			msg = "";
		}
		   $('#custCode').change(function () {
	            $('#registerEmail').attr("disabled", "disabled");
	        });
	        $('#registerEmail').change(function () {
	            $('#custCode').attr("disabled", "disabled");
	        });
		$(document).ajaxStart(function() {
			$("#showLoader").click();
		});
		$(document).ajaxComplete(function() {
			$("#loader").hide();
		});

	});

	function clearFields() {
		$('#custCode').val('');
		$('#registerEmail').val('');
		document.getElementById("errorText").innerHTML = '';
        $('#custCode').removeAttr("disabled")
                    $('#registerEmail').removeAttr("disabled")


	}
	

	function validate() {
		/*	var name= form.elements.name.value;
		 var pass= form.elements.password.value;
		
		 if(name=="" || name==null)
		 {
		 $("#alertMsg").html("<h5>Please enter user name<h5>");
		 $("#openAlert").click();
		 return false;
		 }
		
		 if(pass=="" || pass==null)
		 {
		 $("#alertMsg").html("<h5>Please enter password<h5>");
		 $("#openAlert").click();
		 return false;
		 }*/

		$("#showLoader").click();
		return true;
	}

	function validateNewUser() {
		//alert("inside validateNewUser")
		var name = $("#name").val();
		var date = $("#datepicker").val();

		name = name.trim();

		var alpha = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
		if (date == "" || date == null) {
		//	$("#alertMsg").html("<h5>Please enter 4-Day Course Date</h5>");
			//$("#openAlert").click();
			alert("Please Enter Date");
			return false;
		}

		if (name == "" || name == null) {
			/* $("#alertMsg").html("<h5>Please enter name</h5>");
			$("#openAlert").click(); */
			alert("Please Enter Name");

			return false;
		}

		var email = document.getElementById('email').value;

		if (email == "" || email == null) {
		//$("#frgtPassAlertMsg").html("<h5>Please enter user email<h5>");
		alert("Please Enter User Email")
			return false;
		} else {
			var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			if (!filter.test(email)) {
				/* $("#frgtPassAlertMsg").html(
						"<h5>Please provide a valid email address<h5>"); */
						alert("Please provide a valid email address");
				
				return false;
			}
		}

		return true;

	}

	function saveUserDetails() {
		//alert("inside saveUserDetails method");
		var name = $("#name").val();
		var date = $("#datepicker").val();
		var email = $("#email").val();
		var mobile = $("#mobile").val();
		var mvCode = $("#mvCode").val();
		var description = $("#description").val();
		
		var queries = $("#queries").val();

		
//alert(name+" "+date+" "+email+" "+mobile+" "+mvCode+" "+description);
		if (validateNewUser()) {
			$('#myModal .close').click();

                        var url = "?name="+name+"&date="+date+"&email="+email+"&mobile="+mobile+"&mvCode="+mvCode+"&description="+description+"&queries="+queries;
                        url = "<%=response.encodeURL("customerQueries")%>" + url;

			$
					.ajax({
						type : "POST",
						url : url,
						beforeSend : function() {
							$("#showLoader").click();
						},
						complete : function() {
							$("#loader").hide();
						},
						success : function() {
							alert("Your Query has been successfully Registered. Mahavastu will reach you for further update.");
							$('.modal-backdrop').hide();
						}
					});

			$("#queryForm").submit();
		}
	}
	function openModel() {
		//alert("Here");
		$('#loaderDiv').hide();
		$('.modal-backdrop').hide();
		$("#openModel").click();
	}

	/*	$(function () {
	 $('#openModel').modal('toggle');
	 });*/
	function activationCheck() {
		var email = "";
		var contactCode = "";
		var jsonObj;
		var authResponse = "";
		var jsonCode = 0;
		var respJson;
        var data;
		email = document.getElementById("registerEmail").value;
		contactCode = document.getElementById("custCode").value;
		if (email == "" && contactCode == "") {
			$("#alertMsg").html(
					"<h5>Please Enter MVC Code or Registered Email<h5>");
			$("#openAlert").click();
			return false;
		}

		var url = "ZohoAuthentication?email=" + email + "&contactCode="
				+ contactCode;
		var firstName = "";
		var lastName = "";
		var clientName = "";
		var custCode = "";
		var email = ""
		$
				.ajax({
					type : 'POST',
					url : url,
					success : function(result) {
					if(result!=""){	
						jsonObj = JSON.parse(result);
						//console.log("jsonObj==>"+jsonObj);
						respJson = JSON.stringify(jsonObj);

						if (respJson!=null) {

							data=jsonObj.data[0].Access;
							console.log(data);
							console.log(data.length);
							if(data.length >0 ){
								for(var i=0;i<data.length;i++){
									console.log(data[i]);
									if(data[i]=='MAHAJYOTISH'){
										console.log('---- i['+i+'] '+authResponse);
										authResponse=data[i];
										
										role_id=12;
									}
								}
								
							}
							
							console.log("authResponse "+authResponse);
							
							document.getElementById("role_id").value = role_id;
							console.log("authResponse ["+authResponse+"]");
							clientName=jsonObj.data[0].Full_Name;
							
			               $('#registerEmail').removeAttr("disabled");
			               $('#custCode').removeAttr("disabled");
						   $("#registerEmail").val(jsonObj.data[0].Email);
						   $("#custCode").val(jsonObj.data[0].Contact_Code);
						   $("#clientName").val(clientName);
						} else {
							jsonCode = null;
						}

						if (jsonCode == null) {
							document.getElementById("activationFlag").value = 100;

						} else if (authResponse == "MAHAJYOTISH") {
							console.log("Here role found of ovfc")
							document.getElementById("activationFlag").value = 102;
							document.getElementById("access").value = authResponse;
							document.getElementById("role_id").value = 12;
							document.newClientForm.submit();

						} else {
							document.getElementById("activationFlag").value = 101;

						}
						var actVal = document.getElementById("activationFlag").value;
						if (actVal == 101) {
							window.location.href = "register.action?message=You don't have access to MahaJyotish Account . Please Leave your Query by Click on Trouble in Login. ";

						}
                        else if (actVal == 100) {
								window.location.href = "register.action?message=No information Found. Please try with another Email Id 0r Leave your query by click on Trouble in Login. ";
                        }
					}
					else
						{
						window.location.href = "register.action?message=No information Found. Please try with another Email Id or Leave your query by click on Trouble in Login. ";
						}
					}

				});

	}
</script>
</head>
<body>
	<s:hidden name="message" id="msg"></s:hidden>

	<div id="loader"></div>
	<div id="content">

		<div class="row">

			<div data-role="header" data-theme="b" data-position="overlay">
				<div class="row" id="headerStrip"></div>

				<div
					class="col-lg-1 col-lg-offset-5 col-md-1 col-md-offset-5 col-sm-1 col-sm-offset-5 col-xs-3 col-xs-offset-5">
					<img id="headerImg" src="vastuImages/mjk-logo.png">
					<p id="orangeHeader">MahaJyotish Kundli</p>
				</div>
			</div>
		</div>

		<div class="row" style="margin-top: 50px;">
			<div class="col-sm-3 hidden-xs"></div>
			<div class="col-sm-5  col-xs-12">
				<div class="col-sm-12" style="margin-top: 0px; margin-bottom: 10px">
					<div class="container-fluid"
						style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
						<div class="row-fluid">
							<div class="span8">
								<p class="header" align="center">Enter Your Details for
									Registration</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4 hidden-xs"></div>
		</div>



		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6" id="formDiv">

				<form action='registerNewUser.action' data-ajax="false"
					method="post" class="form-horizontal"
					style="margin-top: 20px; padding: 10px;" id="newClientForm"
					name="newClientForm" onsubmit=" return validate()">
					<s:hidden id="activationFlag" name="activationFlag"></s:hidden>
					<s:hidden id="clientName" name="clientName"></s:hidden>
					<s:hidden id="access" name="access"></s:hidden>
					<s:hidden id="role_id" name="role_id"></s:hidden>
					<s:hidden id="msg" value="%{message}" />
					<div class="form-group">
						<p style="padding-left: 12%; color: #A0A0A0;">MahaJyotish
							access is restricted to MahaJyotish Experts. If you have done
							MahaJyotish Course</p>
						<p style="padding-left: 20%; color: #A0A0A0;">or enrolled for
							upcoming course then please provide the following details.</p>
					</div>


					<div class="form-group">
						<label for="name" class="col-sm-3 control-label"><small>
								MVC Code :</small></label>
						<div class="col-sm-7">
							<input type="text" id="custCode" name="customerCode"
								class="form-control" placeholder="CC/XXXX">
						</div>

					</div>


					<div class="form-group"
						style="margin-top: 0px; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px;">
						<label for="name" class="col-sm-12 "><center>
								<small> OR</small>
							</center></label>
					</div>




					<div class="form-group">
						<label for="password" class="col-sm-3 control-label"><small>
								Registered Email :</small></label>
						<div class="col-sm-7">
							<input type="email" id="registerEmail" name="emailId"
								class="form-control" placeholder="xyz@abc.com">
						</div>
					</div>
					<div class="form-group">

						<center>
							<span id="errorText" style="color: red; font-weight: bold;">
							</span>
						</center>

					</div>

					<div class="form-group">
						<div class="col-sm-3"></div>
						<div class="col-sm-4 ui-content">
							<button type="button" id="place"
								class="btn btn-success btn-block"
								style="background-color: #FFA500; color: white;"
								onclick="return activationCheck()">Submit</button>
						</div>

						<div class="col-sm-4 ui-content">
							<button type="button" id="clear"
								class="btn btn-success btn-block"
								style="background-color: #FFA500; color: white;"
								onclick="clearFields()">Clear</button>
						</div>

						<!-- 						<div class="col-sm-4"></div>
 -->
						<div class="col-sm-8"></div>

						<div class="col-sm-4 ui-content">
							<p align="right">
								<a id="homeLink" data-ajax="false"
									href='<c:url value="query.action"/>'>Trouble In Login ?</a>
							</p>
							<p align="right">
								<a id="homeLink" data-ajax="false"
									href='<c:url value="home.action"/>'>Home</a>
							</p>
						</div>

					</div>
				</form>

			</div>
			<div class="col-sm-2"></div>
		</div>


		<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
			data-target="#alertModel" style="display: none;" id="openAlert"></button>
		<div class="modal fade" id="alertModel">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header" style="padding: 10px 25px;">
						<h4 id="alertMsg"></h4>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							style="background-color: #FFA500; color: white; width: 80px;"
							data-dismiss="modal">OK</button>
					</div>
				</div>
			</div>
		</div>

		<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
			data-target="#loaderDiv" style="display: none;" id="showLoader"
			data-backdrop="static" data-keyboard="false"></button>
		<div class="modal fade" id="loaderDiv">
			<div class="modal-dialog">
				<div class="modal-content"
					style="background-color: transparent; -moz-box-shadow: 0px 0px 0px 0px #ccc; -webkit-box-shadow: 0px 0px 0px 0px #ccc; box-shadow: 0px 0px 0px 0px #ccc; border: 0px">
					<div id="loader"
						style="display: block; width: 100%; height: 200px;"></div>
				</div>
			</div>
		</div>

		<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
			data-target="#myModal" style="display: none;" id="openModel"
			data-backdrop="static" data-keyboard="false"></button>
		<div class="modal fade center-div" id="myModal">
			<div class="modal-dialog">
				<div class="modal-content center-div" style="height: 95%">
					<div class="modal-header" style="padding: 5px 25px;">
						<button type="button" class="close" data-dismiss="modal"
							style="width: 20px;">&times;</button>
						<h5>
							<span class="glyphicon glyphicon-eye-open"></span>Leave your
							query Here
						</h5>
					</div>
					<div class="modal-body"
						style="padding: 20px 40px; overflow-y: auto;">
						<form action='<c:url value="register.action"/>' data-ajax="false"
							method="post" id="queryForm">
							<div class="form-group">
								<label for="eamil">Name</label> <input type="text"
									class="form-control" id="name" name="userInfo.name"
									placeholder="Enter Name" required="required">
							</div>
							<div class="form-group">
								<label for="eamil"> Course Detail</label> <input type="text"
									class="form-control" id="datepicker"
									name="userInfo.courseDetail" placeholder="Enter Date" required>
							</div>
							<div class="form-group">
								<h5 id="frgtPassAlertMsg" style="color: red;"></h5>

								<label for="eamil"> Email</label> <input type="text"
									class="form-control" id="email" name="userInfo.email"
									placeholder="Enter email">
							</div>
							<div class="form-group">
								<label for="eamil"> Moblie</label> <input type="text"
									class="form-control" id="mobile" name="userInfo.mobile"
									placeholder="Enter Mobile No.">
							</div>
							<div class="form-group">
								<label for="eamil"> MV Code</label> <input type="text"
									class="form-control" id="mvCode" name="userInfo.mvCode"
									placeholder="Enter MVCode">
							</div>
							<div class="form-group">
								<label for="eamil"> Description</label>
								<textarea id="description" rows="4" cols="50"
									placeholder="Description"></textarea>
							</div>
							<div class="form-group">
								<label for="eamil"> Queries</label> <select id="queries">
									<option value="None">-- None --</option>
									<option value="Login Issue">Login Issue</option>
									<option value="Forgot My Registered Email Id">Forgot
										My Registered Email Id</option>
									<option value="Forgot My MVCode">Forgot My MVCode</option>
									<option value="How To Use">How To Use</option>
									<option value="Others">Others</option>

								</select>
							</div>
							<button type="button" id="saveInfo"
								class="btn btn-success btn-block"
								style="background-color: #FFA500; color: white;"
								onclick="saveUserDetails()">
								<span class="glyphicon glyphicon-off"></span> Submit
							</button>


						</form>
					</div>
				</div>
			</div>
		</div>

	</div>


</body>
</html>
