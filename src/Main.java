public class Main {

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.run();
    }
}
