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
public class TransactionVO extends BaseAbstractVO {

	private static final long	serialVersionUID	= 2861258207084591504L;

	private String				openingBalStr;

	private String				description;

	private String				transAmountStr;

	private String				sequenceIndexStr;

	private EntryVO				entryVO;

	/**
	 * @return the openingBalStr
	 */
	public String getOpeningBalStr() {
		return openingBalStr;
	}

	/**
	 * @param openingBalStr
	 *            the openingBalStr to set
	 */
	public void setOpeningBalStr(String openingBalStr) {
		this.openingBalStr = openingBalStr;
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
	 * @return the transAmountStr
	 */
	public String getTransAmountStr() {
		return transAmountStr;
	}

	/**
	 * @param transAmountStr
	 *            the transAmountStr to set
	 */
	public void setTransAmountStr(String transAmountStr) {
		this.transAmountStr = transAmountStr;
	}

	/**
	 * @return the sequenceIndexStr
	 */
	public String getSequenceIndexStr() {
		return sequenceIndexStr;
	}

	/**
	 * @param sequenceIndexStr
	 *            the sequenceIndexStr to set
	 */
	public void setSequenceIndexStr(String sequenceIndexStr) {
		this.sequenceIndexStr = sequenceIndexStr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.dwr.vo.BaseAbstractVO#getKeyStr()
	 */
	/**
	 * @return the entryVO
	 */
	public EntryVO getEntryVO() {
		return entryVO;
	}

	/**
	 * @param entryVO
	 *            the entryVO to set
	 */
	public void setEntryVO(EntryVO entryVO) {
		this.entryVO = entryVO;
	}

	@Override
	public String getKeyStr() {
		return entryVO.getKeyStr() + "/" + sequenceIndexStr;
	}
}
