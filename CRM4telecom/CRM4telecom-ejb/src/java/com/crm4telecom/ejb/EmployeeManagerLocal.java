/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.ejb;

import java.util.List;
import javax.ejb.Local;

@Local
public interface EmployeeManagerLocal {
    List<String> completeEmployee(String rawEmployee);
}
