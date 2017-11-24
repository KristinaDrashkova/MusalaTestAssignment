package com.musala.generala.service;

import com.musala.generala.exeptions.NoEmployeesException;
import com.musala.generala.service.iterator.EmployeeIteratorFactory;
import com.musala.generala.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService implements IEmployeeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private EmployeeIteratorFactory employeeIteratorFactory;

    public EmployeeService(EmployeeIteratorFactory employeeIteratorFactory) {
        this.employeeIteratorFactory = employeeIteratorFactory;
    }

    @Override
    public void logEmployeeInfo(String path) throws IOException {
        Iterator employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(path);
        if (!employeeIterator.hasNext()) {
            try {
                LOGGER.error("There are no employees");
                throw new NoEmployeesException("There are no employees");
            } catch (NoEmployeesException e) {
                e.printStackTrace();
            }
        } else {
            double averageAgeOfEmployees = averageAgeOfEmployees(path);
            double averageLengthOfServiceOfEmployees = averageLengthOfServiceOfEmployees(path);
            List<Character> mostCommonCharactersInEmployeesNames = mostCommonCharactersInEmployeesNames(path, 3);
            double maximumLengthOfServiceOfEmployee = maximumLengthOfServiceOfEmployee(path);
            LOGGER.info("Average age of employees: {}", averageAgeOfEmployees);
            LOGGER.info("First three most common characters: {}", mostCommonCharactersInEmployeesNames.toString());
            LOGGER.info("Average length of service of the employees: {}", averageLengthOfServiceOfEmployees);
            LOGGER.info("Maximum length of service among all employees: {}", maximumLengthOfServiceOfEmployee);
        }
    }


    /**
     * Returns the calculated average age
     * from all the employees
     *
     * @return calculated average age
     */
    @Override
    public double averageAgeOfEmployees(String path) throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(path);
        long employeeAgesSum = 0;
        double counter = 0.0;
        while (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            employeeAgesSum += employee.getAge();
            counter++;
        }
        return employeeAgesSum / counter;
    }

    /**
     * Returns calculated average length of service
     * from all the employees
     *
     * @return calculated average length of service
     */
    @Override
    public double averageLengthOfServiceOfEmployees(String path) throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(path);
        double employeeLengthOfServiceSum = 0.0;
        double counter = 0.0;
        while (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            employeeLengthOfServiceSum += employee.getLengthOfService();
            counter++;
        }

        return employeeLengthOfServiceSum / counter;
    }

    /**
     * Returns the maximum length of service
     * from all the employees
     *
     * @return the maximum length of service
     */
    @Override
    public double maximumLengthOfServiceOfEmployee(String path) throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(path);
        double maxLengthOfService = 0;
        while (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            if (employee.getLengthOfService() > maxLengthOfService) {
                maxLengthOfService = employee.getLengthOfService();
            }
        }
        return maxLengthOfService;
    }

    /**
     * Returns list of the first three most common characters
     * from all the names of all the employee names
     *
     * @return list of the first three most common characters
     * from all the names
     */
    @Override
    public List<Character> mostCommonCharactersInEmployeesNames(String path, int count) throws IOException {
        if (count > countCharactersInEmployeeNames(path).size()) {
            count = countCharactersInEmployeeNames(path).size();
        }
        return countCharactersInEmployeeNames(path).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(count).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Returns collection from all the characters
     * in the names of the all employees
     * with the number of their occurrences as a value
     *
     * @return collection with character for a key and its occurrence
     * in all the names of all employees as a value
     * @see com.musala.generala.models.Employee
     */
    private Map<Character, Integer> countCharactersInEmployeeNames(String path) throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(path);
        Map<Character, Integer> countCharactersInNames = new HashMap<>();
        while (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            for (char c : employee.getName().toLowerCase().toCharArray()) {
                if (!countCharactersInNames.containsKey(c)) {
                    countCharactersInNames.put(c, 0);
                }
                int value = countCharactersInNames.get(c);
                value += 1;
                countCharactersInNames.put(c, value);
            }
        }

        return countCharactersInNames;
    }
}
