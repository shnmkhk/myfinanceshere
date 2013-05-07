package org.rabbit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SheetDAOImplUTest.class, EntryDAOImplUTest.class,
		TransactionDAOImplUTest.class })
public class DAOTestSuite {

	// Do nothing, annotations take care of picking the test case classes and execute testcases.
}