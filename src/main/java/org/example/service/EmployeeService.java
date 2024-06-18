package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.model.Employee;

import java.util.List;

public class EmployeeService {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public void saveEmployee(Employee employee) {
        employeeDAO.saveEmployee(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    // Other service methods...
}
