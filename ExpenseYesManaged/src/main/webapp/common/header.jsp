<%@ page errorPage="/error.jsp"%>
<style>
p {
	position: relative; 
	right: 2px; 
	top: 2px;
}

p span {
	width: 5px; 
	height: 5px;
}

p span a {
	position: absolute; 
	right: 0px; 
	top: -15px;
	text-decoration: none;
	font-weight: bold;
	font-size: 0.75em;
}
</style>
<%!
	boolean authenticated = false;
%>
<%
	if (request.getUserPrincipal() != null){
		authenticated = true;
	} else {
		response.sendRedirect("/index.jsp#content");
	}
%>

<c:if test="${not empty sessionScope.INFO_MESSAGE}">
	<div class="message info-message" id="info_message">
		<p>
			<span><a href="javascript:void()" onclick="$('#info_message').remove()">X</a></span>
		</p>
		<c:out value="${sessionScope.INFO_MESSAGE}" escapeXml="false" />
		<c:remove var="INFO_MESSAGE" scope="session" />
	</div>
</c:if>

<c:if test="${not empty sessionScope.ERROR_MESSAGE}">
	<div class="message error-message" id="error_message">
		<p>
			<span><a href="javascript:void(0)" onclick="$('#error_message').remove()">X</a></span>
		</p>
		<c:out value="${sessionScope.ERROR_MESSAGE}" escapeXml="false" />
		<c:remove var="ERROR_MESSAGE" scope="session" />
	</div>
</c:if>
