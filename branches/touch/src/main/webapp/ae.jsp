<! DOCTYPE HTML>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Add an entry <c:if test="${pageScope.sheetKeyId}">for <c:out
			value="${pageScope.sheetKeyId}" />
	</c:if></title>
<head>
</head>
<body>
	<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
		<div>
			<ul class="horizontal-list" style="text-align: right; width: 100%;">
				<li class="header-one">Add an entry</li>
				<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
					<li class="header-two"><c:out
							value="${sessionScope.SHEET_MONTH_YR_ARRAY[0]}" /></li>
					<li class="header-three"><c:out
							value="${sessionScope.SHEET_MONTH_YR_ARRAY[1]}" /></li>
				</c:if>
			</ul>
			<ul class="horizontal-list" style="text-align: left; width: 100%;">
				<li><a href="<c:url value='/ea/#content'/>">Back to entries</a></li>
			</ul>
		</div>
	</c:if>
	<%@ include file="/common/header.jsp"%>
	<form action="/ea/#content" method="POST">
		<input type="hidden" name="sid" value="${param.sid}" />
		<table class="form-container">
			<tr>
				<td><label class="required-field">Type *</label><br /> <span>
						<input type="radio" name="type" id="type_income" value="I" /> <label
						for="type_income">Income</label>
				</span> <span> <input type="radio" name="type" id="type_expense"
						value="E" checked="checked" /> <label for="type_expense">Expense</label>
				</span></td>
			</tr>
			<tr>
				<td><label for="shortCode" class="required-field">Label
						*</label><br /> <input type="text" id="shortCode" name="shortCode"
					maxlength="32" size="10"
					value="<c:out value='${sessionScope.INPUT_SHORT_CODE}'/>" /></td>
			</tr>
			<tr>
				<td><label for="amount" class="required-field">Amount *</label><br />
					<input type="text" id="amount" name="amount" maxlength="32"
					size="10" value="<c:out value='${sessionScope.INPUT_AMOUNT}'/>" /></td>
			</tr>
			<tr>
				<td><label for="description" class="optional-field">Describe</label><br />
					<textarea id="description" name="description" rows="4" cols="11"><c:out
							value='${sessionScope.INPUT_DESCRIPTION}' /></textarea></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="Add" /> <input
					type="submit" name="submit" value="Cancel" /></td>
			</tr>
		</table>
		<ul class="horizontal-list" style="text-align: left;">
			<li><a href="<c:url value='/ea/#content'/>">Back to entries</a></li>
		</ul>
	</form>
</body>
</html>