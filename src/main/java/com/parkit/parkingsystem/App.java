package com.parkit.parkingsystem;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    static DataBaseConfig databaseConfig;
    public static void main(String args[]){
        logger.info("Initializing Parking System");
        logger.debug("Debug message");
        InteractiveShell.loadInterface();

        databaseConfig.executeSqlFromFile("schema_and_data.sql");
    }
}
