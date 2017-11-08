package test.java;

import com.musala.generala.models.Employee;
import com.musala.generala.repositories.EmployeeRepository;
import com.musala.generala.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

public class EmployeeServiceTest {

    private Employee mockedEmployeeOne;
    private Employee mockedEmployeeTwo;
    private Employee mockedEmployeeThree;
    private EmployeeRepository employeeRepository;
    private static final double DELTA = 1e-15;

    @Before
    public void initialize() {

        this.mockedEmployeeOne = Mockito.mock(Employee.class);
        this.mockedEmployeeTwo = Mockito.mock(Employee.class);
        this.mockedEmployeeThree = Mockito.mock(Employee.class);
        this.employeeRepository = new EmployeeRepository();
    }
    @Test
    public void parse() throws Exception {
    }

    @Test
    public void averageAgeOfEmployeesShouldCalculateCorrectWithNormalInputData(){
        mockEmployeeGetAge(10, 20 ,30);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(20.0, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMaxValue() {
        mockEmployeeGetAge(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(Integer.MAX_VALUE, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMinValue() {
        mockEmployeeGetAge(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(Integer.MIN_VALUE, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldCalculateCorrect() {
        mockEmployeeGetLengthOfService(10.1, 20.2, 30.3);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(20.2, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldOverflowWithCornerMaxValues() {
        mockEmployeeGetLengthOfService(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(Double.POSITIVE_INFINITY, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldWorkCorrectWithCornerMinValues() {
        mockEmployeeGetLengthOfService(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(Double.MIN_VALUE, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrect(){
        mockEmployeeGetLengthOfService(10.1, 20.2, 30.3);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(30.3, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMaxValues(){
        mockEmployeeGetLengthOfService(Double.MAX_VALUE, 20.2, 30.3);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(Double.MAX_VALUE, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMinValues(){
        mockEmployeeGetLengthOfService(10.1, Double.MIN_VALUE, 30.3);
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals(30.3, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void mostCommonCharactersInEmployeesNamesShouldWorkCorrect() {
        mockEmployeeGetName("Aaaaaaf", "Aaaaaaf Bbbbbf", "Aaaaaaf Bbbbbf Cccccf");
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        Assert.assertEquals("[a, b, f]", employeeService.mostCommonCharactersInEmployeesNames().toString());
    }

    @Test
    public void fillEmployeeNamesInToMapShouldWorkCorrect(){
        mockEmployeeGetName("Aaaaaaf", "Aaaaaaf Bbbbbf", "Aaaaaaf Bbbbbf Cccccf");
        addEmployeesToRepository(this.mockedEmployeeOne, this.mockedEmployeeTwo, this.mockedEmployeeThree);
        EmployeeService employeeService = new EmployeeService(this.employeeRepository);
        HashMap<Character, Integer> map = new HashMap<>();
        for (Employee employee : this.employeeRepository.getEmployeeList()) {
            for (Character ch : employee.getName().toLowerCase().toCharArray()) {
                map.putIfAbsent(ch, 0);
                map.put(ch, map.get(ch) + 1);
            }
        }

        Assert.assertEquals(employeeService.fillEmployeeNamesInToMap(), map);
    }

    private void mockEmployeeGetName(String nameOne, String nameTwo, String nameThree) {
        Mockito.when(this.mockedEmployeeOne.getName()).thenReturn(nameOne);
        Mockito.when(this.mockedEmployeeTwo.getName()).thenReturn(nameTwo);
        Mockito.when(this.mockedEmployeeThree.getName()).thenReturn(nameThree);
    }

    private void mockEmployeeGetLengthOfService(double lengthOne, double lengthTwo, double lengthThree) {
        Mockito.when(this.mockedEmployeeOne.getLengthOfService()).thenReturn(lengthOne);
        Mockito.when(this.mockedEmployeeTwo.getLengthOfService()).thenReturn(lengthTwo);
        Mockito.when(this.mockedEmployeeThree.getLengthOfService()).thenReturn(lengthThree);
    }

    private void mockEmployeeGetAge(int ageOne, int ageTwo, int ageThree) {
        Mockito.when(this.mockedEmployeeOne.getAge()).thenReturn(ageOne);
        Mockito.when(this.mockedEmployeeTwo.getAge()).thenReturn(ageTwo);
        Mockito.when(this.mockedEmployeeThree.getAge()).thenReturn(ageThree);
    }

    private void addEmployeesToRepository(Employee mockedEmployeeOne,
                                          Employee mockedEmployeeTwo, Employee mockedEmployeeThree) {
        this.employeeRepository.addEmployee(mockedEmployeeOne);
        this.employeeRepository.addEmployee(mockedEmployeeTwo);
        this.employeeRepository.addEmployee(mockedEmployeeThree);
    }
}