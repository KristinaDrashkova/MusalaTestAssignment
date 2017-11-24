package com.musala.generala;

import com.musala.generala.service.iterator.EmployeeIteratorFactoryFromFile;
import com.musala.generala.service.IEmployeeService;
import com.musala.generala.service.EmployeeService;
import com.musala.generala.service.iterator.IEmployeeIteratorFactory;
import org.apache.log4j.PropertyConfigurator;
import java.io.IOException;


public class Main {
    private static final String CONFIG_FILENAME_PATH = "src/main/webapp/WEB-INF/lib/log4j.properties";
    private static final String RESOURCES_EMPLOYEE_DATA_PATH = "src/main/resources/employee data.txt";

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(CONFIG_FILENAME_PATH);
        IEmployeeIteratorFactory employeeIteratorFactory =
                new EmployeeIteratorFactoryFromFile(RESOURCES_EMPLOYEE_DATA_PATH);
        IEmployeeService employeeService = new EmployeeService(employeeIteratorFactory);
        employeeService.logEmployeeInfo();
    }
}
