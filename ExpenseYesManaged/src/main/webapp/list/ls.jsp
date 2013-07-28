<%@ page
	import="java.util.List, org.rabbit.model.Sheet, com.google.appengine.api.users.UserService, com.google.appengine.api.users.UserServiceFactory"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<title>Sheets list</title>
<body>
	<%@ include file="/common/header.jsp"%>
	<div style="width: 100%;">
		<ul class="horizontal-list" style="text-align: right; width: 100%;">
			<li class="header-one">Sheet Listing</li>
			<li class="header-two">All</li>
		</ul>
		<ul class="horizontal-list" style="text-align: left; width: 100%;">
			<li><a href="<c:url value='/sa/#content'/>">Reload sheets</a></li>
			<li>&nbsp;|&nbsp;</li>
			<li><a href="<c:url value='/as.jsp#content'/>">Add a sheet</a></li>
		</ul>
	</div>
	<c:if test="${empty sessionScope.allSheetsMap}">
		<div style="float: right">No sheets found to display</div>
	</c:if>
	<c:if test="${not empty sessionScope.allSheetsMap}">
		<hr />
		<ul class="horizontal-list occupy-half-page-width"
			style="width: 100%;">
			<c:forEach var="sheetEntry" items="${sessionScope.allSheetsMap}">
				<li class="header-two"><c:out value='${sheetEntry.key}' /></li>
				<li>
					<ul
						style="list-style-type: none; text-align: left; display: inline-block; word-wrap: break-word;">
						<c:forEach var="sheetObj" items="${sheetEntry.value}">
							<li style="display: inline-table; float: left; margin: 2px; width: 50px;"><a class="no-underline"
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
			<ul class="horizontal-list" style="text-align: left; width: 100%;">
				<li><a href="<c:url value='/sa/#content'/>">Reload sheets</a></li>
				<li>&nbsp;|&nbsp;</li>
				<li><a href="<c:url value='/as.jsp#content'/>">Add a sheet</a></li>
			</ul>
		</c:if>
	</c:if>
</body>