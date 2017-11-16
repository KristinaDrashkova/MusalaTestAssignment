package com.musala.generala.repositories;

import com.musala.generala.models.Employee;
import com.musala.generala.service.EmployeeIterator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

    public void initializeIterator() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(RESOURCES_EMPLOYEE_DATA_PATH));
        this.employeeIterator = new EmployeeIterator(bufferedReader);
    }
}
