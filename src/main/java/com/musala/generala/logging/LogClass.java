package main.java.com.musala.generala.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogClass {
    public static Logger LOGGER = LogManager.getRootLogger();

    public static void setup() {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }
}
