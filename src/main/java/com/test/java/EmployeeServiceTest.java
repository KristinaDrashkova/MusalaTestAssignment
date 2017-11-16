package com.test.java;

import com.musala.generala.repositories.EmployeeRepository;
import com.musala.generala.service.EmployeeService;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.*;
import java.util.*;

import static com.test.java.PredefinedEmployeeTestSubjects.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    private final static String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    private static final String INVALID_PATH = "src/resources/employee invalid data.txt";
    private static final double DELTA = 1e-15;
    private EmployeeRepository mockedEmployeeRepository;
    private EmployeeService employeeService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialize() {
        PropertyConfigurator.configure(CONFIG_FILENAME_TEST_PATH);
        this.mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
    }

    @Test
    public void parseShouldThrowExceptionWithInvalidInputPath() throws IOException {
        this.thrown.expectMessage("Could not find file " + INVALID_PATH);
        this.thrown.reportMissingExceptionWithMessage("Exception expected");
        employeeService.parse(INVALID_PATH);
    }

    @Test
    public void parseShouldWorkCorrectly() throws IOException {
        this.employeeService.parse(RESOURCES_EMPLOYEE_DATA_PATH);
        verify(this.mockedEmployeeRepository, times(4)).addEmployee(any());
    }

    @Test
    public void averageAgeOfEmployeesShouldCalculateCorrectWithNormalInputData(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(NORA , NORMAN, NORBERT));
        Assert.assertEquals(20, this.employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMaxValue() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(MAXIMILIAN, MAXIMILIAN, MAXIMILIAN));
        Assert.assertEquals(Integer.MAX_VALUE, this.employeeService.averageAgeOfEmployees(),DELTA);
    }


    @Test
    public void averageLengthOfServiceOfEmployeesShouldCalculateCorrect() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(NORA , NORMAN, NORBERT));
        Assert.assertEquals(20.2, this.employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldOverflowWithCornerMaxValues() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(MAXIMILIAN, MAXIMILIAN, MAXIMILIAN));
        Assert.assertEquals(Double.POSITIVE_INFINITY, this.employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }


    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrect(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(NORA , NORMAN, NORBERT));
        Assert.assertEquals(30.3, this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMaxValues(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(NORA , MAXIMILIAN, NORBERT));
        Assert.assertEquals(Double.MAX_VALUE, this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }


    @Test
    public void mostCommonCharactersInEmployeesNamesShouldWorkCorrect() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(NORA , NORMAN, NORBERT));
        Assert.assertArrayEquals(new Character[]{'a', 'b', 'f'},
                this.employeeService.mostCommonCharactersInEmployeesNames().toArray(new Character[3]));
    }

    @Test
    public void fillEmployeeNamesInToMapShouldWorkCorrect(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(MAXIMILIAN, MAXIMILIAN, MAXIMILIAN));
        HashMap<Character, Integer> charactersInNames = this.employeeService.countCharactersInEmployeeNames();
        Assert.assertTrue(charactersInNames.get(' ') == 6);
        Assert.assertTrue(charactersInNames.get('a') == 3);
        Assert.assertTrue(charactersInNames.get('b') == 3);
        Assert.assertTrue(charactersInNames.get('c') == 3);
        Assert.assertTrue(charactersInNames.size() == 4);
    }
}