import exeptions.InvalidEntryParametersException;
import interfaces.IEmployee;
//Made other change
class Employee implements IEmployee {
    private String name;
    private int age;
    private double lengthOfService;

    Employee(String name, int age, double lengthOfService) {
        this.setName(name);
        this.setAge(age);
        this.setLengthOfService(lengthOfService);
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name.equals("")) {
            try {
                throw new InvalidEntryParametersException("Invalid employee name: " + name);
            } catch (InvalidEntryParametersException e) {
                e.printStackTrace();
            }
        }
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    private void setAge(int age) {
        if (age <= 0) {
            try {
                throw new InvalidEntryParametersException("Invalid employee age: " + age);
            } catch (InvalidEntryParametersException e) {
                e.printStackTrace();
            }
        }
        this.age = age;
    }

    public double getLengthOfService() {
        return this.lengthOfService;
    }

    private void setLengthOfService(double lengthOfService) {
        if (lengthOfService <= 0.0) {
            try {
                throw new InvalidEntryParametersException("Invalid employee length of service: " + lengthOfService);
            } catch (InvalidEntryParametersException e) {
                e.printStackTrace();
            }
        }
        this.lengthOfService = lengthOfService;
    }
}
