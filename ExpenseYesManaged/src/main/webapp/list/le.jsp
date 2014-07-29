<html>
<head>
<%@ page import="java.util.List, org.rabbit.model.Entry"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<script type='text/javascript' src="<c:url value='/dwr/interface/SheetService.js'/>"></script>
<script type='text/javascript' src="<c:url value='/dwr/engine.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/displayTagAjax.js'/>"></script>
</head>
<body>
	<c:if test="${empty sessionScope.SHEET_MONTH_YR_ARRAY}">
		<c:redirect url="/list/ls.jsp#content"></c:redirect>
	</c:if>
	<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
		<title>Entries of sheet [<c:out
				value='${sessionScope.SHEET_MONTH_YR_ARRAY[0]}' />, <c:out
				value='${sessionScope.SHEET_MONTH_YR_ARRAY[1]}' />]
		</title>
	</c:if>
	<%@ include file="../common/header.jsp"%>
	<script type="text/javascript"
		src="<c:url value='/scripts/jquery-1.10.2.min.js'/>"></script>
	<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
		<div style="padding-bottom: 3px;">
			<ul class="horizontal-list" style="text-align: right; width: 100%;">
				<li class="header-one">Entries</li>
				<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
					<li class="header-two"><c:out
							value="${sessionScope.SHEET_MONTH_YR_ARRAY[0]}" /></li>
					<li class="header-three"><c:out
							value="${sessionScope.SHEET_MONTH_YR_ARRAY[1]}" /></li>
				</c:if>
			</ul>
			<ul class="horizontal-list" style="text-align: left; width: 100%;">
				<li><a href="<c:url value='/sa/#content'/>">Back to sheets</a></li>
				<li>&nbsp;|&nbsp;</li>
				<li><a href="<c:url value='/ea/#content'/>"
					onclick="doAjax(current_page_uri);return false;">Reload entries</a></li>
				<li>&nbsp;|&nbsp;</li>
				<li><a
					href="<c:url value='/mae.jsp?sid=${sessionScope.SHEET_KEY_ID}#content'/>"
					class="submit">Add entries </a></li>
			</ul>
		</div>
	</c:if>
	<jsp:include page="../ajax/display.le.jsp"></jsp:include>
	<script type="text/javascript">
	<!--
		try {
			// a crossbrowser solution
			$(document).ready(function() {
				dwr.engine.setErrorHandler(function(message) {
					$("body").removeClass("loading");
				});
				changeLinks();
			});
		} catch (error) {
		}
		-->
	</script>
</body>
</html>
