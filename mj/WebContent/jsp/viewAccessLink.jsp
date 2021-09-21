<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
$( document ).ready(function() {
    $("s:property").addClass("form-control");
});
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
						<p class="header" align="center">View Access Link</p>
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
		<form class="form-horizontal" id="frm_demo" name="frm_demo" action='<c:url value="accessLinkExec.action"/>'
			method="post" style="margin: 15px">
			<s:hidden id="msg" value="%{#session.msg}" />

			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">Role Name:</label>
				<div class="col-sm-8" style="margin-top: 9px;">
					<s:property value="roleInfo.roleName" />
				</div>
			</div>
			<div class="form-group">
				<label for="roledesc" class="col-sm-3 control-label">Role
					Description :</label>
				<div class="col-sm-8" style="margin-top: 9px;">
					<s:property value="roleInfo.roleDesc" />
				</div>
			</div>
			<div class="form-group">
				<label for="httpLink" class="col-sm-3 control-label">Access
					Link :</label>
				<div class="col-sm-8" style="margin-top: 9px;">
					<s:iterator value="httpLinKLst">
						<s:if test="%{roleInfo.accessIdLst.contains(linkId)}">
							<img src="vastuImages/yes.png" height="20" width="20" />
						</s:if>
						<s:else>
							<img src="vastuImages/no.png" height="20" width="20" />
						</s:else>
						<s:property value="linkDesc" />
						<br />
					</s:iterator>
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
