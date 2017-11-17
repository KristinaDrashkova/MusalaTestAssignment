package com.musala.generala.service;

import java.io.IOException;

public class EmployeeIteratorFactory {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    public EmployeeIterator getEmployeeIterator() throws IOException {
        return new EmployeeIterator(RESOURCES_EMPLOYEE_DATA_PATH);
    }
}
