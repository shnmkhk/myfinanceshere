/**
 * 
 */
package org.rabbit.services.dwr;

import java.util.List;
import java.util.Map;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.services.dwr.vo.SheetVO;


/**
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: Aug 3, 2013
 */
public interface DWRSheetService {

	/**
	 * @param userId
	 * @param month
	 * @param year
	 * @throws SheetNotFoundException
	 */
	void deleteSheet(String userId, int month, int year)
			throws SheetNotFoundException;

	/**
	 * @param userId
	 * @param sheetKeyStr
	 * @return
	 * @throws SheetNotFoundException
	 */
	SheetVO getSheet(String userId, String sheetKeyStr)
			throws SheetNotFoundException;

	/**
	 * @param userId
	 * @param month
	 * @param year
	 * @return
	 * @throws SheetNotFoundException
	 */
	SheetVO getSheet(String userId, int month, int year)
			throws SheetNotFoundException;

	/**
	 * @param userId
	 * @return
	 */
	Map<String, List<SheetVO>> getAllSheetsMap(String userId);

	/**
	 * @param userId
	 * @return
	 */
	List<SheetVO> fetchAll(String userId);

	/**
	 * @param userId
	 * @param month
	 * @param year
	 * @return
	 * @throws SheetAlreadyExistsException
	 */
	SheetVO createNew(String userId, int month, int year)
			throws SheetAlreadyExistsException;

	/**
	 * Gets all the sheet map in HTML format straight
	 * away.
	 * @return
	 */
	String getAllSheetMapHTML();
}
