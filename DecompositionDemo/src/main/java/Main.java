import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        String filePath = "/Users/dave/pluralsight/DataFiles/employees.csv";
        String filePath = "src/main/resources/employees.csv";

        ArrayList<Employee> employees = loadEmployees(filePath);
        System.out.println("Number of employees: " + employees.size());
//        writePayroll(employees);
    }

    private static void writePayroll(ArrayList<Employee> employees) {
        System.out.println("Number of employees: " + employees.size());
    }

    /**
     * This method reads the file specified by filePath into an ArrayList of Employee objects.
     * @param filePath the relative path to the employee CSV file.
     * @return An ArrayList of employee objects.
     */
    private static ArrayList<Employee> loadEmployees(String filePath) {
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String input;
            while((input = bufferedReader.readLine()) != null) {
                String[] parts = input.split("\\|");
                if(parts[0].equals("id")) {
                    continue;
                }
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double hoursWorked = Double.parseDouble(parts[2]);
                double payRate = Double.parseDouble(parts[3]);
                Employee newEmployee = new Employee(id, name, hoursWorked, payRate);
                employees.add(newEmployee);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return employees;
    }
}
