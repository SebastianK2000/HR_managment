package org.example.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "job_history")
public class JobHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String jobTitle;
    private Date startDate;
    private Date endDate;

    //Getters

    public Employee getEmployee() {
        return employee;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getId() {
        return id;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
