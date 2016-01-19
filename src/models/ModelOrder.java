package models;

import com.sun.org.apache.xpath.internal.operations.Or;
import dao.ItemDao;
import dao.OrderDao;
import dao.OrderItemDao;
import entity.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelOrder {
    public Map<Item, Integer> getCurrentOrder(String orderCookieValue) {
        HashMap<Integer, Integer> itemsId = parseOrderCookie(orderCookieValue);
        ItemDao itemDao = new ItemDao();
        Map<Integer, Item> items = itemDao.getByArrayId(itemsId.keySet());
        Map<Item, Integer> result = new HashMap<>();
        for (Item item : items.values()) {
            result.put(item, itemsId.get(item.getId()));
        }
        return result;
    }

    // благодаря тому, что json в куках java не видит, нам приходится так извращаться
    private HashMap<Integer, Integer> parseOrderCookie(String cookie) {

        HashMap<Integer, Integer> result = new HashMap<>();

        String patternLinks = "id([0-9]*)=([0-9]*)";
        Pattern patternFindLinks = Pattern.compile(patternLinks);
        Matcher links = patternFindLinks.matcher(cookie);
        while (links.find()) {
            int id = Integer.parseInt(links.group(1));
            int value = Integer.parseInt(links.group(2));
            result.put(id, value);
        }
        return result;
    }

    public void createOrder(User currentUser, Map<Item, Integer> items) {
        OrderDao orderDao = new OrderDao();
        int price = 0;
        LocalDateTime time = LocalDateTime.now();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            price += entry.getKey().getPrice() * entry.getValue();
        }
        int orderId = orderDao.create(new Order(currentUser.getId(), time, price, OrderStatus.NEW));

        OrderItemDao orderItemDao = new OrderItemDao();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            orderItemDao.create(new OrderItem(orderId, entry.getKey().getId(), entry.getValue()));
        }
    }

    public ArrayList<Order> getUserOrders(User currentUser) {
        OrderDao orderDao = new OrderDao();
        return orderDao.getByUserId(currentUser.getId());
    }

    public ArrayList<Order> getActiveOrders() {
        OrderDao orderDao = new OrderDao();
        Collection<OrderStatus> listStatus = new ArrayList<>();
        listStatus.add(OrderStatus.NEW);
        listStatus.add(OrderStatus.EXECUTED);
        listStatus.add(OrderStatus.READY);
        return orderDao.getOrderByStatusArray(listStatus);
    }

    public int getOrderIdFromUrl(String requestURI) {
        String[] path = requestURI.split("/");
        if (path.length >= 3) {
            if (path[1].equals("order")) {
                try {
                    return Integer.parseInt(path[2]);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    public Order getOrderById(int orderId) {
        OrderDao dao = new OrderDao();
        return dao.getById(orderId);
    }

    public boolean isAdminAccessAllowed(User user) {
        if (user == null) {
            return false;
        }
        // todo: вынести отдельно проверку прав, а не так хардкорить
        return user.getGroupId() == 2;
    }

    public boolean isOrderAccessAllowed(Order order, User user) {
        if (order == null || user == null) {
            return false;
        }
        return order.getUserId() == user.getId() || isAdminAccessAllowed(user);
    }
}
