<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:if test="${not empty param.sid}">
	<c:set var="sheetKeyId" value="${param.sid}" scope="session"/>
</c:if>
<c:if test="${empty param.sid && not empty sessionScope.SHEET_KEY_ID}">
	<c:set var="sheetKeyId" value="${sessionScope.SHEET_KEY_ID}" scope="session"/>
</c:if>
<div class="body">
	<table class="form-background">
		<tr>
			<td>
				<div>
					Sheet Key: <c:out value="${sheetKeyId}"/>
					<ul class="horizontal-list">
						<li><label>shanmukha.k@gmail.com</label></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="/">Logout</a></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="/as.jsp#content">Add a sheet</a></li>
						<li>&nbsp;|&nbsp;</li>
						<li><a href="/sa/#content">Sheet Listing</a></li>
						<c:if test="${fn:contains(pageContext.request.requestURI, '/le.jsp') || fn:contains(pageContext.request.requestURI, '/ae.jsp')}">
							<li>&nbsp;|&nbsp;</li>
							<li><a href="/ae.jsp?sid=<c:out value='${sheetKeyId}'/>#content">Add an entry</a></li>
							<li>&nbsp;|&nbsp;</li>
							<li><a
								href="/ea/<c:out value="${sheetKeyId}"/>#content">Entry
									Listing</a></li>
						</c:if>
					</ul>
				</div>
			</td>
		</tr>
	</table>
</div>