package com.musala.generala.service;

import com.musala.generala.exeptions.NoEmployeesException;
import com.musala.generala.interfaces.IEmployeeService;
import com.musala.generala.models.Employee;
import com.musala.generala.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EmployeeService implements IEmployeeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void parse(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String currentLine;
            String name = "";
            int age = 0;
            double lengthOfService = 0;
            while ((currentLine = br.readLine()) != null) {
                String[] lineData = currentLine.split(Pattern.quote("="));
                if (lineData.length == 1 || currentLine.trim().equals("<<>>")) {continue;}
                String key = lineData[0];
                String value = lineData[1];
                switch (key) {
                    case "name" :
                        name = value.trim();
                        break;
                    case "age" :
                        age = Integer.parseInt(value.trim());
                        break;
                    case "lengthOfService" : {
                        lengthOfService = Double.parseDouble(value.trim());
                        try {
                            Employee employee = new Employee(name, age, lengthOfService);
                            this.employeeRepository.addEmployee(employee);
                            LOGGER.info("User {} has been successfully added", name);
                        } catch (IllegalArgumentException e) {
                            LOGGER.error(
                                    "User {} has NOT been successfully added due to invalid input information", name);
                        }
                    } break;

                }

            }

        } catch (IOException e) {
            LOGGER.error("Could not find file: {}", path);
        }
    }

    public void log() {
        if (this.employeeRepository.getEmployeeList().size() == 0) {
            try {
                throw new NoEmployeesException("There are no employees");
            } catch (NoEmployeesException e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("Average age of employees: {}", this.averageAgeOfEmployees());
            LOGGER.info("First three most common characters: {}",
                    this.mostCommonCharactersInEmployeesNames());
            LOGGER.info("Average length of service of the employees: {}",
                    this.averageLengthOfServiceOfEmployees());
            LOGGER.info("Maximum length of service among all employees: {}",
                    this.maximumLengthOfServiceOfEmployee());
        }
    }

    /**
     * Returns the calculated average age
     * from all the Employee in the EmployeeRepository
     *
     * @see com.musala.generala.models.Employee
     *
     * @return calculated average age
     */
    @Override
    public double averageAgeOfEmployees() {
        long employeesSumAges = this.employeeRepository.getEmployeeList().stream().mapToLong(Employee::getAge).sum();
        double size = this.employeeRepository.getEmployeeList().size() * 1.00;
        return  employeesSumAges / size;
    }

    /**
     * Returns calculated average length of service
     * from all the Employee in the EmployeeRepository
     *
     * @see com.musala.generala.models.Employee
     *
     * @return calculated average length of service
     */
    @Override
    public double averageLengthOfServiceOfEmployees() {
        double employeeSumLengthOfService = this.employeeRepository.getEmployeeList()
                .stream().mapToDouble(Employee::getLengthOfService).sum();
        double size = this.employeeRepository.getEmployeeList().size() * 1.00;
        return employeeSumLengthOfService / size;
    }

    /**
     * Returns the maximum length of service
     * from all the Employee in the EmployeeRepository
     *
     * @see com.musala.generala.models.Employee
     *
     * @return the maximum length of service
     */
    @Override
    public double maximumLengthOfServiceOfEmployee() {
        Optional<Employee> optionalMaxLengthOfServiceEmployee =
                this.employeeRepository.getEmployeeList().stream()
                        .max(Comparator.comparing(Employee::getLengthOfService));
        Employee employee = optionalMaxLengthOfServiceEmployee.get();
        return employee.getLengthOfService();
    }

    /**
     * Returns list of the first three most common characters
     * from all the names of all the Employee in the EmployeeRepository
     *
     * @see com.musala.generala.models.Employee
     *
     * @return list of the first three most common characters
     * from all the names
     */
    @Override
    public List<Character> mostCommonCharactersInEmployeesNames() {
        List<Character> mostCommonCharactersList = fillEmployeeNamesInToMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3).map(Map.Entry::getKey).collect(Collectors.toList());


        return mostCommonCharactersList;
    }

    /**
     * Returns HashMap from all the characters
     * in the names of the all Employee in the EmployeeRepository
     * with the number of their occurrences as a value
     *
     * @see com.musala.generala.models.Employee
     *
     * @return HasMap with character for a key and its occurrence
     * in all the names of all the Employee in the EmployeeRepository as a value
     */
    @Override
    public HashMap<Character, Integer> fillEmployeeNamesInToMap() {
        HashMap<Character, Integer> charactersInNames = new HashMap<>();
        for (Employee employee : this.employeeRepository.getEmployeeList()) {
            for (char c : employee.getName().toLowerCase().toCharArray()) {
                if (!charactersInNames.containsKey(c)) {
                    charactersInNames.put(c, 0);
                }
                int value = charactersInNames.get(c);
                value += 1;
                charactersInNames.put(c, value);
            }
        }

        return charactersInNames;
    }
}
