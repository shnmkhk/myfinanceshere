<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<title>Add a sheet</title>
<body>
	<div>
		<ul class="horizontal-list" style="padding: 2px; text-align: right;">
			<li class="header-one">Add a </li><li class="header-two">sheet</li>
		</ul>
		<ul class="horizontal-list" style="text-align: left;">
			<li><a href="<c:url value='/sa/#content'/>">Back to sheets</a></li>
		</ul>
	</div>
	
	<jsp:include page="/common/header.jsp"></jsp:include>
	<form action="/sa/#content" method="POST">
		<table class="form-container">
			<tr>
				<td><label for="month" class="required-field">Month *</label><br /> <input type="text"
					id="month" name="month" maxlength="2" size="2" value="<c:out value='${INPUT_MONTH}'/>" /></td>
			</tr>
			<tr>
				<td><label for="year" class="required-field">Year *</label><br /> <input type="text"
					id="year" name="year" maxlength="4" size="4" value="<c:out value='${INPUT_YEAR}'/>"/></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="Add" /> <input
					type="submit" name="submit" value="Cancel" /></td>
			</tr>
		</table>
	</form>
	<ul class="horizontal-list" style="float: left;">
		<li><a href="<c:url value='/sa/#content'/>">Back to sheets</a></li>
	</ul>
</body>