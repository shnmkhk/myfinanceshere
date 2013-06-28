<%@ page import="java.util.List, org.rabbit.model.Entry" errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<title>Entries of Sheet[<c:out value=""/>]</title>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<display:table name="${sessionScope.entriesOfSelectedSheet}" class="Mars" pagesize="5" requestURI="#content" id="entryRow" decorator="org.rabbit.decorators.EntryDecorator">
		<display:column property="hyperlink" title="Select"/>
		<display:column property="amount" title="Amount" />
		<display:column property="shortCode" title="Code" />
		<display:column property="description" title="Description"/>
		<display:column property="status" title="Status"/>
	</display:table>
</body>