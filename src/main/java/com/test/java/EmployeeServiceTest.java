import com.musala.generala.models.Employee;
import com.musala.generala.repositories.EmployeeRepository;
import com.musala.generala.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;

public class EmployeeServiceTest {

    private Employee employeeOne;
    private Employee employeeTwo;
    private Employee employeeThree;
    private Employee mockedEmployeeOne;
    private Employee mockedEmployeeTwo;
    private EmployeeRepository mockedEmployeeRepository;
    private static final double DELTA = 1e-15;

    @Before
    public void initialize() {
        this.employeeOne = new Employee("Aaaaaaf", 10, 10.1);
        this.employeeTwo = new Employee("Aaaaaaf Bbbbbf", 20, 20.2);
        this.employeeThree = new Employee("Aaaaaaf Bbbbbf Cccccf", 30, 30.3);
        this.mockedEmployeeOne = Mockito.mock(Employee.class);
        Mockito.when(this.mockedEmployeeOne.getAge()).thenReturn(Integer.MAX_VALUE);
        Mockito.when(this.mockedEmployeeOne.getLengthOfService()).thenReturn(Double.MAX_VALUE);
        this.mockedEmployeeTwo = Mockito.mock(Employee.class);
        Mockito.when(this.mockedEmployeeTwo.getAge()).thenReturn(Integer.MIN_VALUE);
        Mockito.when(this.mockedEmployeeTwo.getLengthOfService()).thenReturn(Double.MIN_VALUE);
        this.mockedEmployeeRepository = Mockito.mock(EmployeeRepository.class);
    }
    @Test
    public void parse() {

    }

    @Test
    public void averageAgeOfEmployeesShouldCalculateCorrectWithNormalInputData(){
        mockRepositoryWithNormalData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(20.0, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMaxValue() {
        mockRepositoryWithCornerCaseMaxData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Integer.MAX_VALUE, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageAgeOfEmployeeShouldCalculateCorrectWithCornerCaseMinValue() {
        mockRepositoryWithCornerCaseMinData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Integer.MIN_VALUE, employeeService.averageAgeOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldCalculateCorrect() {
        mockRepositoryWithNormalData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(20.2, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldOverflowWithCornerMaxValues() {
        mockRepositoryWithCornerCaseMaxData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.POSITIVE_INFINITY, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void averageLengthOfServiceOfEmployeesShouldWorkCorrectWithCornerMinValues() {
        mockRepositoryWithCornerCaseMinData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.MIN_VALUE, employeeService.averageLengthOfServiceOfEmployees(),DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrect(){
        mockRepositoryWithNormalData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(30.3, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMaxValues(){
        mockRepositoryWithCornerCaseMaxData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(Double.MAX_VALUE, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void maximumLengthOfServiceOfEmployeeShouldWorkCorrectWithCornerMinValues(){
        mockRepositoryWithMixedData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals(30.3, employeeService.maximumLengthOfServiceOfEmployee(), DELTA);
    }

    @Test
    public void mostCommonCharactersInEmployeesNamesShouldWorkCorrect() {
        mockRepositoryWithNormalData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
        Assert.assertEquals("[a, b, f]", employeeService.mostCommonCharactersInEmployeesNames().toString());
    }

    @Test
    public void fillEmployeeNamesInToMapShouldWorkCorrect(){
        mockRepositoryWithNormalData();
        EmployeeService employeeService = new EmployeeService(this.mockedEmployeeRepository);
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

}