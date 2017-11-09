package com.musala.generala.interfaces;

import java.util.HashMap;
import java.util.List;

public interface IEmployeeService {

    void parse(String path);

    void getEmployeeInfo();

    void log(String status, String message, String parameters);

    double averageAgeOfEmployees();

    double averageLengthOfServiceOfEmployees();

    double maximumLengthOfServiceOfEmployee();

    List<Character> mostCommonCharactersInEmployeesNames();

    HashMap<Character, Integer> fillEmployeeNamesInToMap();
}
