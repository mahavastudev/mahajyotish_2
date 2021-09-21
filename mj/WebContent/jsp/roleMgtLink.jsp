<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="row">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<div class="col-sm-12" id="innerHeadingDiv">
			<div class="container-fluid"
				style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
				<div class="row-fluid">
					<div class="span8">
						<p class="header" align="center">Role Management</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>

<div class="row col-sm-12">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<s:if test='#session.containsKey("8")'>
			<div class="form-group" id="addRole">
				<div class="col-sm-8">
<%-- 				    <s:url action="addRole.action" id="addRole" encode="true" includeParams="none"> --%>
<%--     				</s:url> --%>
					<a href='<c:url value="addRole.action"/>' data-ajax="false">Role Management- Add</a>
				</div>
			</div>
		</s:if>
		<s:if test='#session.containsKey("7")'>
			<div class="form-group" id="viewRole">
				<div class="col-sm-8">
<%-- 				    <s:url action="viewRole.action" id="viewRole" encode="true" includeParams="none"> --%>
<%--     				</s:url> --%>
					<a href='<c:url value="viewRole.action"/>' data-ajax="false">View/Modify Role Management</a>
				</div>
			</div>
		</s:if>
	</div>
	<div class="col-sm-2"></div>
</div>
