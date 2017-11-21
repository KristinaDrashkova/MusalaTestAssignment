package com.test.java;

import com.musala.generala.service.EmployeeIteratorFactory;
import org.junit.Test;
import java.io.IOException;


public class EmployeeIteratorFactoryTest {
    private static final String INVALID_PATH = "src\\main\\java\\com\\test\\resources\\invalid file path.txt";

    @Test(expected = IOException.class)
    public void getEmployeeIteratorShouldThrowExceptionWithInvalidPath() throws Exception {
        EmployeeIteratorFactory employeeIteratorFactory = new EmployeeIteratorFactory();
        employeeIteratorFactory.getEmployeeIterator(INVALID_PATH);
    }
}