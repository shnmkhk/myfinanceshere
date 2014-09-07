<! DOCTYPE HTML>
<html>
<title>Add a sheet</title>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
</head>
<body>
	<div>
		<ul class="horizontal-list" style="text-align: right; width: 100%;">
			<li class="header-one">Add a</li>
			<li class="header-two"><div style="padding: 0px 3px;">sheet</div></li>
		</ul>
		<ul class="horizontal-list" style="text-align: left; width: 100%;">
			<li><a href="javascript:void(0)" onclick="showSheetListPage();return false;">Back to sheets</a></li>
		</ul>
	</div>

	<%@ include file="/common/header.jsp"%>
	<form method="POST" name="add-a-sheet-form" onsubmit="return false;">
		<hr />
		<div>
			<div>
				<span><strong>Month</strong></span>
			</div>
			<div>
				<span> <input type="text" id="month" name="month"
					maxlength="2" size="10" value="<c:out value='${INPUT_MONTH}'/>"
					placeholder="Eg. 12" alt="Month Eg. 12 for december"
					title="Month Eg. 12 for december" />
				</span>
			</div>
			<div>
				<span><strong>Year</strong></span>
			</div>
			<div>
				<span> <input type="text" id="year" name="year" maxlength="4"
					size="16" value="<c:out value='${INPUT_YEAR}'/>"
					placeholder="Eg. 2013" alt="Year Eg. 2013" title="Year Eg. 2013" />
				</span>
			</div>
		</div>
		<hr />
		<div>
			<span style="float: right;"> 
			<input type="submit" name="submit" value="Add" onclick="addSheet();return false;"/> 
			<input type="submit" name="submit" value="Cancel" class="cancel" onclick="showSheetListPage();return false;"/>
			</span>
		</div>
	</form>
	<ul class="horizontal-list" style="text-align: left;">
		<li><a href="javascript:void(0);" onclick="showSheetListPage();return false;">Back to sheets</a></li>
	</ul>
	<script type="text/javascript">
		function clearAddASheetInputs(){
			$("#month").val("");
			$("#year").val("");
		}
	</script>
</body>
</html>