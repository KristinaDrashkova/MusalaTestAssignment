package com.musala.generala;

import com.musala.generala.interfaces.IEmployeeService;
import com.musala.generala.repositories.EmployeeRepository;
import com.musala.generala.service.EmployeeService;
import org.apache.log4j.PropertyConfigurator;


public class Main {

    private final static String PATH = "src/main/resources/employee data.txt";

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/main/webapp/WEB-INF/lib/log4j.properties");
        EmployeeRepository employeeRepository = new EmployeeRepository();
        IEmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.parse(PATH);
        employeeService.log();
    }
}
