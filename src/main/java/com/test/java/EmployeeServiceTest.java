package com.test.java;

import com.musala.generala.models.Employee;
import com.musala.generala.repositories.EmployeeRepository;
import com.musala.generala.service.EmployeeService;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.test.java.PredefinedEmployeeTestSubjects.*;

public class EmployeeServiceTest {
    private static final String EMPLOYEE_INVALID_DATA_PATH =
            "src/main/java/com/test/resources/employee invalid data.txt";
    private static final String INVALID_PATH = "src/resources/employee invalid data.txt";
    private static final double DELTA = 1e-15;
    private EmployeeRepository mockedEmployeeRepository;
    private EmployeeService employeeService;

    @Before
    public void initialize() {
        this.mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
    }

    @Test
    public void parseShouldLogErrorMessageWithWrongInputFilePath() {
//        this.employeeService.parse(INVALID_PATH);
//        List<String> loggedMessages = getLoggedMessages();
//        String expectedMessage = "Could not find file: " + INVALID_PATH;
//        Assert.assertTrue(loggedMessages.contains(expectedMessage));
    }

    @Test
    public void parseShouldLogErrorMessagesWithWrongInput() {
//        this.employeeService.parse(EMPLOYEE_INVALID_DATA_PATH);
//        List<String> loggedMessages = getLoggedMessages();
//        for (String error : expectedLoggedErrors()) {
//            Assert.assertTrue(loggedMessages.contains(error));
//        }
    }

    // TODO: test successful parsing



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
        Assert.assertEquals("[a, b, f]"
                , employeeService.mostCommonCharactersInEmployeesNames().toString());
    }

    @Test
    public void fillEmployeeNamesInToMapShouldWorkCorrect(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(MAXIMILIAN, MAXIMILIAN, MAXIMILIAN));
        HashMap<Character, Integer> map = new HashMap<>();
        for (Employee employee : this.mockedEmployeeRepository.getEmployeeList()) {
            for (Character ch : employee.getName().toLowerCase().toCharArray()) {
                map.putIfAbsent(ch, 0);
                map.put(ch, map.get(ch) + 1);
            }
        }

        Assert.assertEquals(this.employeeService.fillEmployeeNamesInToMap(), map);
    }
}