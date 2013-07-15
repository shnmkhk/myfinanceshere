<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty sessionScope.INFO_MESSAGE}">
	<div class="info-message">
		<c:out value="${sessionScope.INFO_MESSAGE}" escapeXml="false" />
		<c:remove var="INFO_MESSAGE" scope="session" />
	</div>
</c:if>

<c:if test="${not empty sessionScope.ERROR_MESSAGE}">
	<div class="error-message">
		<c:out value="${sessionScope.ERROR_MESSAGE}" escapeXml="false" />
		<c:remove var="ERROR_MESSAGE" scope="session" />
	</div>
</c:if>
