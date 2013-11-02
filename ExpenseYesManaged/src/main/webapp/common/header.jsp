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

<div class="message info-message" id="info_message" <c:if test="${empty sessionScope.INFO_MESSAGE}">style="display: none"</c:if>>
	<p>
		<span><a href="javascript:void()" onclick="$('#info_message').hide()">X</a></span>
	</p>
	<p id="info_message_content" class="message-content">
		<c:out value="${sessionScope.INFO_MESSAGE}" escapeXml="false" />
	</p>
	<c:remove var="INFO_MESSAGE" scope="session" />
</div>

<div class="message error-message" id="error_message"  <c:if test="${empty sessionScope.ERROR_MESSAGE}">style="display: none"</c:if>>
	<p>
		<span><a href="javascript:void(0)" onclick="$('#error_message').hide()">X</a></span>
	</p>
	<p id="error_message_content" class="message-content">
		<c:out value="${sessionScope.ERROR_MESSAGE}" escapeXml="false" />		
	</p>
	<c:remove var="ERROR_MESSAGE" scope="session" />
</div>
