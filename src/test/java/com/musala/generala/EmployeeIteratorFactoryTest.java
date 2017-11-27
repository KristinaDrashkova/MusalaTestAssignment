package com.musala.generala;

import com.musala.generala.service.iterator.EmployeeIteratorFactoryFromFile;
import com.musala.generala.service.iterator.IEmployeeIteratorFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;


public class EmployeeIteratorFactoryTest {
    private static final String INVALID_PATH = "src\\main\\java\\com\\test\\resources\\invalid file path.txt";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IOException.class)
    public void getEmployeeIteratorShouldThrowExceptionWithInvalidPath() throws Exception {
        IEmployeeIteratorFactory employeeIteratorFactory = new EmployeeIteratorFactoryFromFile(INVALID_PATH);
        employeeIteratorFactory.createEmployeeIterator();
        this.thrown.expect(IOException.class);
        this.thrown.reportMissingExceptionWithMessage("Exception expected");
        this.thrown.expectMessage("Could not find file " + INVALID_PATH +
                ", Original exception message: " + INVALID_PATH + " (The system cannot find the file specified)");
    }
}