import java.util.*;
import java.util.stream.Collectors;

public class EmployeeRepository {
    private List<Employee> employeeList;

    public EmployeeRepository() {
        this.employeeList = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        this.employeeList.add(employee);
    }

    public double averageAgeOfEmployees() {
        int employeesSumAges = this.employeeList.stream().mapToInt(Employee::getAge).sum();
        double size = this.employeeList.size() * 1.00;
        return  employeesSumAges / size;
    }

    public double averageLengthOfServiceOfEmployees() {
        double employeeSumLengthOfService = this.employeeList.stream().mapToDouble(Employee::getLengthOfService).sum();
        double size = this.employeeList.size() * 1.00;
        return employeeSumLengthOfService / size;
    }

    public double maximumLengthOfServiceOfEmployee() {
        Optional<Employee> optionalMaxLengthOfServiceEmployee = this.employeeList.stream().max(Comparator.comparing(Employee::getLengthOfService));
        Employee employee = optionalMaxLengthOfServiceEmployee.get();
        return employee.getLengthOfService();
    }

    public String mostCommonCharactersInEmployeesNames() {
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> mostCommonCharactersMap = fillEmployeeNamesInToMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
        ));
        for (Character character : mostCommonCharactersMap.keySet()) {
            sb.append(character);
            sb.append(", ");
        }

        return sb.toString();
    }

    private HashMap<Character, Integer> fillEmployeeNamesInToMap() {
        HashMap<Character, Integer> charactersInNames = new HashMap<>();
        for (Employee employee : this.employeeList) {
            for (char c : employee.getName().toCharArray()) {
                if (!charactersInNames.containsKey(c)) {
                    charactersInNames.put(c, 0);
                }
                int value = charactersInNames.get(c);
                value += 1;
                charactersInNames.put(c, value);
            }
        }

        return charactersInNames;
    }
}
