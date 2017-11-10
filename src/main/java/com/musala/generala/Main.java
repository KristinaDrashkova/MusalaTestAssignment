package com.musala.generala;

import com.musala.generala.interfaces.IEmployeeService;
import com.musala.generala.repositories.EmployeeRepository;
import com.musala.generala.service.EmployeeService;
import org.apache.log4j.PropertyConfigurator;
import static com.musala.generala.constants.Constants.*;


public class Main {



    public static void main(String[] args) {
        PropertyConfigurator.configure(CONFIG_FILENAME_PATH);
        EmployeeRepository employeeRepository = new EmployeeRepository();
        IEmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.parse(RESOURCES_EMPLOYEE_DATA_PATH);
        employeeService.getEmployeeInfo();
    }
}
