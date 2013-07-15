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
						<%
							if (request.getUserPrincipal() != null) {
						%>
						<li><label>Logged in as <b><%=request.getUserPrincipal().getName()%></b></label></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="<%=userService.createLogoutURL("/")%>">Logout</a></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="/as.jsp#content"
							title="Navigate to 'Add a sheet' screen">Add a Sheet</a></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="/sa/#content" title="Navigate to 'Sheets list' screen">Sheets list</a></li>
						<c:if
							test="${fn:contains(pageContext.request.requestURI, '/le.jsp') || fn:contains(pageContext.request.requestURI, '/ae.jsp')}">
							<li>&nbsp;|&nbsp;</li>
							<li><a
								href="/ae.jsp?sid=<c:out value='${sessionScope.SHEET_KEY_ID}'/>#content"
								title="Navigate to 'Add an entry to the current sheet' screen">Add an Entry</a></li>
							<li>&nbsp;|&nbsp;</li>
							<li><a href="/ea/<c:out value="${sessionScope.SHEET_KEY_ID}"/>#content" title="Navigate to 'Entries list of current sheet' screen">Entries
									list</a></li>
						</c:if>
						<%
							} else {
								out.println("<li><a href=\""
										+ userService.createLoginURL("/sa/#content")
										+ "\"><img src='/css/images/sign-in-with-google.png'/></a></li>");
							}
						%>
					</ul>
				</div>
			</td>
		</tr>
	</table>
</div>