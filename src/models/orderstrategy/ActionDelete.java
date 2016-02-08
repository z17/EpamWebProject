package models.orderstrategy;

import dao.BillDao;
import dao.OrderDao;
import entity.Bill;
import entity.Order;

/**
 * Удаление, сначала счетов, связанных с заказом, затем сам заказ
 */
class ActionDelete implements Action {
    @Override
    public Order action(final Order order) {
        OrderDao dao = new OrderDao();
        dao.delete(order.getId());
        return null;
    }
}
