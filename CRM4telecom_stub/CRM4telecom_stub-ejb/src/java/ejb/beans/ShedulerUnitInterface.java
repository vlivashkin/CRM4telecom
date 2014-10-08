/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import java.util.Map;

/**
 *
 * @author root
 */
public interface ShedulerUnitInterface {
    
    public void withdrawMoney(Map<Long, CustomerStatus> map);
    
    public Map checkStatuses();
 
}
