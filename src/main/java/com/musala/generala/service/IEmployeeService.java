package com.musala.generala.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface IEmployeeService {

    void getEmployeeInfo() throws IOException;

    double averageAgeOfEmployees() throws IOException;

    double averageLengthOfServiceOfEmployees() throws IOException;

    double maximumLengthOfServiceOfEmployee() throws IOException;

    List<Character> mostCommonCharactersInEmployeesNames() throws IOException;

    HashMap<Character, Integer> countCharactersInEmployeeNames() throws IOException;
}
