package com.musala.generala.repositories;

import com.musala.generala.models.Employee;

import java.util.*;

/**
 * The class that holds record of all Employee
 * and gives access to add Employee
 * and retrieves all Employee as list
 */

public class EmployeeRepository{
    private List<Employee> employeeList;

    public EmployeeRepository() {
        this.employeeList = new ArrayList<>();
    }

    /**
     *  Returns List of Employee that is unmodifiable
     *
     * @see com.musala.generala.models.Employee
     *
     * @return unmodifiable list of Employee
     */
    public List<Employee> getEmployeeList() {
        return Collections.unmodifiableList(this.employeeList);
    }

    /**
     *
     * @param employee to be added to the list
     */
    public void addEmployee(Employee employee) {
        this.employeeList.add(employee);
    }


}
