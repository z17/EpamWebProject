package models;

import dao.UserDao;
import entity.Group;
import entity.Order;
import entity.OrderStatus;
import entity.User;
import models.ModelOrder;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.junit.Assert.*;

public class ModelOrderTest {
    ModelOrder model = new ModelOrder();

    @Test
    public void testGetCurrentOrder() throws Exception {
        Cookie[] cookies1 = new Cookie[1];
        cookies1[0] = new Cookie("123", "123");
        assertTrue(model.getCurrentOrder(cookies1) == null);

        Cookie[] cookies2 = new Cookie[1];
        cookies2[0] = new Cookie("order", "123");
        assertTrue(model.getCurrentOrder(cookies2).size() == 0);


        Cookie[] cookies3 = new Cookie[2];
        cookies3[0] = new Cookie("ordeasfr", "12asf3");
        cookies3[1] = new Cookie("order", "id1=1,id2=3");
        assertTrue(model.getCurrentOrder(cookies3).size() == 2);
    }


    @Test
    public void testGetUserOrders() throws Exception {
        assertTrue(model.getUserOrders(new User(1, "", new Group(1, "admin"), "", "", "", "", "")).size() > 0);
        assertTrue(model.getUserOrders(new User(555, "", new Group(1, "admin"), "", "", "", "", "")).size() == 0);
    }

    @Test
    public void testGetActiveOrders() throws Exception {
        assertTrue(model.getActiveOrders().size() > 0);
        for (Order item : model.getActiveOrders()) {
            assertTrue(item.getStatus() != OrderStatus.CLOSE);
        }
    }

    @Test
    public void testIsAdminAccessAllowed() throws Exception {
        assertTrue(model.isAdminAccessAllowed(new User(1, "", new Group(2, "admin"), "", "", "", "", "")));
        assertFalse(model.isAdminAccessAllowed(new User(1, "", new Group(1, "admin"), "", "", "", "", "")));
    }

    @Test
    public void testIsOrderAccessAllowed() throws Exception {
        Order order1 = model.getSingleOrder("/order/1");
        UserDao dao = new UserDao();
        User user1 = dao.getById(1);
        User user2 = dao.getById(2);
        assertTrue(model.isOrderAccessAllowed(order1, user1));
        assertTrue(model.isOrderAccessAllowed(order1, user2));

        Order order2 = model.getSingleOrder("/order/29");
        assertTrue(model.isOrderAccessAllowed(order2, user1));
        assertFalse(model.isOrderAccessAllowed(order2, user2));
    }


    @Test
    public void testGetSingleOrder() throws Exception {
        Order order1 = model.getSingleOrder("/order/1");
        Order order2 = model.getSingleOrder("/order/3");
        Order order3 = model.getSingleOrder("/ordsdggsd");
        Order order4 = model.getSingleOrder("");
        Order order5 = model.getSingleOrder(null);

        assertTrue(order1.getId() == 1);
        assertTrue(order2.getId() == 3);
        assertTrue(order3 == null);
        assertTrue(order4 == null);
        assertTrue(order5 == null);

    }
}