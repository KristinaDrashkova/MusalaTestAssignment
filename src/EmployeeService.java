import exeptions.FileNotFoundCustomException;
import exeptions.NoEmployeesException;
import interfaces.IEmployee;
import interfaces.IEmployeeRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

class EmployeeService implements Runnable{

    private final static String PATH = "src/resources/test.txt";
    private IEmployeeRepository employeeRepository;

    EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String currentLine;
            String name = "";
            int age = 0;
            double lengthOfService = 0;
            while ((currentLine = br.readLine()) != null) {
                String[] lineData = currentLine.split(Pattern.quote("="));
                if (lineData.length == 1 || currentLine.trim().equals("<<>>")) {continue;}
                String key = lineData[0];
                String value = lineData[1];
                switch (key) {
                    case "name" :
                        name = value.trim();
                        break;
                    case "age" :
                        age = Integer.parseInt(value.trim());
                        break;
                    case "lengthOfService" : {
                        lengthOfService = Double.parseDouble(value.trim());
                        IEmployee employee = new Employee(name, age, lengthOfService);
                        this.employeeRepository.addEmployee(employee);
                    } break;

                }

            }

            if (this.employeeRepository.getEmployeeList().size() == 0) {
                throw new NoEmployeesException("There are no employees");
            } else {
                System.out.println("Average age of employees: " + this.employeeRepository.averageAgeOfEmployees());
                System.out.println("First three most common characters: " + this.employeeRepository.mostCommonCharactersInEmployeesNames());
                System.out.println("Average length of service of the employees: " + this.employeeRepository.averageLengthOfServiceOfEmployees());
                System.out.println("Maximum length of service among all employees: " + this.employeeRepository.maximumLengthOfServiceOfEmployee());
            }
        } catch (IOException e) {
            try {
                throw new FileNotFoundCustomException("Can not find this path:" + PATH);
            } catch (FileNotFoundCustomException ex) {
                ex.printStackTrace();
            }
        } catch (NoEmployeesException e) {
            e.printStackTrace();
        }
    }
}
