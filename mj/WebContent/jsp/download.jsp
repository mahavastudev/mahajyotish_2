<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row hidden-xs visible-sm visible-md visible-lg col-sm-10 col-lg-10 col-md-10 col-md-offset-1 col-lg-offset-1 col-sm-offset-1" style="margin-top:60px;">
</div>

<div class="row col-sm-10 col-lg-10 col-md-10 col-md-offset-1 col-lg-offset-1 col-sm-offset-1" style="background-color: #eaeaea;border: 1px solid #E3B322">
<p align="center" style="margin-top:10px;"><font size="4" color="#7E0B1A" face="Verdana, Helvetica, sans-serif"><s:i18n name="vastu">
                <s:text name="genKundli" />
        </s:i18n></font></p>
  
<form action='<c:url value="downloadPdf.action"/>' method="POST" class="form-horizontal" style="margin-top:50px;">

<div class="form-group">
<label for="name" class="col-sm-4 control-label"></label>
    <div class="col-sm-12">
	<p align="center"><button type="submit" class="btn btn-default">Download</button></p>
    </div>
  </div>
	
</form>
</div>


