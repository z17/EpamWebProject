package models;

import entity.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

public class ModelUserTest {
    ModelUser model = new ModelUser();

    @Test
    public void testGetUserIdFromUrl() throws Exception {
        assertTrue(model.getUserIdFromUrl("/user/5") == 5);
        assertTrue(model.getUserIdFromUrl("/user/") == 0);
        assertTrue(model.getUserIdFromUrl("/asasg") == 0);
        assertTrue(model.getUserIdFromUrl("trjtrj") == 0);
        assertTrue(model.getUserIdFromUrl("/user/10") == 10);
        assertTrue(model.getUserIdFromUrl("/user/1fd0") == 0);
    }

    @Test
    public void testGetUserFromUrl() throws Exception {
        assertTrue(model.getUserFromUrl("") == null);
        assertTrue(model.getUserFromUrl("/user/1") != null);
        assertTrue(model.getUserFromUrl("/user/9999") == null);
        assertTrue(model.getUserFromUrl("/sdgsdgsdg/") == null);
    }


}