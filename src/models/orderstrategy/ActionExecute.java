package models.orderstrategy;

import dao.OrderDao;
import entity.Order;
import entity.OrderStatus;

/**
 * Смена статуса на выполняется
 */
class ActionExecute implements Action {
    @Override
    public Order action(final Order order) {
        OrderDao dao = new OrderDao();
        order.setStatus(OrderStatus.EXECUTE);
        dao.update(order);
        return order;
    }
}
