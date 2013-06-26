<%@ page import="java.util.List"%>
<%@ page import="org.rabbit.model.Sheet"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Listing available sheets</title>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<display:table name="${sessionScope.allSheets}" class="Mars" pagesize="5" requestURI="#content">
		<display:column property="month" title="Month" />
		<display:column property="year" title="Year" />
		<display:column property="createdOn" title="Creation date" />
	</display:table>
</body>