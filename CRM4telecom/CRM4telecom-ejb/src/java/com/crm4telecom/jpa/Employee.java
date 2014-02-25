package com.crm4telecom.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity @Table
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")})
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Collection<OrderProcessing> orderProcessingCollection;

    public Employee() {
    }

    public Employee(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
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

    public Collection<OrderProcessing> getOrderProcessingCollection() {
        return orderProcessingCollection;
    }

    public void setOrderProcessingCollection(Collection<OrderProcessing> orderProcessingCollection) {
        this.orderProcessingCollection = orderProcessingCollection;
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
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crm4telecom.jpa.Employee[ employeeId=" + employeeId + " ]";
    }
    
}
