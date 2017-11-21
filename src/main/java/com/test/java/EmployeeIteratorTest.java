package com.test.java;

import com.musala.generala.service.EmployeeIterator;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.test.java.PredefinedEmployeeTestSubjects.CONFIG_FILENAME_TEST_PATH;

public class EmployeeIteratorTest {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    private static final String EMPTY_FILE_PATH = "src/main/java/com/test/resources/empty file.txt";


    @Before
    public void initialize() {
        PropertyConfigurator.configure(CONFIG_FILENAME_TEST_PATH);
    }

    @Test()
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
}
