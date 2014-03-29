<%@ page
	import="com.google.appengine.api.users.UserService, com.google.appengine.api.users.UserServiceFactory"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	UserService userService = UserServiceFactory.getUserService();
	String thisURL = request.getRequestURI();
	String loginUrl = userService.createLoginURL(response
			.encodeRedirectURL("/home.jsp#content"));
%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
<c:if test="${not empty param.sid}">
	<c:set var="SHEET_KEY_ID" value="${param.sid}" scope="session" />
</c:if>
<div class="body" style="width: 100%">
	<div data-role="footer" data-theme="a" style="padding: 5px 10px;">
		<%
			if (request.getUserPrincipal() != null) {
		%><h1 align="left">
			Logged in as <b><%=request.getUserPrincipal().getName()%></b>
		</h1>
		<%
			}
		%>

		<div data-role="navbar">
			<ul style="padding: 5px 10px;">
				<%
					if (request.getUserPrincipal() != null) {
				%>
				<li><a href="<c:url value='/home.jsp'/>#content"
					class="ui-btn-active" data-ajax="false">Home</a></li>
				<li><a
					href="<%=userService.createLogoutURL("/home.jsp#content")%>"
					onclick="_logoutNow();return false;">Logout</a></li>
				<%-- 
				<li><a href="<%=userService.createLogoutURL("/home.jsp#content")%>">Logout</a></li>
				--%>
				<%
					} else {
				%>
				<li><a href="<%=loginUrl%>" data-ajax="false"> <img
						alt="Sign-in with Google"
						src="/css/images/sign-in-with-google.png" />
				</a></li>
				<%
					}
				%>
			</ul>
		</div>
		<!-- /navbar -->
	</div>
	<!-- /header -->
</div>

<script type="text/javascript">
	var logoutLandingPage = '<c:out value="${baseURL}"/>' + '<c:url value="/_ah/logout"/>?continue=';
	var logoutContinuePage = '<c:out value="${baseURL}"/>' + '<c:url value="/home.jsp#content"/>';
	function _logoutNow() {
		var consolidatedUrl = logoutLandingPage + logoutContinuePage;
		location.href = consolidatedUrl;
		return false;
	}
</script>