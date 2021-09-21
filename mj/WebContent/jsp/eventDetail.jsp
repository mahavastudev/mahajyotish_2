<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	$(document).ready(function() {
		$('#searchTerm').keyup(function() {
			searchTable($(this).val());
		});
	});
	function confirmDialog() {
	    var r = confirm("Are you sure want to delete?");
	    if (r == true) {
	       return true;
	    } else {
	        return false;
	    }
	}
	function searchTable(inputVal) {
		var table = $('#dataTable');
		table.find('tr').each(function(index, row) {
			var allCells = $(row).find('td');
			if (allCells.length > 0) {
				var found = false;
				allCells.each(function(index, td) {
					var regExp = new RegExp(inputVal, 'i');
					if (regExp.test($(td).text())) {
						found = true;
						return false;
					}
				});
				if (found == true)
					$(row).show();
				else
					$(row).hide();
			}
		});
	}
</script>

<div class="row">
	<div class="col-sm-2 hidden-xs"></div>
	<div class="col-sm-8  col-xs-12">
		<div class="col-sm-12" id="innerHeadingDiv">
			<div class="container-fluid"
				style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
				<div class="row-fluid">
					<div class="span8">
						<p class="header" align="center">Event List</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>

<div class="row col-sm-offset-1 col-sm-10">
	
		<form class="form-horizontal" id="frm_demo" name="frm_demo"
			data-ajax="false">
			<s:if test="eventLogsLst.size()==0">
				<p style="width: 100%;text-align: center;">No Data Found</p>
			</s:if>
		<s:else>
				<div class="form-group">
				<div class="col-sm-offset-8 col-sm-3  col-xs-12">
					<input type="text" id="searchTerm" placeholder="search" class="form-control"
						onkeyup="doSearch()" />
						</div>
					</div>
			<div class="table-responsive">
				<table id="dataTable" class="ui-table ui-responsive" width="100%" height="100%" align="center">
					<tr>
						<th style="width:9%;">Date</th>
						<th style="width:9%;">Time</th>
						<th style="width:9%;">Activity</th>
						<th style="width:9%;">Lagna R</th>
						<th style="width:9%;">Lagna Lord</th>
                                                <th style="width:9%;">Lagna NL</th>
                                                <th style="width:9%;">Sun RL/NL/SL</th>
                                                <th style="width:9%;">Moon RL/NL/SL</th>
                                                <th style="width:9%;">Day Lord</th>
						<th style="width:9%;">VIEW</th>
                                               <th style="width:10%;">DELETE</th>
					</tr>
				<s:iterator value="eventLogsLst">
						<tr>
							<td><s:property value="eventDate" /></td>
							<td><s:property value="eventTime" /></td>
							<td><s:property value="eventName"/></td>
							<td><s:property value="sign"/></td>
							<td><s:property value="lord"/></td>
							<td><s:property value="NL"/></td>
							<td><s:property value="sun"/></td>
							<td><s:property value="moon"/></td>
							<td><s:property value="dayLord"/></td>
							<td><a href='<c:url value="viewEventLog.action?event.eventId=${eventId}"/>'
								class="tableDataLink" data-ajax="false">View</a>
							</td>
							<td>
							<a href='<c:url value="deleteOldEvent.action?event.eventId=${eventId}"/>'
								class="tableDataLink" data-ajax="false" onclick="return confirmDialog()">Delete</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			</s:else>		
		</form>	
</div>		
<div class="form-group">
	<div class="row col-sm-offset-1 col-sm-10" id="backDiv">
           <a href="javascript: history.go(-1)"><img id="backImage" src="vastuImages/back.png" /> </a>
	</div>
</div>	

