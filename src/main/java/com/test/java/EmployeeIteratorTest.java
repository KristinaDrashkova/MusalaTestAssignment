package com.test.java;

import com.musala.generala.models.Employee;
import com.musala.generala.service.iterator.EmployeeIteratorFactoryFromFile;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

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
        Iterator<Employee> employeeIterator = new EmployeeIteratorFactoryFromFile(EMPTY_FILE_PATH).getEmployeeIterator();
        Assert.assertEquals(false, employeeIterator.hasNext());
    }

    @Test
    public void hasNextAndNextShouldWorkCorrectly() throws IOException {
        Iterator<Employee> employeeIterator = new EmployeeIteratorFactoryFromFile(RESOURCES_EMPLOYEE_DATA_PATH).getEmployeeIterator();
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
