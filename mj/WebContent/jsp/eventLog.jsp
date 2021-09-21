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
<%--			<s:if test="kundliLogsLst.size()==0">
				<p style="width: 100%;text-align: center;">No Data Found</p>
			</s:if>
		<s:else>
				<div class="form-group">
				<div class="col-sm-offset-8 col-sm-3  col-xs-12">
					<input type="text" id="searchTerm" placeholder="search" class="form-control"
						onkeyup="doSearch()" />
						</div>--%>
		        <div class="col-sm-6">
	
			<div class="table-responsive">
				<table id="dataTable" class="ui-table ui-responsive" width="100%" height="100%" align="left">
					 <tr>
                                                <th style="background-color:#ff8000;width:30%">Event Details</th>
                                                <th style="width:70%;background-color:#ff8000"></th>
                                        </tr>

					<tr>
						<th style="width:30%;">Event Name</th>
						<th style="width:70%;"><s:property value="event.eventName" /></th>
					</tr>
				
						<tr>
						<td>Date</td>	<td><s:property value="event.eventDate" /></td></tr>
						<tr> <td>Time</td>  	<td><s:property value="event.eventTime" /></td></tr>
						<tr> <td>Description</td>  	<td><s:property value="event.description"/></td></tr>
						 <tr><td>Country</td>  	<td><s:property value="event.country"/></td></tr>
						 <tr><td>State</td>  	<td><s:property value="event.state"/></td></tr>
						 <tr><td>City</td>  	<td><s:property value="event.city"/></td></tr>
						
					
				
	
				</table>
			</div>
			</div>
                        <div class="col-sm-6">

			 <div class="table-responsive">
                                <table id="dataTable" class="ui-table ui-responsive" width="100%" height="100%" align="right">
					 <tr>
                                                <th style="background-color:#ff8000;width:20%" colspan="5">Transit Details</th>
                                               <%-- <th style="background-color:#ff8000;width:20%"></th>
                                                <th style="background-color:#ff8000;width:20%"></th>
                                                <th style="background-color:#ff8000;width:20%"></th>
                                                <th style="background-color:#ff8000;width:20%"></th>--%>

                                        </tr>

                                        <tr>
                                                <th style="width:20%;">Transit</th>

						<th style="width:20%;">Description</th>
                                                <th style="width:20%;">RL</th>
                                                <th style="width:20%;">NL</th>
                                                <th style="width:20%;">SL</th>

                                        </tr>
                                
                                                  <tr><td>Lagna Sign</td><td><s:property value="event.sign" /></td><td><s:property value="event.lord" /></td><td><s:property value="event.NL" /></td><td></td></tr>
						
                                                  <tr><td></td><td>Sun</td><td><s:property value="event.sunRL" /></td><td><s:property value="event.sunNL" /></td><td><s:property value="event.sunSL"/></td></tr>
						<tr><td></td><td>Moon</td><td><s:property value="event.moonRL" /></td><td><s:property value="event.moonNL" /></td><td><s:property value="event.moonSL"/></td></tr>

                                                  <tr><td>Day Lord</td><td><s:property value="event.dayLord" /></td><td></td><td></td><td></td><td></td></tr>
                                                  <tr><td>MDL</td><td><s:property value="event.mdl" /></td><td></td><td><s:property value="event.mdlNL" /></td><td><s:property value="event.mdlSL" /></td></tr>
                                                  <tr><td>ADL</td><td><s:property value="event.adl" /></td><td></td><td><s:property value="event.adlNL" /></td><td><s:property value="event.adlSL" /></td></tr>
                                                  <tr><td>PDL</td><td><s:property value="event.pdl" /></td><td></td><td><s:property value="event.pdlNL" /></td><td><s:property value="event.pdlSL" /></td></tr>


 
                                </table>
                        </div>
			</div>
                        <div class="col-sm-12">

		       <div class="table-responsive">
                                <table id="dataTable" class="ui-table ui-responsive" width="100%" height="100%" align="right">
					 <tr>
                                                <th style="background-color:#ff8000;width:20%" colspan="5">Dasha Details</th>
                                               <%-- <th style="background-color:#ff8000;width:20%"></th>
                                                <th style="background-color:#ff8000;width:20%"></th>
                                                <th style="background-color:#ff8000;width:20%"></th>
                                                <th style="background-color:#ff8000;width:20%"></th>--%>

                                        </tr>

                                        <tr>
							<th style="width:20%;">DL Type</th>
							<th style="width:20%;">DL Name</th>
                                                        <th style="width:20%;">Degree</th>
							<th style="width:20%;">Upto</th>
							<th style="width:20%;">Rashi</th>
			
					</tr>

					<tr><td>MDL</td><td><s:property value="event.mdl" /></td><td><s:property value="event.mdlDegree" /></td><td><s:property value="event.mdlEndTime" /></td><td><s:property value="event.mdlRashi"/></td></tr>


					<tr><td>ADL</td><td><s:property value="event.adl" /></td><td><s:property value="event.adlDegree" /></td><td><s:property value="event.adlEndTime" /></td><td><s:property value="event.adlRashi"/></td></tr>
					<tr><td>PDL</td><td><s:property value="event.pdl" /></td><td><s:property value="event.pdlDegree" /></td><td><s:property value="event.pdlEndTime"/></td><td><s:property value="event.pdlRashi"/></td></tr>
                                </table>
                        </div>


	</div>

   <div class="col-sm-12">

                       <div class="table-responsive">
                            <%--    <table id="dataTable" class="ui-table ui-responsive" width="100%" height="100%" align="right">
					<tr>		<th style="width:100%;">Mahajyotish Script</th>
					</tr>
                                        <tr>
                                                        <th style="width:16%;">Planet</th>
                                                        <th style="width:16%;">Source</th>
                                                        <th style="width:17%;">Results(NL)</th>
                                                        <th style="width:17%;">Adtnl H</th>
                                                        <th style="width:17%;">SL</th>
                                                        <th style="width:17%;">NL(SL)</th>


                                        </tr>

                                        <tr><td><s:property value="event.mdl" /></td><td><s:property value="event.mdlDegree" /></td><td><s:property value="event.mdlEndTime" /></td><td><s:property value="event.mdlRashi"/></td></tr>


                                       <%-- <tr><td><s:property value="event.adl" /></td><td><s:property value="event.adlDegree" /></td><td><s:property value="event.adlEndTime" /></td><td><s:property value="event.adlRashi"/></td></tr>
                                        <tr><td><s:property value="event.pdl" /></td><td><s:property value="event.pdlDegree" /></td><td><s:property value="event.pdlEndTime"/></td><td><s:property value="event.pdlRashi"/></td></tr>
                                </table> --%>
 <table id="dataTable" width="100%" border="0" cellspacing="0"
                cellpadding="0" class="ui-table ui-responsive">
			   <thead>
                        <tr>
                                <th scope="col" style="background-color:#ff8000;width:15%" colspan="6">Mahajyotish Script</th>
                    <%--            <th scope="col" style="background-color:#ff8000;width:15%" ></th>
                                <th scope="col" style="background-color:#ff8000;width:15%" ></th>
                                <th scope="col" style=background-color:#ff8000;width:15% ></th>
                                <th scope="col" style="background-color:#ff8000;width:20% "></th>
                                <th scope="col" style="background-color:#ff8000;width:20%"></th>--%>
                        </tr>
		
	
                        <tr>
                                <th scope="col" width="15%">Planet</th>
                                <th scope="col" width="15%">Source</th>
                                <th scope="col" width="15%">Result(NL)</th>
                                <th scope="col" width="15%">Adtnl H</th>
                                <th scope="col" width="20%">SL</th>
                                <th scope="col" width="20%">NL(SL)</th>
                        </tr>
                </thead>
                <tbody>
                        <s:iterator value="newNatalStrengthChart" id="myAstro"
                                status="iterStatus">
                                <tr>
                                        <s:iterator value="myAstro">
                                                <td><s:property />
                                                </td>
                                        </s:iterator>
                                </tr>
                        </s:iterator>
                </tbody>
        </table>
                        </div>


        </div>



		</form>	
</div>		
<div class="form-group">
	<div class="row col-sm-offset-1 col-sm-10" id="backDiv">
           <a href="javascript: history.go(-1)"><img id="backImage" src="vastuImages/back.png" /> </a>
	</div>
</div>	

