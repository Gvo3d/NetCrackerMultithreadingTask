package org.yakimovdenis.logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingConfiguration {
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger LOGGER = Logger.getLogger("confLogger");

    public static void configure() {
        try {
            logManager.readConfiguration(new FileInputStream("src/main/resources/logging.properties"));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error in loading configuration", exception);
        }
        LOGGER.log(Level.INFO, "Logger initialized!");
    }
}