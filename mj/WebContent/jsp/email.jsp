<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
 $( document ).ready(function() {
    $("#email").addClass("form-control");
    var msg=document.getElementById('msg').value;
    if(msg == null || msg ==""){  	
    }
    else{
        $("#alertMsg").html("<h5>"+msg+"</h5>");
        $("#openAlert").click();
    	msg="";
    }   
}); 
function validate()
{
     var email = document.getElementById("email").value;
    if(email=="" || email == null)
    {
      $("#alertMsg").html("<h5>Please enter email</h5>");
      $("#openAlert").click();
      return false;
    }
	else{
      	var filter =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    	if (!filter.test(email)) 
	    {	
            $("#alertMsg").html("<h5>Please provide a valid email address</h5>");
	    $("#openAlert").click();
	    return false;
   		}			
	}
	
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
						<p class="header" align="center">Email Pdf</p>
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
			action='<c:url value="GenerateKundliFromDetails.action?genType=E"/>'
			method="post" data-ajax="false" onsubmit="return validate()"
			style="margin: 15px">
			<s:hidden id="msg" name="message" />
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">Email:</label>
				<div class="col-sm-8">
					<s:textfield id="email" name="userInfo.email"></s:textfield>
				</div>
			</div>
                        <s:if test='#session.containsKey("1")'>
                        <div class="form-group">
				<label for="name" class="col-sm-3 control-label">Dasha Detail:</label>
				<div class="col-sm-8">
					<s:checkbox id="dashaDetail" name="userInfo.dashaDetail" style="margin-top: 12px;"></s:checkbox>
				</div>
			</div>
                        </s:if> 
                        <s:if test='#session.containsKey("2")'>
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">House Detail:</label>
				<div class="col-sm-8">
					<s:checkbox id="houseDetail" name="userInfo.houseDetail" style="margin-top: 12px;"></s:checkbox>
				</div>
			</div>
                        </s:if>
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




