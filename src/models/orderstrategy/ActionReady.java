package models.orderstrategy;

import dao.BillDao;
import dao.OrderDao;
import entity.Bill;
import entity.Order;
import entity.OrderStatus;

/**
 * Смена статуса на выполнено, выставление счёта
 */
class ActionReady implements Action {
    @Override
    public Order action(Order order) {
        BillDao billDao = new BillDao();
        billDao.create(new Bill(order.getId(), false, order.getPrice()));

        OrderDao orderDao = new OrderDao();
        order.setStatus(OrderStatus.READY);
        orderDao.update(order);
        return order;
    }
}
