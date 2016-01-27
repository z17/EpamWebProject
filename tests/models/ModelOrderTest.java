package models;

import models.ModelOrder;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelOrderTest {

    @Test
    public void testGetOrderIdFromUrl() throws Exception {
        ModelOrder model = new ModelOrder();

        assertTrue(model.getOrderIdFromUrl("/order/123") == 123);
        assertTrue(model.getOrderIdFromUrl("/order123") == 0);
        assertTrue(model.getOrderIdFromUrl("/order/asfasf23") == 0);
    }

    @Test
    public void testGetCurrentOrder() throws Exception {

    }

    @Test
    public void testCreateOrder() throws Exception {

    }

    @Test
    public void testGetUserOrders() throws Exception {

    }

    @Test
    public void testGetActiveOrders() throws Exception {

    }

    @Test
    public void testGetOrderById() throws Exception {

    }

    @Test
    public void testIsAdminAccessAllowed() throws Exception {

    }

    @Test
    public void testIsOrderAccessAllowed() throws Exception {

    }

    @Test
    public void testDoAction() throws Exception {

    }
}