package com.test.java;

import com.musala.generala.service.EmployeeIterator;
import org.junit.Assert;
import org.junit.Test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmployeeIteratorTest {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    private static final String EMPTY_FILE_PATH = "src/main/java/com/test/resources/empty file.txt";
    private static final String INVALID_PATH = "src/main/java/com/test/resources/empty  file.txt";


    @Test
    public void hasNextShouldReturnFalseWithEmptyCollection() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(EMPTY_FILE_PATH));
        EmployeeIterator employeeIterator = new EmployeeIterator(bufferedReader);
        Assert.assertEquals(false, employeeIterator.hasNext());
        bufferedReader.close();
    }

    @Test
    public void hasNextAndNextShouldWorkCorrectly() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(RESOURCES_EMPLOYEE_DATA_PATH));
        EmployeeIterator employeeIterator = new EmployeeIterator(bufferedReader);
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(false, employeeIterator.hasNext());
        bufferedReader.close();
    }

    @Test(expected = IOException.class)
    public void creatingIteratorWithInvalidPathShouldThrowException() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INVALID_PATH));
        EmployeeIterator employeeIterator = new EmployeeIterator(bufferedReader);
        bufferedReader.close();
    }
}
