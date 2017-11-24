package com.musala.generala.service;

import com.musala.generala.exeptions.NoEmployeesException;
import com.musala.generala.models.Employee;
import com.musala.generala.service.iterator.IEmployeeIteratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService implements IEmployeeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private IEmployeeIteratorFactory employeeIteratorFactory;

    public EmployeeService(IEmployeeIteratorFactory employeeIteratorFactory) {
        this.employeeIteratorFactory = employeeIteratorFactory;
    }

    @Override
    public void logEmployeeInfo() throws IOException, NoEmployeesException {
        Iterator employeeIterator = this.employeeIteratorFactory.getEmployeeIterator();
        if (!employeeIterator.hasNext()) {
            LOGGER.error("There are no employees");
            throw new NoEmployeesException("There are no employees");
        } else {
            double averageAgeOfEmployees = averageAgeOfEmployees();
            double averageLengthOfServiceOfEmployees = averageLengthOfServiceOfEmployees();
            List<Character> mostCommonCharactersInEmployeesNames = mostCommonCharactersInEmployeesNames(3);
            double maximumLengthOfServiceOfEmployee = maximumLengthOfServiceOfEmployee();
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
    public double averageAgeOfEmployees() throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator();
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
    public double averageLengthOfServiceOfEmployees() throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator();
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
    public double maximumLengthOfServiceOfEmployee() throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator();
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
    public List<Character> mostCommonCharactersInEmployeesNames(int count) throws IOException {
        if (count > countCharactersInEmployeeNames().size()) {
            count = countCharactersInEmployeeNames().size();
        }
        return countCharactersInEmployeeNames().entrySet().stream()
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
     */
    private Map<Character, Integer> countCharactersInEmployeeNames() throws IOException {
        Iterator<Employee> employeeIterator = this.employeeIteratorFactory.getEmployeeIterator();
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
