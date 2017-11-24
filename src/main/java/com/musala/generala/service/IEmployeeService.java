package com.musala.generala.service;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {

    /**
     * Logs the information about the employees
     */
    void logEmployeeInfo(String path) throws IOException;

    /**
     * Returns the calculated average age
     * from all the employees
     *
     * @return calculated average age
     */
    double averageAgeOfEmployees(String path) throws IOException;

    /**
     * Returns calculated average length of service
     * from all the employees
     *
     * @return calculated average length of service
     */
    double averageLengthOfServiceOfEmployees(String path) throws IOException;

    /**
     * Returns the maximum length of service
     * from all the employees
     *
     * @return the maximum length of service
     */
    double maximumLengthOfServiceOfEmployee(String path) throws IOException;

    /**
     * Returns list of the first three most common characters
     * from all the names of all the employee names
     *
     * @return list of the first three most common characters
     * from all the names
     */
    List<Character> mostCommonCharactersInEmployeesNames(String path, int count) throws IOException;
}
