package com.musala.generala.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EmployeeIteratorFactory {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";


    public EmployeeIterator getEmployeeIterator() throws IOException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(RESOURCES_EMPLOYEE_DATA_PATH));
        } catch (FileNotFoundException e) {
            throw new IOException("Could not find file " + RESOURCES_EMPLOYEE_DATA_PATH +
                    "\nOriginal exception message: " + e.getMessage());
        }
        return new EmployeeIterator(bufferedReader);
    }
}
