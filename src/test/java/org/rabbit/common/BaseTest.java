package org.rabbit.common;

import org.junit.After;
import org.junit.Before;
import org.rabbit.shared.DataStoreJDOInitializer;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class BaseTest {

	private static LocalServiceTestHelper helper = null;
	
	static {
		helper = new LocalServiceTestHelper(
				new LocalDatastoreServiceTestConfig());
		DataStoreJDOInitializer.initialize();
	}
	
	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

}
