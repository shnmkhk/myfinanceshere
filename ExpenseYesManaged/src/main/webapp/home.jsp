<title>Home</title>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
</head>
<body>
	<%@ include file="common/header.jsp"%>
	<script type="text/javascript">
		var listSheetsLoaded = false;
		var addASheetLoaded = false;
		var listEntriesLoaded = false;
		var addAnEntryLoaded = false;

		function hideAll() {
			$("#list_sheets").hide();
			$("#add_a_sheet").hide();
			$("#list_entries").hide();
			$("#add_entries").hide();
			$("#error_message").hide();
			$("#status_message").hide();
		}
		
		function failureHandler(data) {
			$("body").removeClass("loading");
			alert("Failed to fetch data, please check your network connection");
		}

		function showSheetListPage(loadAgain) {
			if (listSheetsLoaded) {
				hideAll();
				$("#list_sheets").show();
				$("body").removeClass("loading");
			} else {
				$.ajax({
						url:"/ajax/sa/?ref=Home#content",
						timeout: 15000,
						success: function(data) {
							hideAll();
							$("#list_sheets").html(data);
							listSheetsLoaded = true;
							$("body").removeClass("loading");
							$("#list_sheets").show();
						},
						timeout: failureHandler
				});
			}
		}

		function showAddASheetPage() {
			if (addASheetLoaded) {
				hideAll();
				$("#add_a_sheet").show();
				clearAddASheetInputs();
				return;
			} else {
				$("body").addClass("loading");
				$.ajax({
					url: "/ajax/as.jsp#content", 
					timeout: 15000,
					success: function(data) {
						hideAll();
						$("#add_a_sheet").html(data);
						$("#add_a_sheet").show();
						addASheetLoaded = true;
						$("body").removeClass("loading");
						clearAddASheetInputs();
					},
					timeout: failureHandler
				});
			}
		}

		function postProcessingEntriesListing() {
			hideEntryEl();
			var iterated = false;
			$( ".edit-entry-select").each(function() {
				var ix = $(this).attr('id').match(/\d+/)[0];
				loadEntryCategories($(this).attr("id"), ix, $("#h_type_edit_" + ix).val(), 5);
				$('input[name=type_edit_' + ix +'][value=' + $("#h_type_edit_" + ix).val() + ']').attr('checked', 'checked');
				$('#category_edit_' + ix).val($("#h_category_edit_" + ix).val());
				iterated = true;
			});
			if (iterated) {
				displayListEntries();
			}
		}
		function showListEntriesPage(urlToLoad, successMsg) {
			if (listEntriesLoaded && !urlToLoad) {
				hideAll();
				$("#list_entries").show();
				return;
			} else {
				loadedPrevSheetURI = urlToLoad;
				if (successMsg) {
					$("#info_message").html("<p><span><a href=\"javascript:void()\" onclick=\"$('#info_message').remove()\">X</a></span></p>" + successMsg);
				}
				$("body").addClass("loading");
				$.ajax({
					url: urlToLoad, 
					timeout: 15000,
					success: function(data) {
						hideAll();
						$("#list_entries").html(data);
						$("#list_entries").show();
						listEntriesLoaded = true;
						$("body").removeClass("loading");
						$("span.pagelinks a").attr('data-ajax', 'false');
						$("table#entryRow a").attr('data-ajax', 'false');
						postProcessingEntriesListing();
					},
					timeout: failureHandler
				});
			}
		}

		function resetAddEntriesFields() {
			$("div#add_entries .entry_field").val("");
			$("div#add_entries .entry_field_checked").prop("checked", true);
		}

		function showAddEntriesPage(loadAgain) {
			if (addAnEntryLoaded && !loadAgain) {
				hideAll();
				resetAddEntriesFields();
				$("#add_entries").show();
				return;
			} else {
				$("body").addClass("loading");
				$.ajax({
					url: "/ajax/mae.jsp#content",
					timeout: 15000,
					success: function(data) {
						hideAll();
						resetAddEntriesFields();
						$("#add_entries").html(data);
						$("#add_entries").show();
						addAnEntryLoaded = true;
						$("body").removeClass("loading");
						loadEntryCategories('category_1', 'short_code_1', 'E', 5);
						loadEntryCategories('category_2', 'short_code_2', 'E', 5);
						loadEntryCategories('category_3', 'short_code_3', 'E', 5);
					},
					timeout: failureHandler
				});
			}
		}

		function processResponse(response) {
			var noIssues = eval(response.errored) == true ? false : true;
			if (noIssues) {
				$("#error_message_content").html("");
				$("#error_message").hide();
			} else {
				$("#error_message").show();
				$("#error_message_content").html(response.errorMessage);
			}
			return noIssues;
		}

		function addSheet() {
			var givenMonth = $("#month").val();
			var givenYear = $("#year").val();
			$("body").addClass("loading");
			SheetService.createNew(givenMonth, givenYear, function(response) {
				listSheetsLoaded = false;
				$("body").removeClass("loading");
				var noIssues = processResponse(response);
				if (noIssues) {
					if (showSheetListPage != undefined) showSheetListPage();
				}
			});
		}

		var ENTRY_PROP_AMOUNT = "amount";
		var ENTRY_PROP_SHORTCODE = "shortCode";
		var ENTRY_PROP_DESCR = "descr";
		var ENTRY_PROP_TYPE = "type";

		function addMultipleEntries() {
			var noOfEntries = eval($("#no-of-entries").val());
			var entriesArr = "{\"entries\":[";
			for (var i = 1; i <= noOfEntries; i++) {
				var incomeSelected = ($("#type_income_" + i).prop('checked') == true);
				var expenseSelected = ($("#type_expense_" + i).prop('checked') == true);

				var derivedType = incomeSelected ? 'I' : 'E';
				var shortCode = $("#short_code_" + i).val();
				var amount = $("#amount_" + i).val();
				var category = $("#category_" + i).val();

				var entry = new Entry(derivedType, shortCode, shortCode, amount, category);
				entriesArr += entry.toString();
				if (i != noOfEntries) {
					entriesArr += ",";
				}
			}
			entriesArr += "]}";

			$("body").addClass("loading");
			EntryService.addMultipleEntries(entriesArr, function(response) {
				$("body").removeClass("loading");
				var noIssues = processResponse(response);
				if (noIssues) {
					showListEntriesPage(loadedPrevSheetURI);
				}
			});
		}

		function deleteEntry(arg1, arg2, arg3, arg4, sheetKeyStr, sequenceIndex) {
			var r = confirm("Are you sure you want to delete this entry ?\n\n\t" + arg1 + "\n\t" + arg2 + "\n\t" + arg3 + "\n\t" + arg4);
			if (r == true) {
				var entriesArr = "{\"sheet_key_str\":\"" + sheetKeyStr
				+ "\", \"entries\":[";
				var toDeleteEntry = new ToDeleteEntry(sheetKeyStr, sequenceIndex);
				entriesArr += toDeleteEntry.toString();
				entriesArr += "]}";
		
				$("body").addClass("loading");
				EntryService.deleteSelectedEntries(entriesArr, function(response) {
					$("body").removeClass("loading");
					var noIssues = processResponse(response);
					if (noIssues) {
						showListEntriesPage(loadedPrevSheetURI);
					}
				});
			} 
		}
		
		function hideEntryEl () {
			$(".edit-entries-el").hide();
			$(".list-entries-el").hide();
		}
		function displayEditEntries() {
			$("#display-readonly-list").hide();
			$("#display-editable-list").show();
			
			hideEntryEl();
			$(".list-entries-el").show();
		}
		
		function displayListEntries() {
			$("#display-readonly-list").show();
			$("#display-editable-list").hide();
			
			hideEntryEl();
			$(".edit-entries-el").show();
		} 
	</script>


	<div>
		<div id="list_sheets">
			<b>Loading available sheets....</b>
		</div>
		<div id="add_a_sheet"></div>
		<div id="list_entries"></div>
		<div id="add_entries"></div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("body").addClass("loading");
			if (showSheetListPage) {
				showSheetListPage();
				loadEntryCategoryOptions();
			}
		});
	</script>	
</body>