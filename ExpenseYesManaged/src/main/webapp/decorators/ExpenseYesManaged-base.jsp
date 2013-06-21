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

<!--                                                               -->
<!-- Consider inlining CSS to reduce the number of requested files -->
<!--                                                               -->
<link type="text/css" rel="stylesheet" href="css/ExpenseYesManaged.css">

<!--                                           -->
<!-- Any title is fine                         -->
<!--                                           -->
<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<script type="text/javascript" language="javascript"
	src="ExpenseYesManaged/ExpenseYesManaged.nocache.js"></script>
<title>Expense-Yes-Managed :: Rabbit Computing</title>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
<body>
	<!-- OPTIONAL: include this if you want history support -->
	<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"></iframe>

	<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
			Your web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>
	<div class="">
		<div class="main-body random-image-background" align="center">
			<div class="main-container transparent-black-background">
				<div class="main-widget">
					<div class="main-content">
						<div style="height: 100%; margin: 0px">
							<div class="header" style="height: 30%">
								<div class="rabbit-common-align-left header-container">
									<p style="padding-left: 5px;">
										<font color="#FFFFFF">Expense-<font color="orange" size="4">Yes</font>-Managed<br /> <span class="tagline">Enable
											yourself to track your spendings</span></font>
									</p>
									<div align="right" style="padding: 5px;">
										<div style="min-width: 1.5em; min-height: 1.5em;"></div>
									</div>
								</div>
							</div>

							<div class="body" style="height: 70%">
								<table class="form-background">
									<tr>
										<td><decorator:body /></td>
									</tr>
								</table>
								<table class="form-background">
									<tr>
										<td>
											<div>
												<label>shanmukha.k@gmail.com</label>&nbsp;|&nbsp; <a href="">Logout</a>&nbsp;|&nbsp;
												<a href="">Add an expense</a>
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
	</div>
</body>
</html>