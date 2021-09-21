<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
$( document ).ready(function() {
    $("#name").addClass("form-control");
    $("#password").addClass("form-control");
    $("#email").addClass("form-control");
    $("#mobileNo").addClass("form-control");
    $("#role").addClass("form-control");
    var msg=document.getElementById('msg').value;
    if(msg == null || msg ==""){  	
    }
    else{
        $("#alertMsg").html("<h5>"+msg+"<h5>");
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
 	var name=document.frm_demo.name.value;
        name = name.trim();
         var alpha = /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$/;
 	var password = document.frm_demo.password.value;
 	var email=document.getElementById('email').value;
 	var mobNo=document.getElementById('mobileNo').value;
 	var role = document.getElementById('role').value;
	var prefix = document.getElementById('prefix').value;

       	if(prefix=="" || prefix==null){
                $('#prefix').val("MJK");
        //	document.getElementById('prefix').value="MJK";

        }
    if(name=="")
    {
        $("#alertMsg").html("<h5>Please enter name<h5>");
        $("#openAlert").click();
    	return false;
    }

    else if(!name.match(alpha)) {
        $("#alertMsg").html("<h5>Please enter valid name</h5>");
        $("#openAlert").click();
        return false;
    }

    
    else if(password==""){
        $("#alertMsg").html("<h5>Please enter password<h5>");
        $("#openAlert").click();
        return false;
    }
    
    else if(email=="" || email==null)
    {
        $("#alertMsg").html("<h5>Please enter email ID<h5>");
        $("#openAlert").click();
    	return false;
    }
    
 
    else if(mobNo=="" )
    {
        $("#alertMsg").html("<h5>Please enter Mobile Number<h5>");
        $("#openAlert").click();
    	return false; 
    }
    
    else if (role == -1) {
        $("#alertMsg").html("<h5>Please select role<h5>");
        $("#openAlert").click();
    	return false; 
	}
    
	else if(mobNo.length!=10)
	{
                $("#alertMsg").html("<h5>Please enter mobile number of length 10<h5>");
                $("#openAlert").click();
		return false;
	}
    
   else
   {
      	var filter =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    	if (!filter.test(email)) 
	    {	
            $("#alertMsg").html("<h5>Please provide a valid email address<h5>");
            $("#openAlert").click();
	    return false;
   		}	
	}
   $("#showLoader").click();
   return true;
   }

$(function() {
                $("#mobileNo").keypress(function(e){
                        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                                return false;
                        }
                });
        });
   
function show_details() {
dojo.event.topic.publish("show_detail");
}



function show_detailscity() {

dojo.event.topic.publish("show_detailcity");
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
						<p class="header" align="center">User Management Modify</p>
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
			action='<c:url value="modifyUserExec.action"/>' method="post"
			onsubmit="return validate()" style="margin: 15px" data-ajax="false">

			<s:hidden id="msg" value="%{#session.msg}" />
			<s:hidden id="userId" name="userInfo.uId"></s:hidden>
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">Name:</label>
				<div class="col-sm-8">
					<s:textfield id="name" name="userInfo.uname" maxlength="40"></s:textfield>
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-3 control-label">Password
					:</label>
				<div class="col-sm-8">
					<s:textfield id="password" name="userInfo.upassword" maxlength="40" onkeypress="return RestrictSpace()"></s:textfield>
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-3 control-label">Email :</label>
				<div class="col-sm-8">
					<s:textfield id="email" name="userInfo.email" maxlength="50" readonly="true"></s:textfield>
				</div>
			</div>

			<div class="form-group">
				<label for="mobileNo" class="col-sm-3 control-label">Mobile
					Number :</label>
				<div class="col-sm-8">
					<%--<s:textfield id="mobileNo" name="userInfo.mobileNo" onkeydown="return onlyNumbers(event)"></s:textfield>--%>
					<s:textfield id="mobileNo" name="userInfo.mobileNo"></s:textfield>
				</div>
			</div>

			<div class="form-group">
                                <label for="prefix" class="col-sm-3 control-label">Prefix For Kundli :</label>
                                <div class="col-sm-8">
                                        <input type="text" class="form-control" id="prefix"
                                                name="userInfo.prefix" />
                                </div>
                        </div>
			

			<div class="form-group">
				<label for="role" class="col-sm-3 control-label">Role :</label>
				<div class="col-sm-8">
					<s:select id="role" headerKey='-1' headerValue='select'
						list="rolelist" listKey="roleId" listValue="roleName"
						name="userInfo.roleId" value="userInfo.roleId" />
				</div>
			</div>
			<div class="form-group">
				<label for="role2" class="col-sm-3 control-label">Role2 :</label>
				<div class="col-sm-8">
					<s:select id="role2" headerKey='-1' headerValue='select' list="rolelist" listKey="roleId" listValue="roleName"
						name="userInfo.roleId2" value="userInfo.roleId2" />
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
