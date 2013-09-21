<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<title>Add a sheet</title>
<body>
	<div>
		<ul class="horizontal-list" style="text-align: right; width: 100%;">
			<li class="header-one">Add a </li><li class="header-two">sheet</li>
		</ul>
		<ul class="horizontal-list" style="text-align: left; width: 100%;">
			<li><a href="<c:url value='/sa/#content'/>">Back to sheets</a></li>
		</ul>
	</div>
	
	<%@ include file="/common/header.jsp" %>
	<form action="/sa/#content" method="POST">
		<p>
			<span>
				<input type="text" id="month" name="month" maxlength="2" size="10" value="<c:out value='${INPUT_MONTH}'/>" placeholder="Month Eg. 12" alt="Month Eg. 12" title="Month Eg. 12"/>
			</span>
			<br/>
			<br/>
			<span>
				<input type="text" id="year" name="year" maxlength="4" size="16" value="<c:out value='${INPUT_YEAR}'/>" placeholder="Year Eg. 2013"  alt="Year Eg. 2013" title="Year Eg. 2013"/>
			</span>
			<hr/>
			<span style="float: right;">
				<input type="submit" name="submit" value="Add" />
				<input type="submit" name="submit" value="Cancel" />
			</span>
		</p>
		<%-- <table class="form-container">
			<tr>
				<td></td>
			</tr>
			<tr>
				<td><label for="year" class="required-field">Year *</label><br /> <input type="text"
					id="year" name="year" maxlength="4" size="8" value="<c:out value='${INPUT_YEAR}'/>" placeholder="Eg. 2013"/></td>
			</tr>
			<tr>
				<td><hr/></td>
			</tr>
			<tr>
				<td style="text-align: right;"><input type="submit" name="submit" value="Add" /> <input
					type="submit" name="submit" value="Cancel" /></td>
			</tr>
		</table> --%>
	</form>
	<ul class="horizontal-list" style="text-align: left;">
		<li><a href="<c:url value='/sa/#content'/>">Back to sheets</a></li>
	</ul>
</body>