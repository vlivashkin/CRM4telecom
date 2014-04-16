package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Employee;
import com.crm4telecom.jpa.Product;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetManagerTest {

    private static GetManagerLocal instance;
    private static Long l = -1L;
    private static Long l1 = 1L;

    public GetManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance = new GetManager() {
            @Override
            public Product create(String sqlQuery, String product) {
                Product p = new Product();
                p.setName("testProduct");
                p.setDescription("testDescription");
                return p;
            }

            @Override
            public Employee find(long EmployeeId) {
                Employee e = new Employee();
                e.setFirstName("testFirstName");
                e.setLastName("testLastName");
                return e;
            }
        };
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProduct_shouldThrowNullPointerException_whenProductIsNull() {
        instance.getProduct((String)null);
    }

    @Test
    public void testGetProduct_shouldReturnStub_whenProductIsNotNull() {
        assertEquals("testProduct", instance.getProduct("test").getName());
        assertEquals("testDescription", instance.getProduct("test").getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployee_shouldThrowNullPointerException_whenEmployeeIdIsNull() {
        instance.getEmployee(null);
    }

    @Test
    public void testGetEmployee_shouldReturnNull_whenEmployeeIdIsNegative() {
        assertEquals(null, instance.getEmployee(l));
    }

    @Test
    public void testGetEmployee_shouldReturnStub_whenEmployeeIdIsPositive() {
        assertEquals("testFirstName", instance.getEmployee(l1).getFirstName());
        assertEquals("testLastName", instance.getEmployee(l1).getLastName());
    }
   

}
