<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script>
$( document ).ready(function() {
    $("#password").addClass("form-control");
    $("#confirmPass").addClass("form-control");
    var msg=document.getElementById('msg').value;
    if(msg == null || msg ==""){  	
    }
    else{
        $("#alertMsg").html("<h5>"+msg+"</h5>");
        $("#openAlert").click();
    	msg="";
    }   
});

function RestrictSpace() {
    if (event.keyCode == 32) {
        return false;
    }
}


function validate()
{
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPass").value;
    if(password=="" || password == null)
    {
      $("#alertMsg").html("<h5>Please enter password</h5>");
      $("#openAlert").click();
      return false;
    }
    else if(confirmPassword=="" || confirmPassword==null)
    {
      $("#alertMsg").html("<h5>Please enter confirm password</h5>");
      $("#openAlert").click();
      return false;
    }
	else{
		if(confirmPassword != password){
			$("#alertMsg").html("<h5>your password & confirm password is not match</h5>");
                        $("#openAlert").click();
			return false;
		}
	}
 //  $("#showLoader").click();
   return true;
}

</script>
<div class="row">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<div class="col-sm-12" id="innerHeadingDiv">
			<div class="container-fluid"
				style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
				<div class="row-fluid">
					<div class="span8">
						<p class="header" align="center">Change Password</p>
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
			action='<c:url value="changePassExec.action"/>' method="post"
			data-ajax="false" onsubmit="return validate()" style="margin: 15px">

			<s:hidden id="roleId" name="roleInfo.roleId"></s:hidden>
			<s:hidden id="msg" name="message" />
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label">New
					Password:</label>
				<div class="col-sm-7">
					<s:password id="password" name="password" maxlength="40" onkeypress="return RestrictSpace()"></s:password>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label">Confirm
					Password:</label>
				<div class="col-sm-7">
					<s:password id="confirmPass" name="confirmPass" maxlength="40" onkeypress="return RestrictSpace()"></s:password>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-3"></div>
				<div class="col-sm-4 ui-content">
					<button type="submit" id="place" class="form-control"
						style="background-color: #FFA500; color: white">Submit</button>
				</div>
				<div class="col-sm-4 ui-content">
					<button type="reset" value="Reset" class="form-control"
						style="background-color: #FFA500; color: white">Reset</button>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12" id="backDiv">
					<a href="javascript: history.go(-1)"><img id="backImage"
						src="vastuImages/back.png" /> </a>
				</div>
			</div>
		</form>
	</div>
	<div class="col-sm-2"></div>
</div>




