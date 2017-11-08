package com.musala.generala.models;

/**
 * Class Employee with name(String), age(int), lengthOfService(double)
 */

public class Employee{
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
        if (name.equals("")) {
            throw new IllegalArgumentException("Illegal employee name: " + name);
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
            throw new IllegalArgumentException("Illegal employee age: " + age);
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
            throw new IllegalArgumentException("Illegal employee length f service: " + lengthOfService);
        }
        this.lengthOfService = lengthOfService;
    }
}
