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
<script>

function validate(form)
{
var name= form.elements.name.value;
var pass= form.elements.password.value;

if(name=="" || name==null)
{
alert("Please enter user name");
return false;
}

if(pass=="" || pass==null)
{
alert("Please enter password");
return false;
}

return true;
}
</script>

<div class="row visible-xs hidden-sm hidden-md hidden-lg col-sm-12 col-xs-12" style="background-color: #E3B322;">
        <p align="center" style="margin-top:10px;"><font size="4" color="#7E0B1A" face="Verdana, Helvetica, sans-serif">KUNDLI ---</font></p>
        <form action="Regis.action" method="POST" onsubmit=" return validate(this)" id="form1" class="form-horizontal" style="margin-top:20px;">
                <div class="form-group">
                        <label for="name" class="col-sm-2 col-md-2 col-lg-3 control-label"><small>User Name :</small></label>
                        <div class="col-sm-4 col-md-4 col-lg-8">
                                <input type="text" class="form-control col-sm-4" id="name" name="userId" placeholder="User Name">
                        </div>
                </div>
                <div class="form-group">
                        <label for="password" class="col-sm-2 col-md-2 col-lg-3 control-label"><small>Password :</small></label>
                        <div class="col-sm-4 col-md-4 col-lg-8">
                                <input type="password" class="form-control" id="password" name="password"  placeholder="Password">
                        </div>
                </div>
                <div class="form-group">
                        <div class="col-sm-3 col-lg-3 col-sm-offset-3 col-lg-offset-3">
                        <button type="submit" class="btn btn-sm-4 col-sm-8">Sign in</button>
                </div>
		</div>
        </form>
</div>


<div class="row hidden-xs visible-sm visible-md visible-lg col-sm-10 col-lg-10 col-md-10 col-md-offset-1 col-lg-offset-1 col-sm-offset-1" style="background-color: #E3B322;margin-top:200px;">
	<p align="center" style="margin-top:10px;"><font size="4" color="#7E0B1A" face="Verdana, Helvetica, sans-serif">KUNDLI</font></p>
	<form action="Regis.action" method="POST" onsubmit=" return validate(this)" id="form2" class="form-horizontal" style="margin-top:20px;">
		<div class="form-group">
    			<label for="name" class="col-sm-3 col-md-3 col-lg-3 control-label"><small>User Name :</small></label>
    			<div class="col-sm-8 col-md-8 col-lg-8">
      				<input type="text" class="form-control col-sm-4" id="name" name="userId" placeholder="User Name">
    			</div>
  		</div>
  		<div class="form-group">
    			<label for="password" class="col-sm-3 col-md-3 col-lg-3 control-label"><small>Password :</small></label>
    			<div class="col-sm-8 col-md-8 col-lg-8">
      				<input type="password" class="form-control" id="password" name="password"  placeholder="Password">
    			</div>
  		</div>
  		<div class="form-group">
    			<div class="col-sm-3 col-lg-3 col-sm-offset-3 col-lg-offset-3">
      			<button type="submit" class="btn btn-sm-4 col-sm-8">Sign in</button>
    		</div>
	</form>
</div>


<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>

