package models.orderstrategy;

import dao.OrderDao;
import dao.OrderItemDao;
import entity.Item;
import entity.Order;
import entity.OrderItem;

import java.util.Map;

public class ActionAdd implements Action {
    @Override
    public Order action(Order order) {
        OrderDao dao = new OrderDao();
        int orderId = dao.create(order);
        OrderItemDao orderItemDao = new OrderItemDao();
        for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
            orderItemDao.create(new OrderItem(orderId, entry.getKey().getId(), entry.getValue()));
        }
        return order;
    }
}
