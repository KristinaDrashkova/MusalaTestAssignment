package com.test.java;

import com.musala.generala.service.iterator.EmployeeIteratorFactoryFromFile;
import com.musala.generala.service.EmployeeService;
import com.musala.generala.service.iterator.IEmployeeIteratorFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;
import org.mockito.Mockito;

import java.io.*;
import java.util.Iterator;

import static com.test.java.PredefinedEmployeeTestSubjects.*;

public class EmployeeServiceTest {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    private static final double DELTA = 1e-15;
    private EmployeeService employeeService;
    private Iterator mockedEmployeeIterator;

    @Before
    public void initialize() throws IOException {
        PropertyConfigurator.configure(CONFIG_FILENAME_TEST_PATH);
        this.mockedEmployeeIterator = Mockito.mock(Iterator.class);
        IEmployeeIteratorFactory mockedEmployeeIteratorFactory = Mockito.mock(EmployeeIteratorFactoryFromFile.class);
        this.employeeService = new EmployeeService(mockedEmployeeIteratorFactory);
        Mockito.when(mockedEmployeeIteratorFactory.getEmployeeIterator())
                .thenReturn(this.mockedEmployeeIterator);
        Mockito.when(this.mockedEmployeeIterator.hasNext())
                .thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
    }

    @Test
    public void averageAgeOfEmployeesShouldCalculateCorrectWithNormalInputData() throws IOException {
        Mockito.when(this.mockedEmployeeIterator.next()).thenReturn(NORA).thenReturn(NORMAN).thenReturn(NORBERT);
        Assert.assertEquals(20, this.employeeService.averageAgeOfEmployees(), DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMaxValue() throws IOException {
        Mockito.when(this.mockedEmployeeIterator.next())
                .thenReturn(MAXIMILIAN).thenReturn(MAXIMILIAN).thenReturn(MAXIMILIAN);
        Assert.assertEquals(Integer.MAX_VALUE,
                this.employeeService.averageAgeOfEmployees(), DELTA);
    }


    @Test
    public void averageLengthOfServiceOfEmployeesShouldCalculateCorrect() throws IOException {
        Mockito.when(this.mockedEmployeeIterator.next()).thenReturn(NORA).thenReturn(NORMAN).thenReturn(NORBERT);
        Assert.assertEquals(20.2,
                this.employeeService.averageLengthOfServiceOfEmployees(), DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldOverflowWithCornerMaxValues() throws IOException {
        Mockito.when(this.mockedEmployeeIterator.next())
                .thenReturn(MAXIMILIAN).thenReturn(MAXIMILIAN).thenReturn(MAXIMILIAN);
        Assert.assertEquals(Double.POSITIVE_INFINITY,
                this.employeeService.averageLengthOfServiceOfEmployees(), DELTA);
    }


    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrect() throws IOException {
        Mockito.when(this.mockedEmployeeIterator.next()).thenReturn(NORA).thenReturn(NORMAN).thenReturn(NORBERT);
        Assert.assertEquals(30.3,
                this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMaxValues() throws IOException {
        Mockito.when(this.mockedEmployeeIterator.next()).thenReturn(NORA).thenReturn(MAXIMILIAN).thenReturn(NORBERT);
        Assert.assertEquals(Double.MAX_VALUE,
                this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }


    @Test
    public void mostCommonCharactersInEmployeesNamesShouldWorkCorrect() throws IOException {
        Mockito.when(this.mockedEmployeeIterator.next()).thenReturn(NORA).thenReturn(NORMAN).thenReturn(NORBERT);
        Assert.assertArrayEquals(new Character[]{'a', 'b', 'f'},
                this.employeeService
                        .mostCommonCharactersInEmployeesNames(3).toArray(new Character[3]));
    }
}