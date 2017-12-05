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
    private String fieldSeparator = "=";
    private String employeeSeparator = "<<>>";
    private Map<String, String> applicationPropertiesData;
    private String nameLabel = "name";
    private String ageLabel = "age";
    private String lengthOfServiceLabel = "lengthOfService";

    EmployeeIterator(BufferedReader bufferedReader, Map<String, String> applicationPropertiesData) {
        this.bufferedReader = bufferedReader;
        this.applicationPropertiesData = applicationPropertiesData;
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
            try {
                this.employeeSeparator = getEmployeeSeparator();
            } catch (IOException e) {}
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

    private void parseLine(String line) {
        try {
            this.fieldSeparator = getFieldSeparator();
            this.nameLabel = getNameLabel();
            this.ageLabel = getAgeLabel();
            this.lengthOfServiceLabel = getLengthOfServiceLabel();
        } catch (IOException e) {}
        String[] lineData = line.split(fieldSeparator);
        String key = lineData[0];
        String value = lineData.length == 1 ? "" : lineData[1].trim();
        if (key.equals(this.nameLabel)) {
            this.name = value;
        } else if (key.equals(this.ageLabel)) {
            this.age = Integer.parseInt(value);
        } else if (key.equals(this.lengthOfServiceLabel)) {
            this.lengthOfService = Double.parseDouble(value);
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
        return this.applicationPropertiesData.get("employeeSeparator");
    }

    private String getFieldSeparator() throws IOException {
        return this.applicationPropertiesData.get("fieldSeparator");
    }

    private String getNameLabel() {
        return this.applicationPropertiesData.get("nameLabel");
    }

    private String getAgeLabel() {
        return this.applicationPropertiesData.get("ageLabel");
    }

    private String getLengthOfServiceLabel() {
        return this.applicationPropertiesData.get("lengthOfServiceLabel");
    }
}
