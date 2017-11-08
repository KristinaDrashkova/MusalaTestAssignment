package com.musala.generala.interfaces;

import java.util.HashMap;
import java.util.List;

public interface IEmployeeService {

    void parse(String path);

    void log();

    double averageAgeOfEmployees();

    double averageLengthOfServiceOfEmployees();

    double maximumLengthOfServiceOfEmployee();

    List<Character> mostCommonCharactersInEmployeesNames();

    HashMap<Character, Integer> fillEmployeeNamesInToMap();
}
