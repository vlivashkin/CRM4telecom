/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Employee;
import com.crm4telecom.jpa.Product;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.management.Query;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Alex
 */
@Stateless
public class GetManagerTest {
    
    @EJB
     private GetManagerLocal instance = new GetManager(){
       @Override
       public Product create(String sqlQuery,String product){
           return null;
       }
       @Override
       public Employee find(long EmployeeId){
               return null;
       }
     };
    
    public GetManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProduct method, of class GetManager.
     */
    @Test
    public void testGetProduct() throws Exception {
        System.out.println("testGetProduct");
        System.out.println("Expected: null");
        System.out.println("Actual :" + instance.getProduct("sds"));
        assertEquals(null, instance.getProduct("sds"));
        
        try {
            instance.getProduct(null);
            fail("should've thrown an exception");
        } catch (Throwable expected) {
            System.out.println("Expected:" + NullPointerException.class.toString());
            System.out.println("Actual :" + expected.getClass().toString());
            assertEquals(NullPointerException.class, expected.getClass());
        }
        
    }

    @Test
    public void testGetEmployee() throws Exception {
        System.out.println("testGetEmployee");
         System.out.println("Expected: null");
        System.out.println("Actual :" + instance.getEmployee((long) 1));
        assertEquals(null, instance.getEmployee((long) 1));
       try {
            instance.getEmployee(null);
            fail("should've thrown an exception");
        } catch (Throwable expected) {
            System.out.println("Expected:" + NullPointerException.class.toString());
            System.out.println("Actual :" + expected.getClass().toString());
            assertEquals(NullPointerException.class, expected.getClass());
        }
    }

}
