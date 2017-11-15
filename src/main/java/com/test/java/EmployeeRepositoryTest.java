package com.test.java;

import com.musala.generala.repositories.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;


import static com.test.java.PredefinedEmployeeTestSubjects.NORMAN;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EmployeeRepositoryTest {
    private EmployeeRepository employeeRepository = new EmployeeRepository();



    @Test(expected = UnsupportedOperationException.class)
    public void getEmployeeListShouldReturnUnmodifiableList() {
        this.employeeRepository.getEmployeeList().add(NORMAN);
    }

    @Test
    public void addEmployeeListShouldAddEmployeeInList() {
        EmployeeRepository spy = spy(new EmployeeRepository());
        spy.addEmployee(NORMAN);
        verify(spy, times(1)).addEmployee(any());
        Assert.assertEquals(NORMAN, spy.getEmployeeList().get(0));
        Assert.assertEquals(1, spy.getEmployeeList().size());
    }
}