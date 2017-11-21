package com.musala.generala.service;

import com.musala.generala.exeptions.NoEmployeesException;
import com.musala.generala.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that does most of the business logic in the project
 * for more information check the methods documentation
 */
public class EmployeeService implements IEmployeeService {
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private EmployeeIteratorFactory employeeIteratorFactory;

    public EmployeeService(EmployeeIteratorFactory employeeIteratorFactory) {
        this.employeeIteratorFactory = employeeIteratorFactory;
    }

    @Override
    public void getEmployeeInfo() throws IOException {
        EmployeeIterator employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(RESOURCES_EMPLOYEE_DATA_PATH);
        if (!employeeIterator.hasNext()) {
            try {
                LOGGER.error("There are no employees");
                throw new NoEmployeesException("There are no employees");
            } catch (NoEmployeesException e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("Average age of employees: {}", this.averageAgeOfEmployees() + "");
            LOGGER.info("First three most common characters: {}"
                    , this.mostCommonCharactersInEmployeesNames().toString());
            LOGGER.info("Average length of service of the employees: {}"
                    , this.averageLengthOfServiceOfEmployees() + "");
            LOGGER.info("Maximum length of service among all employees: {}"
                    , this.maximumLengthOfServiceOfEmployee() + "");
        }
    }


    /**
     * Returns the calculated average age
     * from all the Employee in the EmployeeRepository
     *
     * @return calculated average age
     * @see com.musala.generala.models.Employee
     */
    @Override
    public double averageAgeOfEmployees() throws IOException {
        EmployeeIterator employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(RESOURCES_EMPLOYEE_DATA_PATH);
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
     * from all the Employee in the EmployeeRepository
     *
     * @return calculated average length of service
     * @see com.musala.generala.models.Employee
     */
    @Override
    public double averageLengthOfServiceOfEmployees() throws IOException {
        EmployeeIterator employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(RESOURCES_EMPLOYEE_DATA_PATH);
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
     * from all the Employee in the EmployeeRepository
     *
     * @return the maximum length of service
     * @see com.musala.generala.models.Employee
     */
    @Override
    public double maximumLengthOfServiceOfEmployee() throws IOException {
        EmployeeIterator employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(RESOURCES_EMPLOYEE_DATA_PATH);
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
     * from all the names of all the Employee in the EmployeeRepository
     *
     * @return list of the first three most common characters
     * from all the names
     * @see com.musala.generala.models.Employee
     */
    @Override
    public List<Character> mostCommonCharactersInEmployeesNames() throws IOException {
        return countCharactersInEmployeeNames().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Returns HashMap from all the characters
     * in the names of the all Employee in the EmployeeRepository
     * with the number of their occurrences as a value
     *
     * @return HasMap with character for a key and its occurrence
     * in all the names of all the Employee in the EmployeeRepository as a value
     * @see com.musala.generala.models.Employee
     */
    @Override
    public HashMap<Character, Integer> countCharactersInEmployeeNames() throws IOException {
        EmployeeIterator employeeIterator = this.employeeIteratorFactory.getEmployeeIterator(RESOURCES_EMPLOYEE_DATA_PATH);
        HashMap<Character, Integer> countCharactersInNames = new LinkedHashMap<>();
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
