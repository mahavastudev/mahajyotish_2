<%@taglib uri="/struts-tags" prefix="s"%>
<style>
@media only screen and (min-width: 750px) {
	#menuDiv,.well .sidebar-nav {
		height: 800px;
	}
	.nav-list>li>a:hover {
		color: orange;
	}
	.nav-list>li>a {
		color: white;
		border-bottom: 1px solid orange;
		padding-left: 0px;
	}
}
</style>


<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" id="menuList">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
	</div>
</div>



<div class="span3" style="margin-top: -10px; margin-left: 0px;">
	<div class="well sidebar-nav"
		style="background-color: #B95F00; bottom: 20px;z-index:1000;postion:absolute" id="menuDiv">
		<ul class="nav nav-list">
				<li>
				<s:url action="Generate.action" id="generate" encode="true">
    			</s:url>
    			<a href='<s:property value="%{generate}"/>' data-ajax="false">Generate Kundli</a>
				</li>
				<s:if test='#session.containsKey("8")'>
					<li>
					<s:url action="userMgt.action" id="userMgt" encode="true">
    				</s:url>
					<a href='<s:property value="%{userMgt}"/>' data-ajax="false">User Management</a>
					</li>
				</s:if>
				<s:if test='#session.containsKey("7")'>
					<li>
					<s:url action="roleMgt.action" id="roleMgt" encode="true">
					</s:url>
					<a href='<s:property value="roleMgt"/>' data-ajax="false">Role Management</a>
					</li>
				</s:if>
                <li >
				<s:url action="changePass.action" id="changePass" encode="true">
				</s:url>
				<a href='<s:property value="changePass"/>' data-ajax="false">Change Password</a>
				</li>
				<li>
				<s:url action="Logout.action" id="logOut" encode="true">
				</s:url>
				<a href='<s:property value="logOut"/>' data-ajax="false">Logout</a>
				</li>
		</ul>
	</div>
</div>

