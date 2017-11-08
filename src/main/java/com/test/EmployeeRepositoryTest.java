package com.test;

import com.musala.generala.models.Employee;
import com.musala.generala.repositories.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;

public class EmployeeRepositoryTest {
    private final static String FIELD_NAME = "employeeList";
    private EmployeeRepository employeeRepository;
    private Employee mockedEmployeeOne;


    @Before
    public void initialize(){
        this.employeeRepository = new EmployeeRepository();
        this.mockedEmployeeOne = Mockito.mock(Employee.class);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void getEmployeeListShouldReturnUnmodifiableList() {
        this.employeeRepository.getEmployeeList().add(this.mockedEmployeeOne);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void addEmployeeListShouldAddEmployeeInList() throws NoSuchFieldException, IllegalAccessException {
        this.employeeRepository.addEmployee(this.mockedEmployeeOne);
        Field listField = this.employeeRepository.getClass().getDeclaredField(FIELD_NAME);
        listField.setAccessible(true);
        List<Employee> employees = (List<Employee>) listField.get(this.employeeRepository);
        Employee employee = employees.get(employees.size() - 1);
        Assert.assertEquals(this.mockedEmployeeOne, employee);
    }

}