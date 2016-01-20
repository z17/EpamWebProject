package models;

import models.ModelOrder;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelOrderTest {

    @Test
    public void testGetOrderId() throws Exception {
        ModelOrder model = new ModelOrder();

        assertTrue(model.getOrderIdFromUrl("/order/123") == 123);
        assertTrue(model.getOrderIdFromUrl("/order123") == 0);
        assertTrue(model.getOrderIdFromUrl("/order/asfasf23") == 0);
    }
}