<%@ page import="java.util.List, org.rabbit.model.Sheet"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<title>Listing available sheets</title>
<body>
	<%@ include file="/common/header.jsp" %>
	<c:if test="${not empty sessionScope.allSheets}">
		<ul class="horizontal-list occupy-half-page-width">
			<c:forEach var="sheetObj" items="${sessionScope.allSheets}">
				<li>
					<a class="no-underline" href='/ea/<c:out value="${sheetObj.keyStr }"/>#content'>
					<div class="sheet-container">
						<div class="sheet-container-month"><c:out value="${sheetObj.shortMonthStr}"/></div>
						<div class="sheet-container-year"><c:out value="${sheetObj.year}"/></div>
					</div>
					</a>
				</li>
			</c:forEach>
		</ul>
	</c:if>
</body>