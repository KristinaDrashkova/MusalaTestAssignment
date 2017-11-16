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
        parse();
        if (this.cachedEmployee != null) {
            return true;
        } else if (this.isFinished) {
            return false;
        }
        return false;
    }

    @Override
    public Employee next() {
        return nextEmployee();
    }

    private Employee nextEmployee() {
        Employee currentEmployee = this.cachedEmployee;
        this.cachedEmployee = null;
        return currentEmployee;
    }

    private void parse() {
        try {
            String line;
            String name = "";
            int age = 0;
            double lengthOfService = 0.0;
            while ((line = this.bufferedReader.readLine()) != null && !line.trim().equals("<<>>")) {
                String[] lineData = line.split(Pattern.quote("="));
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
                        break;
                    }
                }
            }
            try {
                if (!name.equals("") || age != 0 || lengthOfService != 0) {
                    this.cachedEmployee = new Employee(name, age, lengthOfService);
                }
            } catch (IllegalArgumentException ignored) {
            }
            if (line == null) {
                this.isFinished = true;
            }
        } catch(IOException ioe) {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new IllegalStateException(ioe.toString());
        }
    }

    private void close() throws IOException {
        this.isFinished = true;
        this.cachedEmployee = null;
        this.bufferedReader.close();
    }
}
