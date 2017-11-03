package main.java.com.musala.generala;

import main.java.com.musala.generala.interfaces.IEmployeeService;
import main.java.com.musala.generala.repositories.EmployeeRepository;
import main.java.com.musala.generala.service.EmployeeService;

public class Main {

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        IEmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.initialize();
    }
}
