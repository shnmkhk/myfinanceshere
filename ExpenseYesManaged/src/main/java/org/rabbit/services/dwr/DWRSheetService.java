/**
 * 
 */
package org.rabbit.services.dwr;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.services.dwr.vo.SheetResponseWrapper;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: Aug 3, 2013
 */
public interface DWRSheetService {

	/**
	 * @param month
	 * @param year
	 * @throws SheetNotFoundException
	 */
	SheetResponseWrapper deleteSheet(int month, int year) throws SheetNotFoundException;

	/**
	 * @param sheetKeyStr
	 * @return
	 * @throws SheetNotFoundException
	 */
	SheetResponseWrapper getSheet(String sheetKeyStr) throws SheetNotFoundException;

	/**
	 * @param month
	 * @param year
	 * @return
	 * @throws SheetNotFoundException
	 */
	SheetResponseWrapper getSheet(int month, int year) throws SheetNotFoundException;

	/**
	 * @return
	 */
	SheetResponseWrapper getAllSheetsMap();

	/**
	 * @return
	 */
	SheetResponseWrapper fetchAll();

	/**
	 * @param month
	 * @param year
	 * @return
	 * @throws SheetAlreadyExistsException
	 */
	SheetResponseWrapper createNew(int month, int year) throws SheetAlreadyExistsException;

	/**
	 * Gets all the sheet map in HTML format straight away.
	 * 
	 * @return
	 */
	SheetResponseWrapper getAllSheetMapHTML(int interfaceId);
}
