package org.rabbit.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.rabbit.shared.DataStoreJDOInitializer;

public class InitDataStoreListener implements ServletContextListener {

             @Override
             public void contextDestroyed(ServletContextEvent arg0) {

             }

             @Override
             public void contextInitialized(ServletContextEvent arg0) {
                             DataStoreJDOInitializer.initialize();
             }

}
