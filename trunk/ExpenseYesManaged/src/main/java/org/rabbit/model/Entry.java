package org.rabbit.model;

import java.io.Serializable;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.rabbit.common.EntryCategory;
import org.rabbit.services.dwr.vo.BaseAbstractVO;
import org.rabbit.services.dwr.vo.EntryVO;
import org.rabbit.services.dwr.vo.SheetVO;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.TextUtil;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Entry extends BaseEntity implements Serializable {

	public static final String NEW_LINE = "\n";

	private static final long	serialVersionUID	= 1910736454574078429L;

	@Persistent
	private int					sequenceIndex;

	@Persistent
	private char				type;

	@Persistent
	private double				amount;

	@Persistent
	private String				shortCode;

	@Persistent
	private String				description;

	@Persistent
	private char				status;

	@ForeignKey
	private Sheet				sheet;

	@Persistent
	private String				category;

	public Entry(int sequenceIndex, char type, double amount, String shortCode, String description, char status, String category) {
		this.sequenceIndex = sequenceIndex;
		this.type = type;
		this.amount = amount;
		this.shortCode = shortCode;
		this.description = description;
		this.status = status;
		this.category = category;
	}

	public Entry(Key key, int sequenceIndex, char type, double amount, String shortCode, String description, char status, String category) {
		super();
		this.key = key;
		this.sequenceIndex = sequenceIndex;
		this.type = type;
		this.amount = amount;
		this.shortCode = shortCode;
		this.description = description;
		this.status = status;
		this.category = category;
	}

	/**
	 * The type of the transaction. Possible values are 'E' [Expense] or 'I'
	 * [Income]
	 * 
	 * @return the type
	 */
	public char getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(char type) {
		this.type = type;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return Math.abs(amount);
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return TextUtil.formatStr(shortCode);
	}

	/**
	 * @param shortCode
	 *            the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return TextUtil.formatStr(description);
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(char status) {
		this.status = status;
	}

	public int getSequenceIndex() {
		return sequenceIndex;
	}

	public void setSequenceIndex(int sequenceIndex) {
		this.sequenceIndex = sequenceIndex;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Entry [key=" + key + ", sequenceIndex=" + sequenceIndex + ", type=" + type + ", amount=" + amount + ", shortCode=" + shortCode + ", description=" + description + ", status=" + status + ", sheet=" + sheet + "]";
	}

	public String getStyleClass() {
		if (type == 'I') {
			return "style-class-income";
		} else {
			return "style-class-expense";
		}
	}

	public String getTypeStr() {
		final StringBuffer sb = new StringBuffer();
		sb.append("<span ");
		if (type == 'I') {
			sb.append("class=\"income-label\"");
		} else {
			sb.append("class=\"expense-label\"");
		}
		sb.append(">");
		sb.append("<span class=\"category\">").append(getCategory()).append("</span>");
		sb.append("&nbsp;");
		sb.append("<span class=\"entry-tiny-date\" style=\"font-size: 0.8em\">").append(ObjectUtils.getTinyDate(getCreatedOn())).append("</span>");
		return sb.toString();
	}

	public double getSignedAmount() {
		if (type == 'I') {
			return amount;
		} else {
			return (-1) * amount;
		}
	}

	public String getViewFormatLabel() {
		String sheetKeyStr = getSheet().getKeyStr();
		int sequenceNumber = getSequenceIndex();
		return getShortCode() + "&nbsp;<a href='/ea/" + sheetKeyStr + "/" + sequenceNumber + "/delete#content'>[Del]</a>&nbsp;<br/>" + getTypeStr();
	}

	public String getViewFormatAmount() {
		return TextUtil.nf.format(amount);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	private EntryVO	entryVO	= null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.model.BaseEntity#getVO()
	 */
	@Override
	public BaseAbstractVO getVO() {
		if (entryVO == null) {
			entryVO = new EntryVO();
		}

		entryVO.setAmount(TextUtil.nf.format(amount));
		entryVO.setDescription(description);
		entryVO.setParentSheetVO((SheetVO) sheet.getVO());
		entryVO.setSeqIx(String.valueOf(sequenceIndex));
		entryVO.setShortCode(shortCode);
		entryVO.setEntryCategory(category);

		return entryVO;
	}
	
	public String getEditDescriptionBlock() {
		final StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-md-4\">");
				sb.append("<fieldset data-role=\"controlgroup\" data-type=\"horizontal\" data-theme=\"b\">");
				sb.append("<label style=\"float: left\">Type:&nbsp;&nbsp;</label>");
				sb.append("<input style=\"float: left\" id=\"type_income_edit_"+sequenceIndex+"\" name=\"type_edit_"+sequenceIndex+"\" value=\"I\" type=\"radio\"  onclick=\"loadEntryCategories('category_edit_"+sequenceIndex+"', 'short_code_edit_"+sequenceIndex+"', this.value, 0);\"/>");
				sb.append("<label style=\"float: left\" for=\"type_income_edit_"+sequenceIndex+"\" >&nbsp;Income&nbsp;</label>");
				sb.append("<input style=\"float: left\" id=\"type_expense_edit_"+sequenceIndex+"\" name=\"type_edit_"+sequenceIndex+"\" value=\"E\" type=\"radio\"  checked=\"checked\" onclick=\"loadEntryCategories('category_edit_"+sequenceIndex+"', 'short_code_edit_"+sequenceIndex+"', this.value, 0);\"/>");
				sb.append("<label style=\"float: left\" for=\"type_expense_edit_"+sequenceIndex+"\" >&nbsp;Expense&nbsp;</label>");
				sb.append("</fieldset>");
			sb.append("</div>");
			
			sb.append("<div class=\"col-md-4\">");
				sb.append("<select id=\"category_edit_"+sequenceIndex+"\" name=\"category_edit_"+sequenceIndex+"\" class=\"edit-entry-select rounded-corners\" onchange=\"autoFillShortCode(this.value, 'short_code_edit_"+sequenceIndex+"')\"  style=\"background-color: #f5f5f5; color: #8b8989; height: 2.5em;\"></select>");
			sb.append("</div>");
			
			sb.append("<div class=\"col-md-4\">");
				sb.append("<input type=\"text\" id=\"short_code_edit_"+sequenceIndex+"\" name=\"shortCode_edit_"+sequenceIndex+"\" maxlength=\"32\" size=\"14\" value=\""+shortCode+"\" placeholder=\"Eg. Grocery\" alt=\"Label Eg. Grocery\" title=\"Label Eg. Grocery\"  class=\"entry_field\" style=\"background-color: #fffaf0; color: #000000; height: 2.5em; margin-right: 3px;\"/>");
				sb.append("<input type=\"text\" id=\"amount_edit_"+sequenceIndex+"\" name=\"amount_edit_"+sequenceIndex+"\" maxlength=\"32\" size=\"10\" value=\""+amount+"\" placeholder=\"Eg. 8000\" alt=\"Amount Eg. 8000\" title=\"Amount Eg. 8000\"  class=\"entry_field\" style=\"background-color: #fffaf0; color: #000000; height: 2.5em;\"/>");
			sb.append("</div>");
		sb.append("</div>");
		sb.append("<input type=\"hidden\" id=\"h_category_edit_"+sequenceIndex+"\" name=\"h_category_edit_"+sequenceIndex+"\" value=\""+category+"\"/>");
		sb.append("<input type=\"hidden\" id=\"h_type_edit_"+sequenceIndex+"\" name=\"h_type_edit_"+sequenceIndex+"\" value=\""+type+"\"/>");
		
		return sb.toString();
	}
}