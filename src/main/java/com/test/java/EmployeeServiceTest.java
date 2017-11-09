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

    private static final String ILLEGAL_EMPLOYEE_NAME_MESSAGE = "Illegal employee name:";
    private static final String ILLEGAL_EMPLOYEE_LENGTH_OF_SERVICE_MESSAGE = "Illegal employee length of service: -2.0";
    private static final String ILLEGAL_EMPLOYEE_AGE_MESSAGE = "Illegal employee age: -1";
    private static final String TEST_LOG_PATH = "c://Log/test log.log";
    private static final String COULD_NOT_FIND_FILE_MESSAGE = "Could not find file: ";
    private static final double DELTA = 1e-15;
    private static final String EMPLOYEE_INVALID_DATA_PATH =
            "src/main/java/com/test/resources/employee invalid data.txt";
    private static final String INVALID_PATH = "src/resources/employee invalid data.txt";
    private static final String CONFIG_FILENAME_PATH = "src/main/java/com/test/WEB-INF/lib/log4j.properties";
    private static final String TEST_NAME_ONE = "Aaaaaaf";
    private static final String TEST_NAME_TWO = "Aaaaaaf Bbbbbf";
    private static final String TEST_NAME_THREE = "Aaaaaaf Bbbbbf Cccccf";
    private static final int TEST_AGE_ONE = 10;
    private static final int TEST_AGE_TWO = 20;
    private static final int TEST_AGE_THREE = 30;
    private static final double TEST_LENGTH_OF_SERVICE_ONE = 10.1;
    private static final double TEST_LENGTH_OF_SERVICE_TWO = 20.2;
    private static final double TEST_LENGTH_OF_SERVICE_THREE = 30.3;
    private Employee employeeOne;
    private Employee employeeTwo;
    private Employee employeeThree;
    private Employee mockedEmployeeOne;
    private Employee mockedEmployeeTwo;
    private EmployeeRepository mockedEmployeeRepository;
    private EmployeeService employeeService;

    @Before
    public void initialize() {
        PropertyConfigurator.configure(CONFIG_FILENAME_PATH);
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
        String expectedMessage = COULD_NOT_FIND_FILE_MESSAGE + INVALID_PATH;
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
    public void averageAgeOfEmployeesShouldCalculateCorrectWithNormalInputData(){
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(20.0, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMaxValue() {
        mockRepositoryWithCornerCaseMaxData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Integer.MAX_VALUE, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMinValue() {
        mockRepositoryWithCornerCaseMinData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Integer.MIN_VALUE, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldCalculateCorrect() {
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(20.2, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldOverflowWithCornerMaxValues() {
        mockRepositoryWithCornerCaseMaxData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.POSITIVE_INFINITY, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldWorkCorrectWithCornerMinValues() {
        mockRepositoryWithCornerCaseMinData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.MIN_VALUE, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrect(){
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(30.3, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMaxValues(){
        mockRepositoryWithCornerCaseMaxData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.MAX_VALUE, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMinValues(){
        mockRepositoryWithMixedData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(30.3, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void mostCommonCharactersInEmployeesNamesShouldWorkCorrect() {
        mockRepositoryWithNormalData();
        this.employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals("[a, b, f]", employeeService.mostCommonCharactersInEmployeesNames().toString());
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

        Assert.assertEquals(employeeService.fillEmployeeNamesInToMap(), map);
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
        List<String> loggedMessages = new ArrayList<>();
        loggedMessages.add(ILLEGAL_EMPLOYEE_NAME_MESSAGE);
        loggedMessages.add(ILLEGAL_EMPLOYEE_LENGTH_OF_SERVICE_MESSAGE);
        loggedMessages.add(ILLEGAL_EMPLOYEE_AGE_MESSAGE);

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

    @After
    public void clearLog() throws IOException {
        PrintWriter pw = new PrintWriter(TEST_LOG_PATH);
        pw.print("");
        pw.close();
    }
}