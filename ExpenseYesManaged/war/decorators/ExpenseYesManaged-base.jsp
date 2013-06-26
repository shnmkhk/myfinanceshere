<!doctype html>
<!-- The DOCTYPE declaration above will set the     -->
<!-- browser's rendering engine into                -->
<!-- "Standards Mode". Replacing this declaration   -->
<!-- with a "Quirks Mode" doctype is not supported. -->

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--                                                               -->
<!-- Consider inlining CSS to reduce the number of requested files -->
<!--                                                               -->
<link type="text/css" rel="stylesheet" href="/css/displaytag.css">
<link type="text/css" rel="stylesheet" href="/css/print.css">
<link type="text/css" rel="stylesheet" href="/css/site.css">
<link type="text/css" rel="stylesheet" href="/css/ExpenseYesManaged.css">
<!--                                           -->
<!-- Any title is fine                         -->
<!--                                           -->
<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<title>Rabbit Computing :: <decorator:title /></title>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
<body>
	<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
			Your web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>
	<div class="main-body random-image-background" align="center">
		<div class="main-container transparent-black-background">
			<div class="main-widget">
				<div class="main-content">
					<div style="height: 100%; margin: 0px">
						<div class="header" style="height: 30%">
							<div class="rabbit-common-align-left header-container">
								<p style="padding-left: 5px;">
									<font color="#FFFFFF">Expense-<font color="orange"
										size="4">Yes</font>-Managed<br /> <span class="tagline">Enable
											yourself to track your spendings</span></font>
								</p>
								<div align="right" style="padding: 5px;">
									<div style="min-width: 1.5em; min-height: 1.5em;"></div>
								</div>
							</div>
						</div>

						<div class="body" style="height: 70%" id="content">
							<table class="form-background">
								<tr>
									<td>
										<div class="header-text">
											<decorator:title />
										</div>
									</td>
								</tr>
								<tr>
									<td><decorator:body /></td>
								</tr>
							</table>
							<table class="form-background">
								<tr>
									<td>
										<div>
											<ul class="horizontal-list">
												<li><label>shanmukha.k@gmail.com</label></li>
												<li>&nbsp;|&nbsp;</li>
												<li><a href="/">Logout</a></li>
												<li>&nbsp;|&nbsp;</li>
												<li><a href="/as.jsp">Add a sheet</a></li>
												<li>&nbsp;|&nbsp;</li>
												<li><a href="/sa">Sheet Listing</a></li>
											</ul>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>