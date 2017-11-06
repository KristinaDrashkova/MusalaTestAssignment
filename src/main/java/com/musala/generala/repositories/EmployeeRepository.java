package main.java.com.musala.generala.repositories;

import main.java.com.musala.generala.models.Employee;

import java.util.*;

public class EmployeeRepository{
    private List<Employee> employeeList;

    public EmployeeRepository() {
        this.employeeList = new ArrayList<>();
    }

    /**
     *
     * @return this.employeeList
     */
    public List<Employee> getEmployeeList() {
        return Collections.unmodifiableList(this.employeeList);
    }

    /**
     *
     * @param employee
     */
    public void addEmployee(Employee employee) {
        this.employeeList.add(employee);
    }


}
