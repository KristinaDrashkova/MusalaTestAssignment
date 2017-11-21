package com.musala.generala.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface IEmployeeService {

    void getEmployeeInfo(String path) throws IOException;

    double averageAgeOfEmployees(String path) throws IOException;

    double averageLengthOfServiceOfEmployees(String path) throws IOException;

    double maximumLengthOfServiceOfEmployee(String path) throws IOException;

    List<Character> mostCommonCharactersInEmployeesNames(String path) throws IOException;

    HashMap<Character, Integer> countCharactersInEmployeeNames(String path) throws IOException;
}
