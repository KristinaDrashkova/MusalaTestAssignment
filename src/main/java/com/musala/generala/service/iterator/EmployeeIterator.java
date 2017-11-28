package com.musala.generala.service.iterator;

import com.musala.generala.models.Employee;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

class EmployeeIterator implements Iterator<Employee> {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeIterator.class);
    private Employee cachedEmployee;
    private BufferedReader bufferedReader;
    private boolean isFinished = false;
    private String name;
    private int age;
    private double lengthOfService;


    EmployeeIterator(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public boolean hasNext() {
        if (this.cachedEmployee != null) {
            return true;
        } else if (this.isFinished) {
            return false;
        }
        parseEmployee();
        return !(this.isFinished && this.cachedEmployee == null);
    }


    @Override
    public Employee next() {
        Employee currentEmployee = null;
        if (hasNext()) {
            currentEmployee = this.cachedEmployee;
            this.cachedEmployee = null;
        }
        return currentEmployee;
    }

    private void parseEmployee() {
        try {
            String line;
            String employeeSeparator = getEmployeeSeparator();
            while ((line = this.bufferedReader.readLine()) != null && !line.trim().equals(employeeSeparator)) {
                parseLine(line);
            }
            try {
                if (isEmployee()) {
                    this.cachedEmployee = new Employee(this.name, this.age, this.lengthOfService);
                }
            } catch (IllegalArgumentException e) {
                LOGGER.error(e.getMessage());
            }
            if (line == null) {
                this.isFinished = true;
            }
        } catch (IOException ioe) {
            close();
        }
    }

    private void parseLine(String line) throws IOException {
        String fieldSeparator = getFieldSeparator();
        String[] lineData = line.split(fieldSeparator);
        String key = lineData[0];
        String value = lineData.length == 1 ? "" : lineData[1];
        switch (key) {
            case "name":
                this.name = value.trim();
                break;
            case "age":
                this.age = Integer.parseInt(value.trim());
                break;
            case "lengthOfService": {
                this.lengthOfService = Double.parseDouble(value.trim());
                break;
            }
        }
    }

    private boolean isEmployee() {
        return !(StringUtils.isBlank(this.name) && this.age == 0 && this.lengthOfService == 0.0);
    }

    private void close() {
        this.isFinished = true;
        this.cachedEmployee = null;
        IOUtils.closeQuietly(this.bufferedReader);
    }

    private String getEmployeeSeparator() throws IOException {
        Map<String, String> data = getDataFromSeparatorPropertiesFile();
        return data.get("employeeSeparator");
    }

    private String getFieldSeparator() throws IOException {
        Map<String, String> data = getDataFromSeparatorPropertiesFile();
        return data.get("fieldSeparator");
    }

    private Map<String, String> getDataFromSeparatorPropertiesFile() throws IOException {
        Map<String, String> separatorPropertiesData = new HashMap<>();
        String path = "src/main/resources/separators.properties";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Properties properties = new Properties();
            properties.load(reader);
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                separatorPropertiesData.put(key, value);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("There is no such file: {}", path);
            throw e;
        } catch (IOException e) {
            LOGGER.error("There was problem loading the file: {}", path);
            throw e;
        }

        return separatorPropertiesData;
    }
}
