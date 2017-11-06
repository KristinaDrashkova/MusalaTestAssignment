package main.java.com.musala.generala;

import main.java.com.musala.generala.interfaces.IEmployeeService;
import main.java.com.musala.generala.repositories.EmployeeRepository;
import main.java.com.musala.generala.service.EmployeeService;
import org.apache.log4j.PropertyConfigurator;


public class Main {

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/main/webapp/WEB-INF/lib/log4j.properties");
        EmployeeRepository employeeRepository = new EmployeeRepository();
        IEmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.initialize();
    }
}
