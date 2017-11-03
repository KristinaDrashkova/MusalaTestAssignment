package main.java.interfaces;

import java.util.HashMap;
import java.util.List;

public interface IEmployeeService {
    void initialize();

    double averageAgeOfEmployees();

    double averageLengthOfServiceOfEmployees();

    double maximumLengthOfServiceOfEmployee();

    List<Character> mostCommonCharactersInEmployeesNames();

    HashMap<Character, Integer> fillEmployeeNamesInToMap();
}
