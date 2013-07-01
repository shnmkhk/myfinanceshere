<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<title>Add an entry <c:if test="${pageScope.sheetKeyId}">for <c:out value="${pageScope.sheetKeyId}"/></c:if></title>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<form action="/ea/#content" method="POST">
		<input type="hidden" name="sid" value="${param.sid}"/>
		<table class="form-container">
			<tr>
				<td><label>Type</label><br />
					<span>
						<input type="radio" name="type" id="type_income" value="I"/>
						<label for="type_income">Income</label>
					</span> 
					<span>
						<input type="radio" name="type" id="type_expense" value="E" checked="checked"/>
						<label for="type_expense">Expense</label>
					</span> 
				</td>
			</tr>
			<tr>
				<td><label for="shortCode">Label</label><br /> <input type="text"
					id="shortCode" name="shortCode" maxlength="32" size="10" value="<c:out value='${sessionScope.INPUT_SHORT_CODE}'/>" /></td>
			</tr>
			<tr>
				<td><label for="amount">Amount</label><br /> <input type="text"
					id="amount" name="amount" maxlength="32" size="10" value="<c:out value='${sessionScope.INPUT_AMOUNT}'/>" /></td>
			</tr>
			<tr>
				<td><label for="description">Describe</label><br /> <textarea id="description" name="description" rows="4" cols="11"><c:out value='${sessionScope.INPUT_DESCRIPTION}'/></textarea></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="Add" /> <input
					type="submit" name="submit" value="Cancel" /></td>
			</tr>
		</table>
	</form>
</body>