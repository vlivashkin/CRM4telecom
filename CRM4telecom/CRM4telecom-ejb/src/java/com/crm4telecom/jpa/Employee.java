package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name="Employees")
@Table(catalog = "")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "EMPLOYEE_ID", nullable = false, precision = 38, scale = 0)
    private Long employeeId;

    @Size(max = 30)
    @Column(name = "JOB_DESCRIPTION", length = 30)
    private String jobDescription;

    @Size(max = 30)
    @Column(name = "FIRST_NAME", length = 30)
    private String firstName;

    @Size(max = 30)
    @Column(name = "LAST_NAME", length = 30)
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date schedule;

    @OneToMany(mappedBy = "employeeId")
    private List<OrderProcessing> orderProcessingList;

    @OneToMany(mappedBy = "employeeId")
    private List<User> usersList;

    public Employee() {
    }

    public Employee(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
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

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    @XmlTransient
    public List<OrderProcessing> getOrderProcessingList() {
        return orderProcessingList;
    }

    public void setOrderProcessingList(List<OrderProcessing> orderProcessingList) {
        this.orderProcessingList = orderProcessingList;
    }

    @XmlTransient
    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        return this.employeeId.equals(other.employeeId);
    }

    @Override
    public String toString() {
        return "#" + employeeId + " " + firstName + " " + lastName;
    }

}
