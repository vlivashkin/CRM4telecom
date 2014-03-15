/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class EmployeeManager implements EmployeeManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<String> completeEmployee(String rawEmployee) {
        if (rawEmployee == null) {
            return null;
        }
        List<String> employee = new ArrayList<>();
        String raw = rawEmployee.trim();
        if (raw.matches("^#?\\d+$")) {
            if (raw.startsWith("#")) {
                raw = raw.substring(1);
            }
            Long id = Long.parseLong(raw);
            Employee found = em.find(Employee.class, id);
            if (found != null) {
                employee.add(found.toString());
            }
        } else {
            String[] split = raw.split(" ");
            String sqlQuery = "SELECT c FROM Employee c WHERE LOWER(c.firstName) REGEXP '"+split[0].toLowerCase() +"' or LOWER(c.lastName)  REGEXP '"+ split[0].toLowerCase()+"'";
            System.out.println(sqlQuery);
            Query query = em.createQuery(sqlQuery);
                    //.setParameter("str1", "%" + split[0].toLowerCase() + "%");
      /*      if (split.length == 1) {
                query.setParameter("str2", "%" + split[0].toLowerCase() + "%");
            } else if (split.length == 2) {
                query.setParameter("str2", "%" + split[1].toLowerCase() + "%");
            } else {
                return employee;
            }
        */    query.setMaxResults(10);
            return query.getResultList();
        }
        return employee;
    }
    
    }
    
