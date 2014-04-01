/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Alex
 */
@Stateless
public class CustomerManagerTest {

    @EJB
    private CustomerManagerLocal instance = new CustomerManager() {
        @Override
        public void persist(Customer c) {
            System.out.println("Persits Customer");
        }
        @Override 
        public Customer find(long customerId){
            
            return null; 
        }

    };

    public CustomerManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetCustomer() throws Exception {
        System.out.println("testGetCustomer");
        System.out.println("Expected: null");
        System.out.println("Actual :" + instance.getCustomer((long) -1));
        assertEquals(null, instance.getCustomer((long) -1));
        System.out.println("Expected: null");
        System.out.println("Actual :" + instance.getCustomer((long)1));
        assertEquals(null, instance.getCustomer((long) 1));
        try {
            instance.getCustomer(null);
            fail("should've thrown an exception");
        } catch (Throwable expected) {
            System.out.println("Expected:" + NullPointerException.class.toString());
            System.out.println("Actual :" + expected.getClass().toString());
            assertEquals(NullPointerException.class, expected.getClass());
        }
    }

    @Test
    public void testCreateCustomer() {
        Customer c = new Customer();
        System.out.println("testCreateCustomer");
        try {
            instance.createCustomer(null);
            fail("should've thrown an exception");
        } catch (Throwable expected) {
            System.out.println("Expected:" + NullPointerException.class.toString());
            System.out.println("Actual :" + expected.getClass().toString());
            assertEquals(NullPointerException.class, expected.getClass());
        }
    }

    @Test
    public void testGetMarkets() {
        System.out.println("testGetMarkets");
        try {
            instance.getMarkets(null);
            fail("should've thrown an exception");
        } catch (Throwable expected) {
            System.out.println("Expected:" + NullPointerException.class.toString());
            System.out.println("Actual :" + expected.getClass().toString());
            assertEquals(NullPointerException.class, expected.getClass());
        }
    }
}
