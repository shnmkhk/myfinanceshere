package org.rabbit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rabbit.dao.EntryDAOImplUTest;
import org.rabbit.dao.SheetDAOImplUTest;
import org.rabbit.dao.TransactionDAOImplUTest;
import org.rabbit.services.SheetServiceImplTest;

@RunWith(Suite.class)
@SuiteClasses({ SheetDAOImplUTest.class, EntryDAOImplUTest.class,
	TransactionDAOImplUTest.class, SheetServiceImplTest.class })
public class DAOTestSuite {

	// Do nothing, annotations take care of picking the test case classes and execute testcases.
}