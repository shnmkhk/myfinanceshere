<%@ page import="java.util.List, org.rabbit.model.Entry"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>

<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
	<title>Entries of sheet 
		[<c:out value='${sessionScope.SHEET_MONTH_YR_ARRAY[0]}'/>, 
		<c:out value='${sessionScope.SHEET_MONTH_YR_ARRAY[1]}'/>]
	</title>
</c:if>
<body>
	<%@ include file="/common/header.jsp"%>
	<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
		<div>
			<ul class="horizontal-list" style="padding: 2px; text-align: right;">
				<li class="header-one">Entries</li>
				<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
					<li class="header-two"><c:out
							value="${sessionScope.SHEET_MONTH_YR_ARRAY[0]}" /></li>
					<li class="header-three"><c:out
							value="${sessionScope.SHEET_MONTH_YR_ARRAY[1]}" /></li>
				</c:if>
			</ul>
			<ul class="horizontal-list" style="text-align: left;">
				<li><a href="<c:url value='/sa/#content'/>">Back to sheets</a></li>
				<li>&nbsp;|&nbsp;</li>
				<li><a href="<c:url value='/ea/#content'/>">Reload entries</a></li>
				<li>&nbsp;|&nbsp;</li>
				<li><a href="<c:url value='/ae.jsp?sid=${sessionScope.SHEET_KEY_ID}#content'/>">Add an entry</a></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${not empty sessionScope.entriesOfSelectedSheet}">
		<div style="text-align: right;">
			<display:table name="${sessionScope.entriesOfSelectedSheet}"
				class="Mars" requestURI="#content" id="entryRow"
				decorator="org.rabbit.decorators.EntryDecorator"
				style="text-align: right;" pagesize="5">
				<display:column property="hyperlink" title="Label" class="align-right" />
				<display:column property="amount" title="Amount"
					class="align-right ${entryRow.styleClass}"
					format="{0,number,#,###.00}" />
			</display:table>
		</div>
	</c:if>
	<c:if test="${not empty sessionScope.entriesTotal}">
		<hr width="50%" style="float: right; text-align: right; width: 50%;" />
		<div style="float: right; text-align: right; width: 100%">
			<div class="sum-of-entries-amounts">
				Account balance: <label> <fmt:formatNumber
						value="${sessionScope.entriesTotal}" type="currency"
						pattern="#,###.00" />
				</label>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty sessionScope.entriesOfSelectedSheet}">
		<ul class="horizontal-list" style="float: left;">
			<li><a href="<c:url value='/sa/#content'/>">Back to sheets</a></li>
			<li>&nbsp;|&nbsp;</li>
			<li><a href="<c:url value='/ea/#content'/>">Reload entries</a></li>
			<li>&nbsp;|&nbsp;</li>
			<li><a href="<c:url value='/ae.jsp?sid=${sessionScope.SHEET_KEY_ID}#content'/>">Add an entry</a></li>
		</ul>
	</c:if>
</body>