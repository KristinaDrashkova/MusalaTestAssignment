public class Main {

    private final static String PATH = "C:\\Users\\kristina.drashkova\\IdeaProjects\\untitled\\src\\resources\\test.txt";

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        employeeService.run();
    }
}
