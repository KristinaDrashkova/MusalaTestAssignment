public class Employee {
    private String name;
    private int age;
    private double lengthOfService;

    Employee(String name, int age, double lengthOfService) {
        this.setName(name);
        this.setAge(age);
        this.setLengthOfService(lengthOfService);
    }

    String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    double getLengthOfService() {
        return lengthOfService;
    }

    private void setLengthOfService(double lengthOfService) {
        this.lengthOfService = lengthOfService;
    }
}
