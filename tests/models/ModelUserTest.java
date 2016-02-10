package models;

import entity.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

public class ModelUserTest {
    ModelUser model = new ModelUser();


    @Test
    public void testGetUserFromUrl() throws Exception {
        assertTrue(model.getUserFromUrl("") == null);
        assertTrue(model.getUserFromUrl("/user/1") != null);
        assertTrue(model.getUserFromUrl("/user/9999") == null);
        assertTrue(model.getUserFromUrl("/sdgsdgsdg/") == null);
    }


}