package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Customer;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerManagerTest {

    private static CustomerManagerLocal instance;
    private static Long l = -1L;
    private static Long l1 = 1L;

    public CustomerManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance = new CustomerManager() {
            @Override
            public void persist(Customer c) {
            }

            @Override
            public Customer find(long customerId) {
                Customer c = new Customer();
                c.setFirstName("testFirstName");
                c.setLastName("testLastName");
                return c;
            }
        };
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCustomer_shouldThrowNullPointerException_whenCustomerIsNull() {
        instance.getCustomer(null);
    }

    @Test
    public void testGetCustomer_shouldReturnNull_whenCustomerIdIsNegative() {
        assertEquals(null, instance.getCustomer(l));
    }

    @Test
    public void testGetCustomer_shouldReturnStub_whenIdIsPositive() {
        assertEquals("testFirstName", instance.getCustomer(l1).getFirstName());
        assertEquals("testLastName", instance.getCustomer(l1).getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCustomer_shouldThrowNullPointerException_whenCustomerIsNull() {
        instance.createCustomer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMarkets_shouldThrowNullPointerException_whenCustomerIsNull() {
        instance.getMarkets(null);
    }
}
