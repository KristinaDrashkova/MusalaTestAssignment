package com.musala.generala.service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public interface IEmployeeService {

    void getEmployeeInfo() throws FileNotFoundException;

    void log(String status, String message, String parameters);

    double averageAgeOfEmployees() throws FileNotFoundException;

    double averageLengthOfServiceOfEmployees() throws FileNotFoundException;

    double maximumLengthOfServiceOfEmployee() throws FileNotFoundException;

    List<Character> mostCommonCharactersInEmployeesNames() throws FileNotFoundException;

    HashMap<Character, Integer> countCharactersInEmployeeNames() throws FileNotFoundException;
}
