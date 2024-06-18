package org.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<JobHistory> jobHistory;

    // Getters and Setters for id, firstName, lastName, email, phoneNumber

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Additional methods for managing jobHistory

    public List<JobHistory> getJobHistory() {
        return jobHistory;
    }

    public void setJobHistory(List<JobHistory> jobHistory) {
        this.jobHistory = jobHistory;
    }

    public void addJobHistory(JobHistory jobHistory) {
        jobHistory.setEmployee(this);
        this.jobHistory.add(jobHistory);
    }

    public void removeJobHistory(JobHistory jobHistory) {
        this.jobHistory.remove(jobHistory);
        jobHistory.setEmployee(null);
    }
}
