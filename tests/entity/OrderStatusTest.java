package entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderStatusTest {

    @Test
    public void testGetValue() throws Exception {
        assertTrue(OrderStatus.valueOf(2) == OrderStatus.EXECUTE);
        assertTrue(OrderStatus.valueOf(50) == null);
    }

    @Test
    public void testValueOf() throws Exception {
        assertTrue(OrderStatus.EXECUTE.getValue() == 2);
    }
}