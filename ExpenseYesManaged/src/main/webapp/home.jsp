<! DOCTYPE HTML>
<html>
<title>Home</title>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.10.2.min.js'/>"></script>
<script type='text/javascript' src="<c:url value='/dwr/interface/SheetService.js'/>"></script>
<script type='text/javascript' src="<c:url value='/dwr/engine.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/sheet-util.js'/>"></script>

</head>
<body>
	<script type="text/javascript">
		var listSheetsLoaded = false;
		var addASheetLoaded = false;
		var listEntriesLoaded = false;
		var addAnEntryLoaded = false;

		function hideAll(){
			$("#list_sheets").hide();
			$("#add_a_sheet").hide();
			$("#list_entries").hide();
			$("#add_entries").hide();
		}
		
		function showSheetListPage(loadAgain){
			if (listSheetsLoaded) {
				hideAll();
				$("#list_sheets").show();
			} else {
				$("body").addClass("loading");
				$("#list_sheets").load("/ajax/sa/?ref=Home#content", function() {
					hideAll();
					$("#list_sheets").show();
					listSheetsLoaded = true;
					$("body").removeClass("loading");
				});
			}
		}

		function showAddASheetPage(){
			if (addASheetLoaded) {
				hideAll();
				$("#add_a_sheet").show();
				clearAddASheetInputs();
				return;
			} else {
				$("body").addClass("loading");
				$("#add_a_sheet").load("/ajax/as.jsp#content", function() {
					hideAll();
					$("#add_a_sheet").show();
					addASheetLoaded = true;
					$("body").removeClass("loading");
					clearAddASheetInputs();
				});
			}
		}

		function showListEntriesPage(urlToLoad){
			if (listEntriesLoaded) {
				hideAll();
				$("#list_entries").show();
				return;
			} else {
				$("body").addClass("loading");
				$("#list_entries").load(urlToLoad, function() {
					hideAll();
					$("#list_entries").show();
					listEntriesLoaded = true;
					$("body").removeClass("loading");
				});
			}
		}

		function showAddEntriesPage(){
			if (addAnEntryLoaded) {
				hideAll();
				$("#add_entries").show();
				return;
			} else {
				$("body").addClass("loading");
				$("#add_entries").load("/ajax/mae.jsp#content", function() {
					hideAll();
					$("#add_entries").show();
					addAnEntryLoaded = true;
					$("body").removeClass("loading");
				});
			}
		}
		
		function addSheet() {
			var givenMonth = $("#month").val();
			var givenYear = $("#year").val();
			SheetService.createNew(givenMonth, givenYear, function(result){
				listSheetsLoaded = false;
				showSheetListPage();
			});
		}
	</script>
	
	
	<div>
		<div id="list_sheets"></div>
		<div id="add_a_sheet"></div>
		<div id="list_entries"></div>
		<div id="add_entries"></div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			showSheetListPage();
		});
	</script>
</body>
</html>
