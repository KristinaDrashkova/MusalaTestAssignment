package com.musala.generala.service.iterator;

import com.musala.generala.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EmployeeIteratorFactoryFromFile implements IEmployeeIteratorFactory {
    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(EmployeeIteratorFactoryFromFile.class);
    private String path;
    private String applicationPropertiesFilePath;

    public EmployeeIteratorFactoryFromFile(String path, String appPropFilePath) {
        this.path = path;
        this.applicationPropertiesFilePath = appPropFilePath;
    }

    public Iterator<Employee> createEmployeeIterator() throws IOException {
        BufferedReader bufferedReader;
        Map<String, String> applicationPropertiesData;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            applicationPropertiesData = getDataFromApplicationPropertiesFile();
        } catch (IOException e) {
            LOGGER.error("There was problem loading the file: {}", path);
            throw e;
        }
        return new EmployeeIterator(bufferedReader, applicationPropertiesData);
    }


    private Map<String, String> getDataFromApplicationPropertiesFile() throws IOException {
        Map<String, String> applicationPropertiesData = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(applicationPropertiesFilePath));
            Properties properties = new Properties();
            properties.load(reader);
            reader.close();
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                applicationPropertiesData.put(key, value);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("There is no such file: {}", applicationPropertiesFilePath);
            throw e;
        } catch (IOException e) {
            LOGGER.error("There was problem loading the file: {}", applicationPropertiesFilePath);
            throw e;
        }

        return applicationPropertiesData;
    }
}
