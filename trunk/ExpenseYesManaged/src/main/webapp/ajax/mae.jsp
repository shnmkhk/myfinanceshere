<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Add an entry <c:if test="${pageScope.sheetKeyId}">for <c:out value="${pageScope.sheetKeyId}" /></c:if></title>
<body>
	<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
		<div>
			<ul class="horizontal-list" style="text-align: right; width: 100%;">
				<li class="header-one">Add entries to </li>
				<c:if test="${not empty sessionScope.SHEET_MONTH_YR_ARRAY}">
					<li class="header-two"><div style="padding: 0px 3px;"><c:out value="${sessionScope.SHEET_MONTH_YR_ARRAY[0]}" /></div></li>
					<li class="header-three"><c:out value="${sessionScope.SHEET_MONTH_YR_ARRAY[1]}" /></li>
				</c:if>
			</ul>
			<ul class="horizontal-list" style="text-align: left; width: 100%;">
				<li><a href="javascript:void(0);" onclick="showListEntriesPage()">Back to entries</a></li>
			</ul>
		</div>
	</c:if>
	<%@ include file="/common/header.jsp"%>
	<c:set var="noOfEntries" value="3" />
	<c:if test="${not empty param.noe}">
		<c:set var="noOfEntries" value="${param.noe}"/>
	</c:if>
	<input type="hidden" id="sid" name="sid" value="${param.sid}" />
	<input type="hidden" id="multi" name="form-type" value="multi"/>
	<input type="hidden" id="no-of-entries" name="no-of-entries" value="${noOfEntries}"/>
	<div style="width: 100%; text-align: right; ">
		<input type="submit" class="submit" name="submit" value="Add" onclick="addMultipleEntries();return false;"/> 
		<input type="submit" class="cancel" name="submit" value="Cancel" class="cancel" onclick="showListEntriesPage(); return false;"/>
	</div>
	<hr />
	<c:forEach var="index" begin="1" end="${noOfEntries}" step="1">
		<div>
			<fieldset data-role="controlgroup" data-type="horizontal" data-theme="b">
				<label style="float: left;">Type:&nbsp;&nbsp;</label>
		        
		        <input id="type_income_<c:out value='${index}'/>" name="type_<c:out value='${index}'/>" value="I" checked="checked" type="radio"  style="float: left;">
		        <label for="type_income_<c:out value='${index}'/>" style="float: left;">&nbsp;Income&nbsp;</label>
		        
		        <input id="type_expense_<c:out value='${index}'/>" name="type_<c:out value='${index}'/>" value="E" type="radio" style="float: left;">
		        <label for="type_expense_<c:out value='${index}'/>"  style="float: left;">&nbsp;Expense&nbsp;</label>
		        
	    	</fieldset>
			<span> 
				<input type="text" id="short_code_<c:out value='${index}'/>" name="shortCode_<c:out value='${index}'/>" maxlength="32" size="14" value="<c:out value='${sessionScope.INPUT_SHORT_CODE}'/>" placeholder="Eg. Grocery" alt="Label Eg. Grocery" title="Label Eg. Grocery"  class="entry_field"/>
				<input type="text" id="amount_<c:out value='${index}'/>" name="amount_<c:out value='${index}'/>" maxlength="32" size="10" value="<c:out value='${sessionScope.INPUT_AMOUNT}'/>" placeholder="Eg. 8000" alt="Amount Eg. 8000" title="Amount Eg. 8000"  class="entry_field"/>
				<div style="padding-top: 5px;">
					<select id="category_<c:out value='${index}'/>" name="category_<c:out value='${index}'/>" class="rounded-corners">
						<option value="Salary">Salary</option>
						<option value="Food">Food</option>
						<option value="Transport">Transport</option>
						<option value="Clothing">Clothing</option>
						<option value="Entertainment">Entertainment</option>
	
						<option value="House Rent">House Rent</option>
						<option value="Household Goods">Household Goods</option>
						<option value="Groceries" selected="selected">Groceries</option>
						<option value="Travel">Travel</option>
						<option value="Electricity">Electricity</option>
	
						<option value="Internet">Internet</option>
						<option value="Mobile Phone Recharges/ Bills">Mobile Phone Recharges/ Bills</option>
						<option value="Facilities">Facilities</option>
						<option value="House Renovation">House Renovation</option>
						<option value="Others/ Miscelaneous">Others/ Miscelaneous</option>
					</select>
				</div>
			</span>
		</div>
		<hr />
	</c:forEach>
	<span style="float: right">
		<input type="submit" class="submit" name="submit" value="Add" onclick="addMultipleEntries();return false;"/> 
		<input type="submit" class="cancel" name="submit" value="Cancel" class="cancel" onclick="showListEntriesPage(); return false;"/>
	</span>
	<ul class="horizontal-list" style="text-align: left;">
		<li><a href="javascript:void(0);" onclick="showListEntriesPage()">Back to entries</a></li>
	</ul>
</body>