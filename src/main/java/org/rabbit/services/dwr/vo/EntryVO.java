/**
 * 
 */
package org.rabbit.services.dwr.vo;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: Aug 3, 2013
 */
public class EntryVO extends BaseAbstractVO {

	private static final long serialVersionUID = -2292475062455022964L;

	private SheetVO parentSheetVO;

	private String formattedAmount;

	private String shortCode;

	private String description;
	
	private String seqIxStr;

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return formattedAmount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.formattedAmount = amount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the parentSheetVO
	 */
	public SheetVO getParentSheetVO() {
		return parentSheetVO;
	}

	/**
	 * @param parentSheetVO
	 *            the parentSheetVO to set
	 */
	public void setParentSheetVO(SheetVO parentSheetVO) {
		this.parentSheetVO = parentSheetVO;
	}

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * @param shortCode
	 *            the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	
	/**
	 * @return the seqIx
	 */
	public String getSeqIx() {
		return seqIxStr;
	}

	/**
	 * @param seqIx the seqIx to set
	 */
	public void setSeqIx(String seqIx) {
		this.seqIxStr = seqIx;
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.dwr.vo.BaseAbstractVO#getKeyStr()
	 */
	@Override
	public String getKeyStr() {
		StringBuffer sb = new StringBuffer();
		if (parentSheetVO == null){
			throw new RuntimeException("Parent sheet VO is not set for this entry");
		}
		
		return sb.append(parentSheetVO.getKeyStr()).append("/").append(seqIxStr).append("/").toString();
	}
}
