package main.java.com.musala.generala;

import main.java.com.musala.generala.interfaces.IEmployeeService;
import main.java.com.musala.generala.repositories.EmployeeRepository;
import main.java.com.musala.generala.service.EmployeeService;
import main.java.com.musala.generala.logging.LogClass;

public class Main {

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        IEmployeeService employeeService = new EmployeeService(employeeRepository);
        LogClass.setup();
        employeeService.initialize();
    }
}
