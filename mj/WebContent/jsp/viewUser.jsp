<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
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
     $('#searchTerm').keyup(function() {
          searchTable($(this).val());
     });
//	$('#dataTable_previous').html("Previous/ ");
//	document.getElementById("dataTable_previous").innerHTML = "Previous/ "; 
  
});
	function validate() {
		
		var value = $('#delCheckList:checked').val();
		if (value == null) {
			var str = "please select value to delete";
			alert(str);
			return false;
		} 
		var r = confirm("Are you sure want to delete?");
		if (r == true) {
		       return true;
		    } else {
		        return false;
		    }
		return true;
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
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<div class="col-sm-12" id="innerHeadingDiv">
			<div class="container-fluid"
				style="padding-left: 0px; padding-top: 0px; padding-bottom: 0px;">
				<div class="row-fluid">
					<div class="span8">
						<p class="header" align="center">User Management View</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>

<div class="row col-sm-offset-1 col-sm-10">
	<form id="frm_demo" name="frm_demo" action='<c:url value="deleteUserExec.action"/>'
		data-ajax="false" method="post" onsubmit="return validate()" class="form-horizontal">
		<s:hidden id="msg" value="%{message}" name="msg" />
                <!-- <div class="form-group">
                     <div class="col-sm-offset-8 col-sm-3  col-xs-12">
                          <input type="text" id="searchTerm" placeholder="search" class="form-control" />
                     </div>
                 </div> -->
		<div class="table-responsive">
			 <table id="dataTable" width="100%"
				height="100%" align="center">
				<thead>
				<tr>
					<th>NAME</th>
					<s:if test='#session.containsKey("41")'>
					<th>PASSWORD</th>
					</s:if>
					<th>CC CODE</th>
					<th>EMAIL</th>
					<th>MOBILENO</th>
					<th>ROLE</th>
					<th>ROLE2</th>
					<th>CREATE DATE</th>
					<s:if test='#session.containsKey("5")'>
						<th>MODIFY</th>
					</s:if>
					<s:if test='#session.containsKey("6")'>
						<th>DELETE</th>
					</s:if>
				</tr>
				</thead>
				<tbody>
				<s:iterator value="userLst">
					<tr>
						<td><s:property value="uname" />
						</td>
						<s:if test='#session.containsKey("41")'>
						<td><s:property value="upassword" />
						</td>
						</s:if>
						<td><s:property value="ccCode" />
						<td><s:property value="email" />
						</td>
						<td><s:property value="mobileNo" />
						</td>
						<td><s:property value="roleName" />
						<td><s:property value="roleName2" />
						</td>
						<td><s:property value="createDate" />
						</td>
						<s:if test='#session.containsKey("5")'>
							<td>
<%-- 							<s:url action="modifyUser.action" id="modifyUser" encode="true" includeParams="none"> --%>
<%-- 							<s:param name="userInfo.uId"><s:property value="uId"/></s:param> --%>
<%--     				    	</s:url> --%>
							<a href='<c:url value="modifyUser.action?userInfo.uId=${uId}"/>'
								class="tableDataLink" data-ajax="false">Modify</a></td>
						</s:if>
						<s:if test='#session.containsKey("6")'>
							<td>
							<s:checkbox name="delCheckList"  id="delCheckList"  fieldValue="%{uId}" />
							</td>
						</s:if>
					</tr>
				</s:iterator>
				</tbody>
			</table>
  <script type="text/javascript" charset="utf8" src="js/jquery-1.10.2.min.js"></script>
  <script type="text/javascript" charset="utf8" src="js/jquery.dataTables.min.js"></script>
  
  <script>
  $(function(){
    var table = $("#dataTable").dataTable({
    	"jQueryUI" : false,
        "pagingType" : "full_numbers",
        "iDisplayLength": 10,
        "lengthChange": false,
        "bLengthMenu" : false,
        "lengthMenu" : [ [ 0,5, 10, 50, -1 ], [ 0,5, 10, 50, "All" ] ],
         "aaSorting": [[ 0, "desc" ]],
		"language": {
    		"paginate": {
      			"previous": "Previous/ "
    		}
  	}	 
    });
    $("#buttons").hide();
    $("#dataTable").hide();
    $("#dataTable_length").hide();
    $("#dataTable_info").hide();
    $("#dataTable_paginate").hide();
    $("input[type='text']").keyup(function(){
    	console.log(this.selectionEnd);
    	if(this.selectionEnd==0){
    		$("#dataTable").hide();
    	    $("#dataTable_length").hide();
    	    $("#dataTable_info").hide();
    	    $("#dataTable_paginate").hide();
    	    $("#buttons").hide();
    	    return;
    	}
    	$("#buttons").show();
    	$("#dataTable").show();
    	$("#dataTable_info").show();
        $("#dataTable_paginate").show();
    	
    });
        	
  })
  </script>
		</div>
		<s:if test='#session.containsKey("6")'>
		<div class="form-group" id="buttons">
			  <div class="col-sm-2"></div>
				<div class="col-sm-4 ui-content">
						<button type="delete" id="place" class="form-control"
									style="background-color: #FFA500;color: white">Delete</button>
				</div>
				<div class="col-sm-4 ui-content">
								<button type="reset" value="Reset" class="form-control"
									style="background-color: #FFA500; color: white">Reset</button>
				</div>
			</div>
		</s:if>
		
	</form>
</div>
<div class="form-group">
			<div class="row col-sm-offset-1 col-sm-10" id="backDiv">
				<a href="javascript: history.go(-1)"><img id="backImage"
					src="vastuImages/back.png" /> </a>
			</div>
		</div>
