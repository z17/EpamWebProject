package models;

import dao.ItemDao;
import dao.OrderDao;
import dao.OrderItemDao;
import entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelOrder {
    public Map<Item, Integer> getCurrentOrder(String orderCookieValue) {
        HashMap<Integer, Integer> itemsId = parseOrderCookie(orderCookieValue);
        ItemDao itemDao = new ItemDao();
        ArrayList<Item> items = itemDao.getByArrayId(itemsId.keySet());
        Map<Item, Integer> result = new HashMap<>();
        for (Item item : items) {
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
}
