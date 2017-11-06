package main.java.com.musala.generala.service;

import main.java.com.musala.generala.exeptions.NoEmployeesException;
import main.java.com.musala.generala.interfaces.IEmployeeService;
import main.java.com.musala.generala.models.Employee;
import main.java.com.musala.generala.repositories.EmployeeRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EmployeeService implements IEmployeeService {

    private final static Logger LOGGER = LogManager.getLogger(EmployeeService.class);
    private final static String PATH = "src/main/resources/employee data.txt";
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void initialize() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
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
                            LOGGER.info(String.format("User %s has been successfully added", name));
                        } catch (IllegalArgumentException e) {
                            LOGGER.error(String.format
                                    ("User %s has NOT been successfully added due to invalid input information", name));
                        }
                    } break;

                }

            }

            if (this.employeeRepository.getEmployeeList().size() == 0) {
                throw new NoEmployeesException("There are no employees");
            } else {
                LOGGER.info(String.format("Average age of employees: %.3f", this.averageAgeOfEmployees()));
                LOGGER.info(String.format("First three most common characters: %s",
                        this.mostCommonCharactersInEmployeesNames()));
                LOGGER.info(String.format("Average length of service of the employees: %.3f",
                        this.averageLengthOfServiceOfEmployees()));
                LOGGER.info(String.format("Maximum length of service among all employees: %.3f",
                        this.maximumLengthOfServiceOfEmployee()));
            }
        } catch (IOException e) {
            LOGGER.error(String.format("Could not find file: %s", PATH));
        } catch (NoEmployeesException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return employeeSumAges / size
     */
    @Override
    public double averageAgeOfEmployees() {
        int employeesSumAges = this.employeeRepository.getEmployeeList().stream().mapToInt(Employee::getAge).sum();
        double size = this.employeeRepository.getEmployeeList().size() * 1.00;
        return  employeesSumAges / size;
    }

    /**
     *
     * @return employeeSumLengthOfService / size
     */
    @Override
    public double averageLengthOfServiceOfEmployees() {
        double employeeSumLengthOfService = this.employeeRepository.getEmployeeList()
                .stream().mapToDouble(Employee::getLengthOfService).sum();
        double size = this.employeeRepository.getEmployeeList().size() * 1.00;
        return employeeSumLengthOfService / size;
    }

    /**
     *
     * @return employee.getLengthOfService()
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
     *
     * @return mostCommonCharactersList
     */
    @Override
    public List<Character> mostCommonCharactersInEmployeesNames() {
        List<Character> mostCommonCharactersList = fillEmployeeNamesInToMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3).map(Map.Entry::getKey).collect(Collectors.toList());


        return mostCommonCharactersList;
    }

    /**
     *
     * @return charactersInNames
     */
    @Override
    public HashMap<Character, Integer> fillEmployeeNamesInToMap() {
        HashMap<Character, Integer> charactersInNames = new HashMap<>();
        for (Employee employee : this.employeeRepository.getEmployeeList()) {
            for (char c : employee.getName().toCharArray()) {
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
