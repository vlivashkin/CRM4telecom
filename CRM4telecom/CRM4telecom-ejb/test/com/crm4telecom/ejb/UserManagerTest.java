package com.crm4telecom.ejb;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserManagerTest {

    private static UserManagerLocal instance;

    public UserManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance = new UserManager();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLogin_shouldThrowNullPointerException_whenLoginOrPasswordIsNull() {
        instance.login(null, null);
    }

}
