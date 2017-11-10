package com.musala.generala.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.musala.generala.constants.Constants.*;

/**
 * Class Employee with name(String), age(int), lengthOfService(double)
 */

public class Employee{

    private String name;
    private int age;
    private double lengthOfService;
    private final static Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    public Employee(String name, int age, double lengthOfService) {
        this.setName(name);
        this.setAge(age);
        this.setLengthOfService(lengthOfService);
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name.isEmpty()) {
            LOGGER.error(ILLEGAL_EMPLOYEE_NAME_MESSAGE, name);
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     *  Returns age of Employee.class
     *
     * @return age of Employee
     */
    public int getAge() {
        return this.age;
    }

    private void setAge(int age) {
        if (age <= 0) {
            LOGGER.error(ILLEGAL_EMPLOYEE_AGE_MESSAGE, age);
            throw new IllegalArgumentException();
        }
        this.age = age;
    }

    /**
     *  Returns length of service of Employee
     *
     * @return length of service of Employee
     */
    public double getLengthOfService() {
        return this.lengthOfService;
    }

    private void setLengthOfService(double lengthOfService) {
        if (lengthOfService <= 0.0) {
            LOGGER.error(ILLEGAL_EMPLOYEE_LENGTH_OF_SERVICE_MESSAGE, lengthOfService);
            throw new IllegalArgumentException();
        }
        this.lengthOfService = lengthOfService;
    }
}
