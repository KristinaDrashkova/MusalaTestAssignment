package com.musala.generala.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Employee {
    private final static Logger LOGGER = LoggerFactory.getLogger(Employee.class);
    private String name;
    private int age;
    private double lengthOfService;

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
            LOGGER.error("Illegal employee name: {}", name);
            throw new IllegalArgumentException("Illegal employee name: " + name);
        }
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    private void setAge(int age) {
        if (age <= 0) {
            LOGGER.error("Illegal employee age: {}", age);
            throw new IllegalArgumentException("Illegal employee age: " + age);
        }
        this.age = age;
    }

    public double getLengthOfService() {
        return this.lengthOfService;
    }

    private void setLengthOfService(double lengthOfService) {
        if (lengthOfService <= 0.0) {
            LOGGER.error("Illegal employee length of service: {}", lengthOfService);
            throw new IllegalArgumentException("Illegal employee length of service: " + lengthOfService);
        }
        this.lengthOfService = lengthOfService;
    }
}
