<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
$( document ).ready(function() {
    var msg=document.getElementById('msg').value;
    if(msg == null || msg ==""){  	
    }
    else{
        $("#alertMsg").html("<h5>"+msg+"</h5>");
        $("#openAlert").click();
    	msg="";
    }   
});
function confirmDialog() {
    var r = confirm("Are you sure want to delete?");
    if (r == true) {
       return true;
    } else {
        return false;
    }
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
						<p class="header" align="center">Role Management View</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>

<div class="row col-sm-offset-1 col-sm-10">
	<form class="form-horizontal" id="frm_demo" name="frm_demo"
		action='<c:url value="viewRole.action"/>' data-ajax="false" method="post">
		<s:hidden id="msg" value="%{#session.msg}" name="msg" />
		<div class="table-responsive">
			<table id="dataTable" class="ui-table ui-responsive" width="100%"
				height="100%" align="center">
				<tr>
					<th width="20%">ROLE NAME</th>
					<th width="35%">ROLE DESC</th>
					<th width="15%">VIEW</th>
					<s:if test='#session.containsKey("9")'>
						<th width="15%">MODIFY</th>
					</s:if>
					<s:if test='#session.containsKey("10")'>
						<th width="15%">DELETE</th>
					</s:if>
				</tr>
				<s:iterator value="rolelist">
					<tr>
						<td><s:property value="roleName" /></td>
						<td><s:property value="roleDesc" /></td>
						<td>
<%-- 						<s:url action="viewAccessLink.action" id="viewAccessLink" encode="true" includeParams="none"> --%>
<%--  						<s:param name="roleInfo.roleId"><s:property value="roleId"/></s:param> --%>
<%--      				    </s:url>  --%>
					    <a href='<c:url value="viewAccessLink.action?roleInfo.roleId=${roleId}"/>'
 							class="tableDataLink" data-ajax="false">View</a> 
							<%-- <a href='viewAccessLink.action?roleInfo.roleId=<s:property value="roleId"/>' 
 							class="tableDataLink" data-ajax="false">view</a>  --%>
							
							</td>
						<s:if test='#session.containsKey("9")'>
							<td>
<%-- 							<s:url action="modifyRole.action" id="modifyRole" encode="true" includeParams="none"> --%>
<%-- 							<s:param name="roleInfo.roleId"><s:property value="roleId"/></s:param> --%>
<%--     				    	</s:url> --%>
							<a href='<c:url value="modifyRole.action?roleInfo.roleId=${roleId}"/>'
								class="tableDataLink" data-ajax="false">Modify</a></td>
						</s:if>
						<s:if test='#session.containsKey("10")'>
							<td>
<%-- 							<s:url action="deleteRole.action" id="deleteRole" encode="true" includeParams="none"> --%>
<%-- 							<s:param name="roleInfo.roleId"><s:property value="roleId"/></s:param> --%>
<%--     				    	</s:url> --%>
							<a href='<c:url value="deleteRole.action?roleInfo.roleId=${roleId}"/>'
								class="tableDataLink" data-ajax="false" onclick="return confirmDialog()">Delete</a></td>
						</s:if>
					</tr>
				</s:iterator>
			</table>
		</div>
	</form>
</div>
<div class="form-group">
	<div class="row col-sm-offset-1 col-sm-10" id="backDiv">
		<a href="javascript: history.go(-1)"><img id="backImage"
			src="vastuImages/back.png" /> </a>
	</div>
</div>


