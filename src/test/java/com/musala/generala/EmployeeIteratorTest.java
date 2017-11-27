package com.musala.generala;

import com.musala.generala.models.Employee;
import com.musala.generala.service.iterator.EmployeeIteratorFactoryFromFile;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.Iterator;


public class EmployeeIteratorTest {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src\\main\\resources\\employee data.txt";
    private static final String EMPTY_FILE_PATH = "src\\test\\resources\\empty file.txt";

    @Test()
    public void hasNextShouldReturnFalseWithEmptyCollection() throws IOException {
        Iterator<Employee> employeeIterator = new EmployeeIteratorFactoryFromFile(EMPTY_FILE_PATH).createEmployeeIterator();
        Assert.assertEquals(false, employeeIterator.hasNext());
    }

    @Test
    public void hasNextAndNextShouldWorkCorrectly() throws IOException {
        Iterator<Employee> employeeIterator = new EmployeeIteratorFactoryFromFile(RESOURCES_EMPLOYEE_DATA_PATH).createEmployeeIterator();
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
}
