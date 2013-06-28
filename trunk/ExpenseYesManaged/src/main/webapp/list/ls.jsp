<%@ page import="java.util.List, org.rabbit.model.Sheet" errorPage="/error.jsp"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<title>Listing available sheets</title>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<display:table name="${sessionScope.allSheets}" class="Mars" pagesize="5" requestURI="#content" id="sheetRow" decorator="org.rabbit.decorators.SheetDecorator">
		<display:column title="Select" property="hyperlink" />
		<display:column property="month" title="Month" />
		<display:column property="year" title="Year" />
		<display:column property="createdOn" title="Creation date" format="{0,date,MMM dd, yyyy}"/>
	</display:table>
</body>