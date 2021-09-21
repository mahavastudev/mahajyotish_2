
<%@taglib uri="/struts-tags" prefix="s"%>
<head>

	<TITLE><s:i18n name="vastu">
			<s:text name="admin" />
		</s:i18n>
	</TITLE>

	<link href="pagefile/frstyles.css" rel="stylesheet" type="text/css">
	<link href="pagefile/default.css" rel="stylesheet" type="text/css">
	<META content="MSHTML 6.00.2900.3086" name=GENERATOR>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<link type="text/css" href="pagefile/menu.css" rel="stylesheet" />
	<script type="text/javascript" src="pagefile/jquery.js"></script>
	<script type="text/javascript" src="pagefile/menu.js"></script>

</head>


<body style="overflow-x: hidden;">

	<table height="30" width="86%" border="0" align="center"
		cellpadding="0" cellspacing="0" bgcolor="#eaeaea">
		<tr>
			<td colspan=3 class="logintext">
				<!-- <img src="images/tophead1.png">-->
				<font size="4"><b> <s:i18n name="vastu">
							<s:text name="vastuadm" />
						</s:i18n> </b> </font>
			</td>
		</tr>
		<tr>
			<td colspan=3 height="20">
				<hr size=2 style="background-color: #ffffff;">
			</td>
		</tr>
		<tr>
			<td width="170px" align="left" valign="top" height="30px"
				style="border-style: solid; border-width: 2px; border-color: #eaeaea">
				<br />
				<label class="tfiled1">
					<font size="2"><b>HANDSET&nbsp;APPLICATION</b> </font>
				</label>

				<div id="menu">
					<ul class="menu">
						<li>
							<a href="home.jsp"><span> <s:i18n name="vastu">
										<s:text name="home" />
									</s:i18n> </span> </a>
						</li>
						<li>
							<s:url action="View.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="view" />
									</s:i18n> </span>
							</s:a>
						</li>
						<li>
							<s:url action="RequestedProblems.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="reqprob" />
									</s:i18n> </span>
							</s:a>
						</li>

						<li>
							<s:url action="ProblemRank.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="rank" />
									</s:i18n> </span>
							</s:a>
						</li>

						<li>
							<s:url action="VastuObjects.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:property value="getText('vastuObj')" /> </span>
							</s:a>
						</li>
						<li>
							<s:url action="templateHome.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="templateMgmt" />
									</s:i18n> </span>
							</s:a>
						</li>


						<li>
							<s:url action="SystemConfig.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:property value="getText('sysconfig')" /> </span>
							</s:a>
						</li>


						<li>
							<s:url action="vastuTipsHome.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="vastuTipMgt" />
									</s:i18n> </span>
							</s:a>
						</li>


						<li>
							<s:url action="Reports.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="reports" />
									</s:i18n> </span>
							</s:a>
						</li>

						<li>
							<s:url action="UpdateWebservice.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="updateWeb" />
									</s:i18n> </span>
							</s:a>
						</li>

						<li>
							<s:url action="Change.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="change" />
									</s:i18n> </span>
							</s:a>
						</li>
					</ul>
				</div>

				<br />
				<label class="tfiled1">
					<font size="2"><b>KUNDLI</b> </font>
				</label>
				<div id="menu">
					<ul class="menu">
						<li>
							<s:url action="KundliRequest.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="kundli" />
									</s:i18n> </span>
							</s:a>
						</li>

						<li>
							<s:url action="Generate.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="genKundli" />
									</s:i18n> </span>

							</s:a>
						</li>

					</ul>
				</div>



				<br />
				<label class="tfiled1">
					<font size="2"><b>REAL&nbsp;ESTATE</b> </font>
				</label>

				<div id="menu">
					<ul class="menu">
						<li>
							<s:url action="cityHome.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="citymgt" />
									</s:i18n> </span>
							</s:a>
						</li>


						<li>
							<s:url action="builderHome.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="buildermgt" />
									</s:i18n> </span>
							</s:a>
						</li>


						<li>
							<s:url action="viewProject.action" id="URLtag">
							</s:url>
							<s:a href="%{URLtag}" errorText="An error ocurred"
								loadingText="Loading...">
								<span> <s:i18n name="vastu">
										<s:text name="projectmgt" />
									</s:i18n> </span>
							</s:a>
						</li>

					</ul>
				</div>
				<br />
				<div id="menu">
					<ul class="menu">
						<li class="last">
							<span> <s:url action="logout.action" id="URLtag"></s:url>
								<s:a href="%{URLtag}" errorText="An error ocurred"
									loadingText="Loading...">
									<span> <s:i18n name="vastu">
											<s:text name="out" />
										</s:i18n> </span>
								</s:a>
						</li>
					</ul>
				</div>
			</td>
			<td width="10"></td>
			<td valign="top">
				<br>
				<br>
				<table width="100%" height=645 border=1 cellspacing=0 cellpadding=0
					bordercolor="#E3B322">
					<tr>
						<td valign="top" align="center"
							style="background-repeat: no-repeat">
