package com.musala.generala;

import com.musala.generala.service.EmployeeIteratorFactory;
import com.musala.generala.service.IEmployeeService;
import com.musala.generala.service.EmployeeService;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;


public class Main {
    private static final String CONFIG_FILENAME_PATH = "src/main/webapp/WEB-INF/lib/log4j.properties";

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure(CONFIG_FILENAME_PATH);
        EmployeeIteratorFactory employeeIteratorFactory = new EmployeeIteratorFactory();
        IEmployeeService employeeService = new EmployeeService(employeeIteratorFactory);
        employeeService.getEmployeeInfo();
    }
}
