package models.orderstrategy;

import entity.Order;
import entity.OrderStatus;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ActionStrategyTest {
    ActionStrategy strategy = new ActionStrategy();

    @Test
    public void testSetAction() throws Exception {
        Field f;
        Action currentAction;
        f = strategy.getClass().getDeclaredField("action");

        strategy.setAction(OrderAction.ADD);
        f.setAccessible(true);
        currentAction = (Action) f.get(strategy);
        assertTrue(currentAction instanceof ActionAdd);

        strategy.setAction(OrderAction.READY);
        f.setAccessible(true);
        currentAction = (Action) f.get(strategy);
        assertTrue(currentAction instanceof ActionReady);

        strategy.setAction(OrderAction.EXECUTE);
        f.setAccessible(true);
        currentAction = (Action) f.get(strategy);
        assertTrue(currentAction instanceof ActionExecute);

        strategy.setAction(OrderAction.DELETE);
        f.setAccessible(true);
        currentAction = (Action) f.get(strategy);
        assertTrue(currentAction instanceof ActionDelete);

        strategy.setAction(OrderAction.CLOSE);
        f.setAccessible(true);
        currentAction = (Action) f.get(strategy);
        assertTrue(currentAction instanceof ActionClose);
    }

    @Test
    public void testDoAction() throws Exception {
        Order order = new Order(1, LocalDateTime.now(), 50, OrderStatus.NEW);

        strategy.setAction(OrderAction.ADD);
        Order newOrder =  strategy.doAction(order);
        assertTrue(newOrder.getTime().equals(order.getTime()));

        strategy.setAction(OrderAction.READY);
        newOrder = strategy.doAction(newOrder);
        assertTrue(newOrder.getStatus() == OrderStatus.READY);

        strategy.setAction(OrderAction.EXECUTE);
        newOrder = strategy.doAction(newOrder);
        assertTrue(newOrder.getStatus() == OrderStatus.EXECUTE);

        strategy.setAction(OrderAction.CLOSE);
        newOrder = strategy.doAction(newOrder);
        assertTrue(newOrder.getStatus() == OrderStatus.CLOSE);

        strategy.setAction(OrderAction.DELETE);
        newOrder = strategy.doAction(newOrder);
        assertTrue(newOrder == null);

    }
}