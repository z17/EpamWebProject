package models.orderstrategy;

import dao.OrderDao;
import entity.Order;

/**
 * Удаление
 */
class ActionDelete implements Action {
    @Override
    public Order action(final Order order) {
        OrderDao dao = new OrderDao();
        dao.delete(order.getId());
        return null;
    }
}
