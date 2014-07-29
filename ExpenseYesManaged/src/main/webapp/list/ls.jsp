<! DOCTYPE HTML>
<html>
<title>Sheets list</title>
<head>
	<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<script type="text/javascript"
		src="<c:url value='/scripts/jquery-1.10.2.min.js'/>"></script>
	<script type='text/javascript'
		src="<c:url value='/dwr/interface/SheetService.js'/>"></script>
	<script type='text/javascript' src="<c:url value='/dwr/engine.js'/>"></script>

	<%@ page
		import="java.util.List, org.rabbit.model.Sheet, com.google.appengine.api.users.UserService, com.google.appengine.api.users.UserServiceFactory"
		errorPage="/error.jsp"%>
	<%@ include file="../common/header.jsp"%>
</head>
<body>

	<div style="width: 100%; padding-bottom: 3px;">
		<ul class="horizontal-list" style="text-align: right; width: 100%;">
			<li class="header-one">Sheet Listing</li>
			<li class="header-two">All</li>
		</ul>
		<ul class="horizontal-list" style="text-align: left; width: 100%;">
			<li><a href="<c:url value='/sa/#content'/>"
				onclick="loadSheets(true);return false;">Reload sheets</a></li>
			<li>&nbsp;|&nbsp;</li>
			<li><a href="<c:url value='/as.jsp#content'/>" class="submit">Add
					a new sheet</a></li>
		</ul>
	</div>
	<c:if test="${empty sessionScope.allSheetsMap}">
		<div style="float: right; padding-bottom: 5px; margin-bottom: 3px;">No
			sheets found to display</div>
	</c:if>
	<c:if test="${not empty sessionScope.allSheetsMap}">
		<hr />
		<ul class="horizontal-list occupy-half-page-width"
			style="width: 100%;" id="sheet-list-container">
			<c:forEach var="sheetEntry" items="${sessionScope.allSheetsMap}">
				<li class="header-two"><c:out value='${sheetEntry.key}' /></li>
				<li>
					<ul class="calendar-icon-ul">
						<c:forEach var="sheetObj" items="${sheetEntry.value}">
							<li class="calendar-icon-li"><a class="no-underline"
								href='/ea/<c:out value="${sheetObj.keyStr }"/>#content'>
									<div class="sheet-container cursor-pointer">
										<div class="sheet-container-month">
											<c:out value="${sheetObj.shortMonthStr}" />
										</div>
										<div class="sheet-container-year">
											<c:out value="${sheetObj.year}" />
										</div>
									</div>
							</a></li>
						</c:forEach>
					</ul>
				</li>
				<hr />
			</c:forEach>
		</ul>
		<c:if test="${fn:length(sessionScope.allSheetsMap) > 5}">
			<ul class="horizontal-list"
				style="text-align: left; width: 100%; padding-top: 3px;">
				<li><a href="<c:url value='/sa/#content'/>"
					onclick="loadSheets(0, true);return false;">Reload sheets</a></li>
				<li>&nbsp;|&nbsp;</li>
				<li><a href="<c:url value='/as.jsp#content'/>" class="submit">Add
						a new sheet</a></li>
			</ul>
		</c:if>
	</c:if>
	<script type="text/javascript" src="<c:url value='/scripts/sheet-util.js'/>"></script>
	<script type="text/javascript">
		currentInterfaceId = 0;
	</script>
</body>
</html>