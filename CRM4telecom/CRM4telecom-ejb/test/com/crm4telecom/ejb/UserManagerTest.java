/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crm4telecom.ejb;

import com.crm4telecom.jpa.Users;
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
public class UserManagerTest {
    
    @EJB
    private UserManagerLocal instance = new UserManager();
    public UserManagerTest() {
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

    @Test
    public void testLogin() throws Exception {
        System.out.println("testLogin");
        try {
            instance.login(null, null);
            fail("should've thrown an exception");
        } catch (Throwable expected) {
            System.out.println("Expected:" + NullPointerException.class.toString());
            System.out.println("Actual :" + expected.getClass().toString());
            assertEquals(NullPointerException.class, expected.getClass());
        }
        
    }

}
