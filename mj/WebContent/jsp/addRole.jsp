<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
$( document ).ready(function() {
    $("#name").addClass("form-control");
    $("#roledesc").addClass("form-control");
    var msg=document.getElementById('msg').value;
    if(msg == null || msg ==""){  	
    }
    else{
        $("#alertMsg").html("<h5>"+msg+"</h4>");
        $("#openAlert").click();
    	alert(msg);
    	msg="";
    }
    
});
function validate()
{
	var name=document.frm_demo.name.value;
 	var roleDesc=document.frm_demo.roledesc.value;
	var checkBoxList=[];
	$('input[name="httpLinkValue"]:checked').serialize();
	$('input[name="httpLinkValue"]:checked').each(function() {
		checkBoxList.push(this.value);  
	  $(this).attr('checked', false);
	});
	document.getElementById("accessLink").value=checkBoxList.toString();
 	
    if(name=="")
    {
        $("#alertMsg").html("<h5>Please enter name</h5>");
        $("#openAlert").click();
    	return false;
    }
    
    else if(roleDesc=="")
    {
        $("#alertMsg").html("<h5>Please enter role description</h5>");
        $("#openAlert").click();
    	return false; 
    }
    else if(roleDesc.length >150){
        $("#alertMsg").html("<h5>Please enter max 150 character in description </h5>");
        $("#openAlert").click();
        return false; 
    }
    else if (checkBoxList.length==0) {
        $("#alertMsg").html("<h5>Please select access link</h5>");
        $("#openAlert").click();
 	return false;
 	}
    $("#showLoader").click();
   return true;
   }
 		function onlyNumbers(event) {
        var e = event || evt; // for trans-browser compatibility
        var charCode = e.which || e.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;
 
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
						<p class="header" align="center">Role Management Add</p>
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
			action='<c:url value="addRoleExec.action"/>' method="post"
			onsubmit="return validate()" style="margin: 15px" data-ajax="false">
            <s:hidden id="accessLink" name="accessLink"></s:hidden>
			<s:hidden id="msg" value="%{#session.msg}" />
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">Role Name:</label>
				<div class="col-sm-8">
					<s:textfield id="name" name="roleInfo.roleName" maxlength="30"></s:textfield>
				</div>
			</div>
			<div class="form-group">
				<label for="roledesc" class="col-sm-3 control-label"">Role
					Description :</label>
				<div class="col-sm-8">
			      	<s:textarea id="roledesc" name="roleInfo.roleDesc"/>
				</div>
			</div>
			<div class="form-group">
				<label for="httpLink" class="col-sm-3 control-label"">Access
					Link :</label>
				<div class="col-sm-8">
					<s:iterator value="httpLinKLst">
						<s:checkbox name="httpLinkValue" fieldValue="%{linkId}" />
						<s:property value="linkDesc" />
						<br />
					</s:iterator>
				</div>
			</div>
			<div class="form-group">
			  <div class="col-sm-3"></div>
				<div class="col-sm-4 ui-content">
						<button type="submit" id="place" class="form-control"
									style="background-color: #FFA500;color: white">Submit</button>
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
		<div class="col-sm-2"></div>
	</div>
	</div>
