package main.java.com.musala.generala.repositories;

import main.java.com.musala.generala.models.Employee;

import java.util.*;

public class EmployeeRepository{
    private List<Employee> employeeList;

    public EmployeeRepository() {
        this.employeeList = new ArrayList<>();
    }

    public List<Employee> getEmployeeList() {
        return Collections.unmodifiableList(this.employeeList);
    }

    public void addEmployee(Employee employee) {
        this.employeeList.add(employee);
    }


}
