package com.test.java;

import com.musala.generala.models.Employee;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EmployeeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialize() {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.reportMissingExceptionWithMessage("Exception expected");
    }
    @Test
    public void createEmployeeWithInvalidNameShouldThrowException() {
        this.thrown.expectMessage("Illegal employee name: ");
        new Employee("", 21, 5);
    }

    @Test
    public void createEmployeeWithInvalidAgeShouldThrowException() {
        this.thrown.expectMessage("Illegal employee age: -1");
        new Employee("Kristopher", -1, 5);
    }

    @Test
    public void createEmployeeWithInvalidLengthOfServiceShouldThrowException() {
        this.thrown.expectMessage("Illegal employee length of service: -1");
        new Employee("Kristopher", 25, -1);
    }
}
