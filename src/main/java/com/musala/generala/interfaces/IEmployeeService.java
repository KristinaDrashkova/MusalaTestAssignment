package com.musala.generala.interfaces;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface IEmployeeService {

    void parse(String path) throws IOException;

    void getEmployeeInfo();

    void log(String status, String message, String parameters);

    double averageAgeOfEmployees();

    double averageLengthOfServiceOfEmployees();

    double maximumLengthOfServiceOfEmployee();

    List<Character> mostCommonCharactersInEmployeesNames();

    LinkedHashMap<Character, Integer> countCharactersInEmployeeNames();
}
