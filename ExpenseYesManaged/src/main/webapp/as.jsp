<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<title>Add a sheet</title>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<form action="/sa" method="POST">
		<table class="form-container">
			<tr>
				<td><label for="month">Month</label><br /> <input type="text"
					id="month" name="month" maxlength="2" size="2" value="<c:out value='${INPUT_MONTH}'/>" /></td>
			</tr>
			<tr>
				<td><label for="year">Year</label><br /> <input type="text"
					id="year" name="year" maxlength="4" size="4" value="<c:out value='${INPUT_YEAR}'/>"/></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="Add" /> <input
					type="submit" name="submit" value="Cancel" /></td>
			</tr>
		</table>
	</form>
</body>