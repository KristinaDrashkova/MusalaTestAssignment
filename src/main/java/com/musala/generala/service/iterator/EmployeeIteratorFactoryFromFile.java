package com.musala.generala.service.iterator;

import com.musala.generala.models.Employee;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class EmployeeIteratorFactoryFromFile implements IEmployeeIteratorFactory {
    private String path;

    public EmployeeIteratorFactoryFromFile(String path) {
        this.path = path;
    }

    public Iterator<Employee> getEmployeeIterator() throws IOException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            throw new IOException("Could not find file " + path +
                    ", Original exception message: " + e.getMessage());
        }
        return new EmployeeIterator(bufferedReader);
    }
}
