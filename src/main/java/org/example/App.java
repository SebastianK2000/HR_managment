package org.example;

import org.example.model.Employee;
import org.example.service.EmployeeService;

public class App {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPhoneNumber("123456789");

        employeeService.saveEmployee(employee);
    }
}
