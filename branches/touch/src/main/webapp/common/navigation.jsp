<%@ page
	import="com.google.appengine.api.users.UserService, com.google.appengine.api.users.UserServiceFactory"
	errorPage="/error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	UserService userService = UserServiceFactory.getUserService();
	String thisURL = request.getRequestURI();
%>
<c:if test="${not empty param.sid}">
	<c:set var="SHEET_KEY_ID" value="${param.sid}" scope="session" />
</c:if>
<div class="body" style="width: 100%">
	<table class="form-background">
		<tr>
			<td>
				<div>
					<ul class="horizontal-list">
						<li>[&nbsp;<a href="<c:url value='/home.jsp'/>#content">Standard
								view</a></li>
						<li>|</li>
						<li><a href="<c:url value='/sa/'/>#content">Basic view</a>&nbsp;]</li>
						<%
							if (request.getUserPrincipal() != null) {
						%>
						<li><label>Logged in as <b><%=request.getUserPrincipal().getName()%></b></label></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="<c:url value='/sa/'/>#content">Home</a></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="<%=userService.createLogoutURL("/")%>">Logout</a></li>
						<%
							} else {
								out.println("<li><a href=\""
										+ userService.createLoginURL("/sa/")
										+ "\"><img alt='Sign-in with Google' src='/css/images/sign-in-with-google.png'/></a></li>");
							}
						%>
					</ul>
				</div>
			</td>
		</tr>
	</table>
</div>