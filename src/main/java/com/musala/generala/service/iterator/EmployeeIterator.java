package com.musala.generala.service.iterator;

import com.musala.generala.models.Employee;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

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
            while ((line = this.bufferedReader.readLine()) != null && !line.trim().equals("<<>>")) {
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
        String[] lineData = line.split("=");
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
}
