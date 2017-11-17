package com.test.java;

import com.musala.generala.service.EmployeeIterator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class EmployeeIteratorTest {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    private static final String EMPTY_FILE_PATH = "src/main/java/com/test/resources/empty file.txt";
    private static final String INVALID_PATH = "src/main/java/com/test/resources/empty  file.txt";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void hasNextShouldReturnFalseWithEmptyCollection() throws IOException {
        EmployeeIterator employeeIterator = new EmployeeIterator(EMPTY_FILE_PATH);
        Assert.assertEquals(false, employeeIterator.hasNext());
    }

    @Test
    public void hasNextAndNextShouldWorkCorrectly() throws IOException {
        EmployeeIterator employeeIterator = new EmployeeIterator(RESOURCES_EMPLOYEE_DATA_PATH);
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(true, employeeIterator.hasNext());
        employeeIterator.next();
        Assert.assertEquals(false, employeeIterator.hasNext());
    }

    @Test
    public void creatingIteratorWithInvalidPathShouldThrowException() throws IOException {
        this.thrown.expectMessage("Could not find file " + INVALID_PATH);
        this.thrown.reportMissingExceptionWithMessage("Exception expected");
        EmployeeIterator employeeIterator = new EmployeeIterator(INVALID_PATH);
    }
}
