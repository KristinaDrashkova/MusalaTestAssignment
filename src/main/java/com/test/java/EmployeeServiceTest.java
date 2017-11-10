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
import static com.musala.generala.constants.Constants.*;

public class EmployeeServiceTest {
    private Employee employeeOne;
    private Employee employeeTwo;
    private Employee employeeThree;
    private Employee mockedEmployeeOne;
    private Employee mockedEmployeeTwo;
    private EmployeeRepository mockedEmployeeRepository;
    private EmployeeService employeeService;

    @Before
    public void initialize() {
        PropertyConfigurator.configure(TEST_CONFIG_FILENAME_PATH);
        this.employeeOne = new Employee(TEST_NAME_ONE, TEST_AGE_ONE, TEST_LENGTH_OF_SERVICE_ONE);
        this.employeeTwo = new Employee(TEST_NAME_TWO, TEST_AGE_TWO, TEST_LENGTH_OF_SERVICE_TWO);
        this.employeeThree = new Employee(TEST_NAME_THREE, TEST_AGE_THREE, TEST_LENGTH_OF_SERVICE_THREE);
        this.mockedEmployeeOne = Mockito.mock(Employee.class);
        Mockito.when(this.mockedEmployeeOne.getAge()).thenReturn(Integer.MAX_VALUE);
        Mockito.when(this.mockedEmployeeOne.getLengthOfService()).thenReturn(Double.MAX_VALUE);
        this.mockedEmployeeTwo = Mockito.mock(Employee.class);
        Mockito.when(this.mockedEmployeeTwo.getAge()).thenReturn(Integer.MIN_VALUE);
        Mockito.when(this.mockedEmployeeTwo.getLengthOfService()).thenReturn(Double.MIN_VALUE);
        this.mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
    }

    @Test
    public void parseShouldLogErrorMessageWithWrongInputFilePath() {
        this.employeeService.parse(INVALID_PATH);
        List<String> loggedMessages = getLoggedMessages();
        String expectedMessage = COULD_NOT_FIND_FILE_EXPECTED_MESSAGE + INVALID_PATH;
        Assert.assertTrue(loggedMessages.contains(expectedMessage));
    }

    @Test
    public void parseShouldLogErrorMessagesWithWrongInput() {
        this.employeeService.parse(EMPLOYEE_INVALID_DATA_PATH);
        List<String> loggedMessages = getLoggedMessages();
        for (String error : expectedLoggedErrors()) {
            Assert.assertTrue(loggedMessages.contains(error));
        }
    }


    @Test
    public void getEmployeeInfoShouldLogValidInformation() {
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        this.employeeService.getEmployeeInfo();
        List<String> loggedMessages = getLoggedMessages();
        for (String message : getExpectedLoggedMessages()) {
            Assert.assertTrue(loggedMessages.contains(message));
        }
    }

    @Test
    public void averageAgeOfEmployeesShouldCalculateCorrectWithNormalInputData(){
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(TEST_AGE_TWO, this.employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMaxValue() {
        mockRepositoryWithCornerCaseMaxData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Integer.MAX_VALUE, this.employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMinValue() {
        mockRepositoryWithCornerCaseMinData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Integer.MIN_VALUE, this.employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldCalculateCorrect() {
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(TEST_LENGTH_OF_SERVICE_TWO, this.employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldOverflowWithCornerMaxValues() {
        mockRepositoryWithCornerCaseMaxData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.POSITIVE_INFINITY, this.employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldWorkCorrectWithCornerMinValues() {
        mockRepositoryWithCornerCaseMinData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.MIN_VALUE, this.employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrect(){
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(TEST_LENGTH_OF_SERVICE_THREE, this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMaxValues(){
        mockRepositoryWithCornerCaseMaxData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.MAX_VALUE, this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMinValues(){
        mockRepositoryWithMixedData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(TEST_LENGTH_OF_SERVICE_THREE, this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void mostCommonCharactersInEmployeesNamesShouldWorkCorrect() {
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(EXPECTED_COMMON_CHARACTERS_TO_STRING
                , employeeService.mostCommonCharactersInEmployeesNames().toString());
    }

    @Test
    public void fillEmployeeNamesInToMapShouldWorkCorrect(){
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        HashMap<Character, Integer> map = new HashMap<>();
        for (Employee employee : this.mockedEmployeeRepository.getEmployeeList()) {
            for (Character ch : employee.getName().toLowerCase().toCharArray()) {
                map.putIfAbsent(ch, 0);
                map.put(ch, map.get(ch) + 1);
            }
        }

        Assert.assertEquals(this.employeeService.fillEmployeeNamesInToMap(), map);
    }


    private void mockRepositoryWithNormalData() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.employeeOne, this.employeeTwo, this.employeeThree));
    }

    private void mockRepositoryWithCornerCaseMaxData() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.mockedEmployeeOne, this.mockedEmployeeOne));
    }

    private void mockRepositoryWithCornerCaseMinData() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.mockedEmployeeTwo, this.mockedEmployeeTwo));
    }

    private void mockRepositoryWithMixedData() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.mockedEmployeeTwo, this.employeeOne, this.employeeThree));
    }


    private List<String> expectedLoggedErrors() {
        List<String> loggedMessages = Arrays.asList(ILLEGAL_EMPLOYEE_NAME_EXPECTED_MESSAGE
                , ILLEGAL_EMPLOYEE_LENGTH_OF_SERVICE_EXPECTED_MESSAGE, ILLEGAL_EMPLOYEE_AGE_EXPECTED_MESSAGE);
        return loggedMessages;
    }


    private List<String> getLoggedMessages() {
        List<String> loggedMessages = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(TEST_LOG_PATH))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String message = currentLine.split(" - ")[1].trim();
                loggedMessages.add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loggedMessages;
    }

    private List<String> getExpectedLoggedMessages() {
        List<String> loggedMessages = new ArrayList<>();
        loggedMessages.add(AVERAGE_AGE_OF_EMPLOYEES_MESSAGE
                .substring(0, AVERAGE_AGE_OF_EMPLOYEES_MESSAGE.length() - 2) +
                this.employeeService.averageAgeOfEmployees());
        loggedMessages.add(FIRST_THREE_MOST_COMMON_CHARACTERS_MESSAGE
                .substring(0, FIRST_THREE_MOST_COMMON_CHARACTERS_MESSAGE.length() - 2)
                + this.employeeService.mostCommonCharactersInEmployeesNames().toString());
        loggedMessages.add(AVERAGE_LENGTH_OF_SERVICE_OF_THE_EMPLOYEES_MESSAGE
                .substring(0, AVERAGE_LENGTH_OF_SERVICE_OF_THE_EMPLOYEES_MESSAGE.length() - 2)
                + this.employeeService.averageLengthOfServiceOfEmployees());
        loggedMessages.add(MAXIMUM_LENGTH_OF_SERVICE_AMONG_ALL_EMPLOYEES_MESSAGE
                .substring(0, MAXIMUM_LENGTH_OF_SERVICE_AMONG_ALL_EMPLOYEES_MESSAGE.length() - 2)
                + this.employeeService.maximumLengthOfServiceOfEmployee());

        return loggedMessages;
    }


    @After
    public void clearLog() throws IOException {
        PrintWriter pw = new PrintWriter(TEST_LOG_PATH);
        pw.print("");
        pw.close();
    }
}