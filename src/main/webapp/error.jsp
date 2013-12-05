<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true"%>
<%exception.printStackTrace(); %>
<title>Internal system error</title>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<h3>Unexpected error occurred, however it is recorded by the
		system</h3>
	<table width="100%">
		<tr valign="top">
			<td width="40%"><b>Error:</b></td>
			<td>${pageContext.exception}</td>
		</tr>
		<tr valign="top">
			<td><b>URI:</b></td>
			<td>${pageContext.errorData.requestURI}</td>
		</tr>
		<tr valign="top">
			<td><b>Status code:</b></td>
			<td>${pageContext.errorData.statusCode}</td>
		</tr>
	</table>
</body>