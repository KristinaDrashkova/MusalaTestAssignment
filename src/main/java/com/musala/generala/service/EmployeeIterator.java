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
            return isParse();
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

    private boolean isParse() {
        try {
            String line;
            String name = "";
            int age = 0;
            double lengthOfService = 0.0;
            while ((line = this.bufferedReader.readLine()) != null && !line.trim().equals("<<>>")) {
                String[] lineData = line.split(Pattern.quote("="));
                if (line.trim().equals("<<>>")) {continue;}
                String key = lineData[0];
                String value = lineData.length == 1? "" : lineData[1];
                switch (key) {
                    case "name" :
                        name = value.trim();
                        break;
                    case "age" :
                        age = Integer.parseInt(value.trim());
                        break;
                    case "lengthOfService" : {
                        lengthOfService = Double.parseDouble(value.trim());
                        try {
                            this.cachedEmployee = new Employee(name, age, lengthOfService);
                            return true;
                        } catch (IllegalArgumentException e) {
                            break;
                        }
                    }
                }
            }
            if (line == null) {
                this.isFinished = true;
                return false;
            }
        } catch(IOException ioe) {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new IllegalStateException(ioe.toString());
        }
        return true;
    }

    private void close() throws IOException {
        this.isFinished = true;
        this.cachedEmployee = null;
        this.bufferedReader.close();
    }
}
