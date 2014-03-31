/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Employee;
import com.crm4telecom.jpa.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
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
public class GetManagerTest {
    
    @EJB
     private GetManagerLocal instance = new GetManager();
    
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
