<html>
<head>
 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="js/tablednd.css" />
</head>
<body>
<form action="downloadPdf.action" method="POST" >
  <div class="table-responsive">
  <table class="table">
	<input type="hidden" name="fileName" value="%{fileName}"/>
  	<tr><td colspan="2"><button type="submit">Download</button></td></tr>
  </table>
</div>
</form>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>

