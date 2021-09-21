<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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


<style type="text/css">
#loader {
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background: url('vastuImages/loading.gif') 50% 50% no-repeat;
}
</style>

<script>
$(document).ready(function() {
	//$('#myModal').modal({backdrop: 'static', keyboard: false});  
      $("#description").addClass("form-control");
     $("#queries").addClass("form-control");

	$("#datepicker").datepicker();
	$("#loader").hide();
});
	 $(document).ajaxStart(function() {
		$("#showLoader").click();
	});
	$(document).ajaxComplete(function() {
		$("#queryForm").submit();
		$("#loader").hide();
	}); 
//});
	
	function validateNewUser() {
		//alert("inside validateNewUser")
		var name = $("#name").val();
		var date = $("#datepicker").val();

		name = name.trim();

		var alpha = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;

		if (name == "" || name == null) {
			/* $("#alertMsg").html("<h5>Please enter name</h5>");
			$("#openAlert").click(); */
			alert("Please Enter Name");

			return false;
		}
		if (date == "" || date == null) {
                        alert("Please Enter Date");
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
	
	function clearFields()
	{
		$("#name").val('');
		$("#datepicker").val('');
		$("#email").val('');
		$("#mobile").val('');
		$("#mvCode").val('');
		$("#description").val('');
		$("#queries").val('None');
	}
	function saveUserDetails() {
		var name = $("#name").val();
		var date = $("#datepicker").val();
		var email = $("#email").val();
		
		var mobile = $("#mobile").val();
		var mvCode = $("#mvCode").val();
		var description = $("#description").val();
		
		var queries = $("#queries").val();

		
		if (validateNewUser()) {

                        var url = "?name="+name+"&date="+date+"&email="+email+"&mobile="+mobile+"&mvCode="+mvCode+"&description="+description+"&queries="+queries;
                        url = "<%=response.encodeURL("customerQueries")%>"+url;

            	 		$.ajax({
             	 			type: "POST",
             	 			url: url,
			/* 	beforeSend: function(){
						$("#showLoader").click();
					},	
					complete: function(){
						$("#loader").hide();
					},  */
             	 			success: function(result){
             	 				
									alert("Your Query has been successfully Registered. Mahavastu will reach you for further update.");

             	 			}
             	 			});

		}
	}

</script>
</head>
<body>
<div id="loader"></div>
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

	<div class="row" style="margin-top: 30px;">
		<div class="col-sm-3 hidden-xs"></div>
		<div class="col-sm-5  col-xs-12">
			<div class="col-sm-12" style="margin-top: 0px; margin-bottom: 10px">
				<div class="container-fluid"
					style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
					<div class="row-fluid">
						<div class="span8">
							<p class="header" align="center">Enter Your Details</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-4 hidden-xs"></div>
	</div>
	<div class="row col-sm-12">
		<div class="col-sm-2"></div>
		<div class="col-sm-8" id="formDiv">
			<form class="form-horizontal" id="queryForm" name="frm_demo"
				action="home.action" data-ajax="false" method="post"
				style="margin: 15px">
				<%-- <s:hidden id="lat" name="generateKundli.latitude"></s:hidden>
			<s:hidden id="long" name="generateKundli.longitude"></s:hidden>
			<s:hidden id="cityCode" name="generateKundli.cityCode"></s:hidden>
			<s:hidden id="isSave" name="generateKundli.isSave"></s:hidden>
			<s:hidden id="reqId" name="generateKundli.requestId"></s:hidden>
			<s:hidden id="userId" value="%{#session.userName}"></s:hidden>
			<s:hidden id="transKundli" name="generateKundli.isTransitKundli"></s:hidden>
			<s:hidden name="genType" value="D"></s:hidden> --%>

				<div class="form-group">
					<label for="name" class="col-sm-3 control-label">Name :</label>
					<div class="col-sm-8">
						<table width="100%" height="100%">
							<tr>
								<td width="90%"><input type="text" class="form-control"
									id="name" name="userInfo.name" placeholder="Enter Name"
									required="required"></td>

							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label">Course
						Detail :</label>
					<div class="col-sm-8">
						<table width="100%" height="100%">
							<tr>
								<td width="90%"><input type="text" class="form-control"
									id="datepicker" name="userInfo.courseDetail"
									placeholder="Enter Date" required></td>

							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label">Email:</label>
					<div class="col-sm-8">
						<table width="100%" height="100%">
							<tr>
								<td width="90%"><input type="text" class="form-control"
									id="email" name="userInfo.email" placeholder="Enter email"></td>

							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label">Mobile No:</label>
					<div class="col-sm-8">
						<table width="100%" height="100%">
							<tr>
								<td width="90%"><input type="text" class="form-control"
									id="mobile" name="userInfo.mobile"
									placeholder="Enter Mobile No."></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label">MV Code</label>
					<div class="col-sm-8">
						<table width="100%" height="100%">
							<tr>
								<td width="90%"><input type="text" class="form-control"
									id="mvCode" name="userInfo.mvCode" placeholder="Enter MVCode"></td>

							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label">Description</label>
					<div class="col-sm-8">
						<table width="100%" height="100%">
							<tr>
								<td width="90%"><textarea id="description" rows="4"
										cols="50" placeholder="Description"></textarea></td>

							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label">Query</label>
					<div class="col-sm-8">
						<table width="100%" height="100%">
							<tr>
								<td width="90%"><select id="queries">
										<option value="None"> -- None -- </option>
										<option value="Login Issue">Login Issue</option>
										<option value="Forgot My Registered Email Id">Forgot My Registered Email Id</option>
										<option value="Forgot My MVCode">Forgot My MVCode</option>
										<option value="How To Use">How To Use</option>
										<option value="Others">Others</option>

								</select></td>

							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-3"></div>
					<div class="col-sm-4 ui-content">
						<button type="button" id="saveInfo" class="form-control"
							onclick="saveUserDetails()"
							style="background-color: #FFA500; color: white">Submit</button>
					</div>

					<div class="col-sm-4"></div>
					<div class="col-sm-4 ui-content">
						<button type="button" id="clear" class="form-control"
							onclick="clearFields()"
							style="background-color: #FFA500; color: white">Clear</button>
					</div>
				</div>
			</form>

			<div class="form-group">
				<div class="col-sm-8"></div>
				<div class="col-sm-4 ui-content">
					<p align="right">
						<a id="homeLink" data-ajax="false"
							href='<c:url value="home.action"/>'>Home</a>
					</p>
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
</body>
</html>
