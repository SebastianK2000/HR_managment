package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.model.Employee;

import javax.persistence.RollbackException;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    public void saveEmployee(Employee employee) {
        try {
            employeeDAO.beginTransaction();
            employeeDAO.saveEmployee(employee);
            employeeDAO.commitTransaction();
        } catch (RollbackException e) {
            employeeDAO.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            employeeDAO.closeEntityManager();
        }
    }

    public List<Employee> getAllEmployees() {
        try {
            employeeDAO.beginTransaction();
            List<Employee> employees = employeeDAO.getAllEmployees();
            employeeDAO.commitTransaction();
            return employees;
        } catch (RuntimeException e) {
            employeeDAO.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            employeeDAO.closeEntityManager();
        }
    }

    public Employee getEmployeeById(Long id) {
        try {
            employeeDAO.beginTransaction();
            Employee employee = employeeDAO.getEmployeeById(id);
            employeeDAO.commitTransaction();
            return employee;
        } catch (RuntimeException e) {
            employeeDAO.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            employeeDAO.closeEntityManager();
        }
    }

    public void updateEmployee(Employee employee) {
        try {
            employeeDAO.beginTransaction();
            employeeDAO.updateEmployee(employee);
            employeeDAO.commitTransaction();
        } catch (RollbackException e) {
            employeeDAO.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            employeeDAO.closeEntityManager();
        }
    }

    public void deleteEmployee(Long id) {
        try {
            employeeDAO.beginTransaction();
            employeeDAO.deleteEmployee(id);
            employeeDAO.commitTransaction();
        } catch (RollbackException e) {
            employeeDAO.rollbackTransaction();
            e.printStackTrace();
            throw e;
        } finally {
            employeeDAO.closeEntityManager();
        }
    }
}
