package com.musala.generala.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EmployeeIteratorFactory {
    public EmployeeIterator getEmployeeIterator(String path) throws IOException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new IOException("Could not find file " + path +
                    "\nOriginal exception message: " + e.getMessage());
        }
        return new EmployeeIterator(bufferedReader);
    }
}
