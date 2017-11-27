package com.musala.generala.service.iterator;

import com.musala.generala.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class EmployeeIteratorFactoryFromFile implements IEmployeeIteratorFactory {
    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(EmployeeIteratorFactoryFromFile.class);
    private String path;

    public EmployeeIteratorFactoryFromFile(String path) {
        this.path = path;
    }

    public Iterator<Employee> createEmployeeIterator() throws FileNotFoundException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            LOGGER.error("There was problem loading the file: {}", path);
            throw e;
        }
        return new EmployeeIterator(bufferedReader);
    }
}
