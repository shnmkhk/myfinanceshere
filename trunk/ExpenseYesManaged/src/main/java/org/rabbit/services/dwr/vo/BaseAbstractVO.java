/**
 * 
 */
package org.rabbit.services.dwr.vo;

import java.io.Serializable;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: Aug 3, 2013
 */
public abstract class BaseAbstractVO implements Serializable {

	private static final long	serialVersionUID	= -1655997694845126364L;

	/**
	 * Gets the key string that is needed as URL query string
	 * 
	 * @return
	 */
	public abstract String getKeyStr();
}
