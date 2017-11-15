package com.musala.generala;

import com.musala.generala.service.IEmployeeService;
import com.musala.generala.repositories.EmployeeRepository;
import com.musala.generala.service.EmployeeService;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;


public class Main {
    private static final String CONFIG_FILENAME_PATH = "src/main/webapp/WEB-INF/lib/log4j.properties";
    private final static String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(CONFIG_FILENAME_PATH);
        EmployeeRepository employeeRepository = new EmployeeRepository();
        IEmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.parse(RESOURCES_EMPLOYEE_DATA_PATH);
        employeeService.getEmployeeInfo();
        
    }
}
