<%@ page
	import="java.util.List, org.rabbit.model.Sheet, com.google.appengine.api.users.UserService, com.google.appengine.api.users.UserServiceFactory"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Sheets list</title>
<body>
	<%@ include file="/common/header.jsp"%>
	<div>
		<ul class="horizontal-list" style="padding: 2px; text-align: right;">
			<li class="header-one">Sheet Listing</li>
			<li class="header-two">All</li>
		</ul>
		<ul class="horizontal-list" style="text-align: left;">
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
		<ul class="horizontal-list occupy-half-page-width">
			<c:forEach var="sheetEntry" items="${sessionScope.allSheetsMap}">
				<li class="header-two"><c:out value='${sheetEntry.key}' /></li>
				<li>
					<ul>
						<c:forEach var="sheetObj" items="${sheetEntry.value}">
							<li><a class="no-underline"
								href='/ea/<c:out value="${sheetObj.keyStr }"/>#content'>
									<div class="sheet-container">
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
		<ul class="horizontal-list" style="float: left;">
			<li><a href="<c:url value='/sa/#content'/>">Reload sheets</a></li>
			<li>&nbsp;|&nbsp;</li>
			<li><a href="<c:url value='/as.jsp#content'/>">Add a sheet</a></li>
		</ul>
	</c:if>
</body>