package models.orderstrategy;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderActionTest {

    @Test
    public void testNameFromString() throws Exception {
        assertTrue(OrderAction.nameFromString("add") == OrderAction.ADD);
        assertTrue(OrderAction.nameFromString("delete") == OrderAction.DELETE);
        assertTrue(OrderAction.nameFromString("execute") == OrderAction.EXECUTE);
        assertTrue(OrderAction.nameFromString("ready") == OrderAction.READY);
        assertTrue(OrderAction.nameFromString("close") == OrderAction.CLOSE);
        assertTrue(OrderAction.nameFromString("asgfasgose") == null);
    }
}