<%@ page import="java.util.List, org.rabbit.model.Entry"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<title>Entries of Sheet [<c:out
		value="${sessionScope.SHEET_KEY_ID}" />]
</title>
<body>
	<%@ include file="/common/header.jsp"%>
	<display:table name="${sessionScope.entriesOfSelectedSheet}"
		class="Mars" requestURI="#content" id="entryRow"
		decorator="org.rabbit.decorators.EntryDecorator"
		style="text-align: right;">
		<display:column property="hyperlink" title="Label" class="align-right" />
		<display:column property="amount" title="Amount"
			class="align-right ${entryRow.styleClass}"
			format="{0,number,#,###.00}" />
	</display:table>
	<c:if test="${not empty sessionScope.entriesTotal}">
		<hr width="50%" style="float: right; text-align: right; width: 50%;" />
		<div style="float: right; text-align: right; width: 100%">
			<div class="sum-of-entries-amounts">
				Account balance: <label> <fmt:formatNumber
						value="${sessionScope.entriesTotal}" type="currency" pattern="#,###.00"/>
				</label>
			</div>
		</div>
	</c:if>

</body>