//Made change
import interfaces.IEmployee;
import interfaces.IEmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;

class EmployeeRepository implements IEmployeeRepository {
    private List<IEmployee> employeeList;

    EmployeeRepository() {
        this.employeeList = new ArrayList<>();
    }

    public List<IEmployee> getEmployeeList() {
        return Collections.unmodifiableList(this.employeeList);
    }

    public void addEmployee(IEmployee employee) {
        this.employeeList.add(employee);
    }

    public double averageAgeOfEmployees() {
        int employeesSumAges = this.employeeList.stream().mapToInt(IEmployee::getAge).sum();
        double size = this.employeeList.size() * 1.00;
        return  employeesSumAges / size;
    }

    public double averageLengthOfServiceOfEmployees() {
        double employeeSumLengthOfService = this.employeeList.stream().mapToDouble(IEmployee::getLengthOfService).sum();
        double size = this.employeeList.size() * 1.00;
        return employeeSumLengthOfService / size;
    }

    public double maximumLengthOfServiceOfEmployee() {
        Optional<IEmployee> optionalMaxLengthOfServiceEmployee =
                this.employeeList.stream().max(Comparator.comparing(IEmployee::getLengthOfService));
        IEmployee employee = optionalMaxLengthOfServiceEmployee.get();
        return employee.getLengthOfService();
    }

    public String mostCommonCharactersInEmployeesNames() {
        List<Character> mostCommonCharactersList = fillEmployeeNamesInToMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3).map(Map.Entry::getKey).collect(Collectors.toList());


        return mostCommonCharactersList.toString();
    }

    private HashMap<Character, Integer> fillEmployeeNamesInToMap() {
        HashMap<Character, Integer> charactersInNames = new HashMap<>();
        for (IEmployee employee : this.employeeList) {
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
