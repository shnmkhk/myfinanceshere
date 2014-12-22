function loadSheets(interfaceId, removeMsg) {
	if (removeMsg) {
		$(".message").remove();
	}

	$("body").addClass("loading");
	SheetService.getAllSheetMapHTML(interfaceId, {
		callback : function(response) {
			var sheetListContainer = $("#sheet-list-container");
			if (sheetListContainer[0]) {
				sheetListContainer.html(response.responseAsString);
				var currentLoc = location.href;
				if (currentLoc.indexOf("#content") == -1) {
					location.href = currentLoc + "#content";
				}
			}
			$("body").removeClass("loading");
		},
		timeout : 10000
	})
}

// Default interface is Plain
var currentInterfaceId = 0;
var loadedPrevSheetURI = ""; 

try {
	// a crossbrowser solution
	$(document).ready(function() {
		dwr.engine.setErrorHandler(function(message) {
			$("body").removeClass("loading");
		});
		loadSheets(currentInterfaceId);
	});
} catch (error) {
}


var ENTRY_PROP_AMOUNT = "amount";
var ENTRY_PROP_SHORTCODE = "shortCode";
var ENTRY_PROP_DESCR = "descr";
var ENTRY_PROP_TYPE = "type";
var ENTRY_PROP_CATEGORY = "category";

function Entry(type, descr, shortCode, amount, category) {
	  this.type = type;
	  this.descr = descr;
	  this.shortCode = shortCode;
	  this.amount = amount;
	  this.category = category;
}

Entry.prototype.toString = function()
{
  return "{" +
  		"\""+ENTRY_PROP_TYPE+"\":\""+this.type+"\"," +
  		"\""+ENTRY_PROP_SHORTCODE+"\":\""+this.shortCode+"\"," +
  		"\""+ENTRY_PROP_DESCR+"\":\""+this.descr+"\"," +
  		"\""+ENTRY_PROP_AMOUNT+"\":\""+this.amount+"\"," +
  		"\""+ENTRY_PROP_CATEGORY+"\":\""+this.category+"\"" +
  		"}";
};

var ENTRY_PROP_SHEET_KEY_STR = "sheet_key_str";
var ENTRY_PROP_SEQUENCE_IX = "seq_ix";


function ToDeleteEntry(sheetKeyStr, sequenceIx) {
	  this.sheetKeyStr = sheetKeyStr;
	  this.sequenceIx = sequenceIx;
}

ToDeleteEntry.prototype.toString = function()
{
  return "{" +
  		"\""+ENTRY_PROP_SHEET_KEY_STR+"\":\""+this.sheetKeyStr+"\"," +
  		"\""+ENTRY_PROP_SEQUENCE_IX+"\":\""+this.sequenceIx+"\"}";
};

function showLoader(linkObj, msg) {
	msg = (msg == undefined || msg == null) ? "" : msg;
	linkObj.innerHTML = "Loading" + msg + "..";
}

var ENTRY_TYPE_INCOME = 'I';
var ENTRY_TYPE_EXPENSE = 'E';

function EntryCategory (type, text, value) {
	this.type = type;
	this.text = text;
	this.value = value;	
}

EntryCategory.prototype.isIncome = function () {
	if (this.type == ENTRY_TYPE_INCOME) {
		return true;
	} 
	return false;
}

EntryCategory.prototype.isExpense = function () {
	if (this.type == ENTRY_TYPE_EXPENSE) {
		return true;
	} 
	return false;
}


var EntryCategoryList = [];

var INCOME_CATEGORY_ARR = [ 'Salary', 'House Rent', 'Other Sources' ];
var EXPENSE_CATEGORY_ARR = [ 'Food', 'House Rent', 'Clothing', 'Entertainment',
		'Household Goods', 'Groceries', 'Commuting', 'Electricity', 'Internet',
		'Prepaids/ Bills', 'Facilities', 'House Renovation',
		'Medicines', 'Newspaper Subscr..', 'Cable/ DTH ', 'Vegetables',
		'Eggs/ Meat', 'Travel/ Emergency', 'Others/ Misc..' ];

var INCOME_CATEGORY_OBJECTS = [];
var EXPENSE_CATEGORY_OBJECTS = [];
function loadEntryCategoryOptions() 
{
	if (INCOME_CATEGORY_OBJECTS.length == 0) {
		for (var index in INCOME_CATEGORY_ARR) {
			INCOME_CATEGORY_OBJECTS.push(new EntryCategory(ENTRY_TYPE_INCOME, INCOME_CATEGORY_ARR[index], INCOME_CATEGORY_ARR[index]));		
		}
	}
	if (EXPENSE_CATEGORY_OBJECTS.length == 0) {
		for (var index in EXPENSE_CATEGORY_ARR) {
			EXPENSE_CATEGORY_OBJECTS.push(new EntryCategory(ENTRY_TYPE_EXPENSE, EXPENSE_CATEGORY_ARR[index], EXPENSE_CATEGORY_ARR[index]));		
		}	
	}
}

function loadEntryCategories (select_id, short_code_id, entry_type, default_check_ix) {
	// var select_id = 'category_' + select_index;
	DWRUtil.removeAllOptions(select_id);
	if (entry_type === ENTRY_TYPE_INCOME) {
		DWRUtil.addOptions(select_id, INCOME_CATEGORY_ARR);
		autoFillShortCode(INCOME_CATEGORY_ARR[default_check_ix], short_code_id);
		DWRUtil.setValue(select_id, INCOME_CATEGORY_ARR[default_check_ix]);
	} else if (entry_type === ENTRY_TYPE_EXPENSE) {
		DWRUtil.addOptions(select_id, EXPENSE_CATEGORY_ARR);
		autoFillShortCode(EXPENSE_CATEGORY_ARR[default_check_ix], short_code_id);
		DWRUtil.setValue(select_id, EXPENSE_CATEGORY_ARR[default_check_ix]);
	} 
}

function contains (text, arr) {
	if (Object.prototype.toString.call( arr ) === '[object Array]') {
		for (var i in arr) {
			if (text === arr[i]) {
				return true;
			}
		}
	}
	
	return false;
}

function autoFillShortCode(selectedCategory, shortCodeId) {
	console.log("shortCodeId: " + shortCodeId)
	var availableShortCode = $("#"+shortCodeId).val();
	if (availableShortCode === "" || contains (availableShortCode, INCOME_CATEGORY_ARR) || 
			contains (availableShortCode, EXPENSE_CATEGORY_ARR)) {
		$("#"+shortCodeId).val(selectedCategory);
	}
}