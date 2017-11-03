package interfaces;
//Made change
import java.util.List;

public interface IEmployeeRepository {
    List<IEmployee> getEmployeeList();

    void addEmployee(IEmployee employee);

    double averageAgeOfEmployees();

    double averageLengthOfServiceOfEmployees();

    double maximumLengthOfServiceOfEmployee();

    String mostCommonCharactersInEmployeesNames();
}
