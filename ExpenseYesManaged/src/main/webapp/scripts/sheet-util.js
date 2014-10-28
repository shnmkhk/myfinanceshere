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

EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_INCOME, "Salary", "Salary"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_INCOME, "House Rent", "House Rent"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_INCOME, "Other/ Miscelaneous", "Other/ Miscelaneous"));

EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Food", "Food"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "House Rent", "House Rent"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Clothing", "Clothing"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Entertainment", "Entertainment"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Household Goods", "Household Goods"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Groceries", "Groceries"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Travel", "Travel"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Electricity", "Electricity"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Internet", "Internet"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Mobile Phone Recharges/ Bills", "Mobile Phone Recharges/ Bills"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Facilities", "Facilities"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "House Renovation", "House Renovation"));
EntryCategoryList.push(new EntryCategory(ENTRY_TYPE_EXPENSE, "Others/ Miscelaneous", "Others/ Miscelaneous"));

function loadEntryCategories (select_id, entry_type) {
	DWRUtil.removeAllOptions(select_id);
	var optionsArr = [];
	for (var entryCategory in EntryCategoryList) {
		if (entry_type === EntryCategoryList[entryCategory].type) {
			optionsArr.push(EntryCategoryList[entryCategory].text);
		}
	}
	DWRUtil.addOptions(select_id, optionsArr);
}