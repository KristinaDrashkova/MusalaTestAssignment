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

public class EmployeeServiceTest {
    private static final String TEST_LOG_PATH = "c://Log/test log.log";
    private static final String EMPLOYEE_INVALID_DATA_PATH =
            "src/main/java/com/test/resources/employee invalid data.txt";
    private static final String INVALID_PATH = "src/resources/employee invalid data.txt";
    private static final String TEST_CONFIG_FILENAME_PATH = "src/main/java/com/test/WEB-INF/lib/log4j.properties";
    private static final double DELTA = 1e-15;
    private EmployeeRepository mockedEmployeeRepository;
    private EmployeeService employeeService;
    private Employee norman;
    private Employee nora;
    private Employee norbert;
    private Employee maximilian;

    @Before
    public void initialize() {
        PropertyConfigurator.configure(TEST_CONFIG_FILENAME_PATH);
        // nora, norman and norbert are employees with normal parameters
        this.norman = new Employee("Aaaaaaf", 10, 10.1);
        this.nora = new Employee("Aaaaaaf Bbbbbf", 20, 20.2);
        this.norbert = new Employee("Aaaaaaf Bbbbbf Cccccf", 30, 30.3);
        //maximilian is an employee with MAX value for age and length of service
        this.maximilian = new Employee("A B C", Integer.MAX_VALUE, Double.MAX_VALUE);
        this.mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
    }

    @Test
    public void parseShouldLogErrorMessageWithWrongInputFilePath() {
        this.employeeService.parse(INVALID_PATH);
        List<String> loggedMessages = getLoggedMessages();
        String expectedMessage = "Could not find file: " + INVALID_PATH;
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
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.nora, this.norman, this.norbert));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        this.employeeService.getEmployeeInfo();
        List<String> loggedMessages = getLoggedMessages();
        for (String message : getExpectedLoggedMessages()) {
            Assert.assertTrue(loggedMessages.contains(message));
        }
    }

    @Test
    public void averageAgeOfEmployeesShouldCalculateCorrectWithNormalInputData(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.nora, this.norman, this.norbert));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(20, this.employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMaxValue() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.maximilian, this.maximilian, this.maximilian));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Integer.MAX_VALUE, this.employeeService.averageAgeOfEmployees(),DELTA);
    }


    @Test
    public void averageLengthOfServiceOfEmployeesShouldCalculateCorrect() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.nora, this.norman, this.norbert));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(20.2, this.employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldOverflowWithCornerMaxValues() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.maximilian, this.maximilian, this.maximilian));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.POSITIVE_INFINITY, this.employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }


    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrect(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.nora, this.norman, this.norbert));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(30.3, this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMaxValues(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.maximilian, this.maximilian, this.maximilian));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.MAX_VALUE, this.employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }


    @Test
    public void mostCommonCharactersInEmployeesNamesShouldWorkCorrect() {
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.nora, this.norman, this.norbert));
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals("[a, b, f]"
                , employeeService.mostCommonCharactersInEmployeesNames().toString());
    }

    @Test
    public void fillEmployeeNamesInToMapShouldWorkCorrect(){
        Mockito.when(this.mockedEmployeeRepository.getEmployeeList())
                .thenReturn(Arrays.asList(this.maximilian, this.maximilian, this.maximilian));
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

    private List<String> expectedLoggedErrors() {
        List<String> loggedMessages = Arrays.asList("Illegal employee name:"
                , "Illegal employee length of service: -2.0", "Illegal employee age: -1");
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
        loggedMessages.add("Average age of employees: " +
                this.employeeService.averageAgeOfEmployees());
        loggedMessages.add("First three most common characters: "
                + this.employeeService.mostCommonCharactersInEmployeesNames().toString());
        loggedMessages.add("Average length of service of the employees: "
                + this.employeeService.averageLengthOfServiceOfEmployees());
        loggedMessages.add("Maximum length of service among all employees: "
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