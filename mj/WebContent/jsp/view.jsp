<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<script>

// $(document).ready(function(){
	
// 	var file=document.getElementById("file").value;
// 	$("#myForm").submit();
// });

</script>

<%-- <s:form id="myForm" action="displayPdf" method="GET"> --%>
<%-- <s:hidden name="fileName" id="file" value="%{astrobean.fileName}"/> --%>
<%-- <s:property value="%{astrobean.fileName}"/> --%>
<%-- </s:form> --%>

<div class="container-fluid" >
<div class="row-fluid" id="pdfView" style="height: 450px;">
<iframe id="ifram" src='displayPdf?fileName=<s:property value="%{astrobean.fileName}"/>' style="width:100%;height:100%" sandbox="allow-forms allow-scripts"></iframe>

</div>
</div>

