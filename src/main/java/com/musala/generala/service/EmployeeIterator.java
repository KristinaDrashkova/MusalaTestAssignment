package com.musala.generala.service;

import com.musala.generala.models.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class EmployeeIterator implements Iterator<Employee> {
    private final BufferedReader bufferedReader;
    private Employee cachedEmployee;
    private boolean isFinished = false;
    private String name;
    private int age;
    private double lengthOfService;


    public EmployeeIterator(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public boolean hasNext() {
        if (this.cachedEmployee != null) {
            return true;
        } else if (this.isFinished) {
            return false;
        } else {
            try {
                while (true) {
                    String line = this.bufferedReader.readLine();
                    if (line == null) {
                        this.isFinished = true;
                        return false;
                    }
                    String[] lineData = line.split(Pattern.quote("="));
                    if (line.trim().equals("<<>>")) {continue;}
                    String key = lineData[0];
                    String value = lineData.length == 1? "" : lineData[1];
                    switch (key) {
                        case "name" :
                            this.name = value.trim();
                            break;
                        case "age" :
                            this.age = Integer.parseInt(value.trim());
                            break;
                        case "lengthOfService" : {
                            this.lengthOfService = Double.parseDouble(value.trim());
                            try {
                                this.cachedEmployee = new Employee(this.name, this.age, this.lengthOfService);
                                return true;
                            } catch (IllegalArgumentException e) {
                                return false;
                            } finally {
                                this.name = "";
                                this.age = -1;
                                this.lengthOfService = -1;
                            }
                        }
                    }
                }
            } catch(IOException ioe) {
                close();
                throw new IllegalStateException(ioe.toString());
            }
        }
    }

    @Override
    public Employee next() {
        return nextEmployee();
    }

    private Employee nextEmployee() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more employees");
        }
        Employee currentEmployee = this.cachedEmployee;
        this.cachedEmployee = null;
        return currentEmployee;
    }

    private void close() {
        this.isFinished = true;
        this.cachedEmployee = null;
    }
}
