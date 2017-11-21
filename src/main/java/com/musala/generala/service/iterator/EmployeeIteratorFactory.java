package com.musala.generala.service.iterator;

import com.musala.generala.models.Employee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class EmployeeIteratorFactory {
    public Iterator<Employee> getEmployeeIterator(String path) throws IOException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new IOException("Could not find file " + path +
                    ", Original exception message: " + e.getMessage());
        }
        return new EmployeeIterator(bufferedReader);
    }
}
