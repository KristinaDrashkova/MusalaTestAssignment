package com.musala.generala.repositories;

import com.musala.generala.service.EmployeeIterator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class that holds record of all Employee
 * and gives access to add Employee
 * and retrieves all Employee as list
 */

public class EmployeeRepository{
    private final static String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    private EmployeeIterator employeeIterator;

    public EmployeeIterator getEmployeeIterator() {
        return this.employeeIterator;
    }

    public void initializeIterator() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(RESOURCES_EMPLOYEE_DATA_PATH));
            this.employeeIterator = new EmployeeIterator(bufferedReader);
        } catch (FileNotFoundException e) {
            throw new IOException("Could not find file " + RESOURCES_EMPLOYEE_DATA_PATH +
                    "\nOriginal exception message: " + e.getMessage());
        }
    }
}
