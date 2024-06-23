package org.example.dao;

import org.example.model.Employee;
import org.example.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.RollbackException;
import java.util.List;

public class EmployeeDAO {

    private Session session;

    public EmployeeDAO() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public void beginTransaction() {
        session.beginTransaction();
    }

    public void commitTransaction() {
        session.getTransaction().commit();
    }

    public void rollbackTransaction() {
        session.getTransaction().rollback();
    }

    public void closeEntityManager() {
        session.close();
    }

    public void saveEmployee(Employee employee) {
        try {
            session.save(employee);
        } catch (Exception e) {
            handleException(e);
        }
    }

    public List<Employee> getAllEmployees() {
        return session.createQuery("from Employee", Employee.class).list();
    }

    public Employee getEmployeeById(Long id) {
        return session.get(Employee.class, id);
    }

    public void updateEmployee(Employee employee) {
        try {
            session.update(employee);
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void deleteEmployee(Long id) {
        Employee employee = session.get(Employee.class, id);
        if (employee != null) {
            session.delete(employee);
        }
    }

    private void handleException(Exception e) {
        if (session.getTransaction() != null && session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        e.printStackTrace();
        throw new RuntimeException("Error in Hibernate operation", e);
    }
}
