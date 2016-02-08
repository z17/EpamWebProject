package dao;

import entity.Order;
import entity.OrderStatus;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.Assert.*;

public class OrderDaoTest {
    OrderDao dao = new OrderDao();
    @Test
    public void testGet() throws Exception {
        assertTrue(dao.get().size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        assertTrue(dao.getById(1) != null);
    }

    @Test
    public void testCreate() throws Exception {
        Order order = new Order(1, LocalDateTime.now(), 50, OrderStatus.NEW);
        Order newOrder = dao.create(order);
        assertTrue(dao.getById(newOrder.getId()) != null);
        dao.delete(newOrder.getId());
        assertTrue(dao.getById(newOrder.getId()) == null);
    }

    @Test
    public void testUpdate() throws Exception {
        Order order = new Order(1, LocalDateTime.now(), 50, OrderStatus.NEW);
        Order newOrder = dao.create(order);
        newOrder.setStatus(OrderStatus.EXECUTE);
        dao.update(newOrder);
        assertTrue(dao.getById(newOrder.getId()).getStatus() == OrderStatus.EXECUTE);
        dao.delete(newOrder.getId());
        assertTrue(dao.getById(newOrder.getId()) == null);
    }

    @Test
    public void testDelete() throws Exception {
        Order order = new Order(1, LocalDateTime.now(), 50, OrderStatus.NEW);

        Collection<Order> test1 = dao.get();
        Order newOrder = dao.create(order);
        Collection<Order> test2 = dao.get();
        dao.delete(newOrder.getId());
        Collection<Order> test3 = dao.get();

        assertTrue(test1.size() == test3.size());
        assertTrue(test1.size() + 1 == test2.size());

    }

    @Test
    public void testGetByUserId() throws Exception {

    }

    @Test
    public void testGetOrderByStatusArray() throws Exception {

    }
}