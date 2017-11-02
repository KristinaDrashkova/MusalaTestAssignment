import exeptions.FileNotFoundCustomException;
import exeptions.InvalidEntryParametersException;
import exeptions.NoEmployeesException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

class EmployeeService {

    private final static String PATH = "src/resources/test.txt";
    private EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String currentLine;
            String name = "";
            int age = 0;
            double lengthOfService = 0;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.trim().equals("<<>>")) {
                    if (name.equals("") || age == 0 || lengthOfService == 0) {
                        throw new InvalidEntryParametersException("Invalid entry parameters for employee");
                    } else {
                        Employee employee = new Employee(name, age, lengthOfService);
                        this.employeeRepository.addEmployee(employee);
                    }
                }
                String[] lineData = currentLine.split(Pattern.quote("="));
                switch (lineData[0]) {
                    case "name" : name = lineData[1].trim();
                        break;
                    case "age" : age = Integer.parseInt(lineData[1].trim());
                        break;
                    case "lengthOfService" : lengthOfService = Double.parseDouble(lineData[1].trim());
                        break;
                }
            }
            if (name.equals("") || age == 0 || lengthOfService == 0) {
                throw new InvalidEntryParametersException("Invalid entry parameters for employee");
            } else {
                Employee employee = new Employee(name, age, lengthOfService);
                this.employeeRepository.addEmployee(employee);
            }
            if (this.employeeRepository.getEmployeeList().size() == 0) {
                throw new NoEmployeesException("There are no employees");
            } else {
                System.out.println("Average age of employees: " + employeeRepository.averageAgeOfEmployees());
                System.out.println("First three most common characters: " + employeeRepository.mostCommonCharactersInEmployeesNames());
                System.out.println("Average length of service of the employees: " + employeeRepository.averageLengthOfServiceOfEmployees());
                System.out.println("Maximum length of service among all employees: " + employeeRepository.maximumLengthOfServiceOfEmployee());
            }
        } catch (IOException e) {
            try {
                throw new FileNotFoundCustomException("Can not find this path:" + PATH);
            } catch (FileNotFoundCustomException ex) {
                ex.printStackTrace();
            }
        } catch (InvalidEntryParametersException | NoEmployeesException e) {
            e.printStackTrace();
        }
    }
}
