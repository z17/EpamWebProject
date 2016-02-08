package models.orderstrategy;

import dao.BillDao;
import dao.OrderDao;
import entity.Bill;
import entity.Order;
import entity.OrderStatus;

/**
 * Смена статуса на "закрыто", оплата счета
 */
class ActionClose implements Action {
    @Override
    public Order action(Order order) {

        BillDao billDao = new BillDao();
        Bill bill = billDao.getByOrderId(order.getId());
        bill.setPaid(true);
        billDao.update(bill);

        OrderDao orderDao = new OrderDao();
        order.setStatus(OrderStatus.CLOSE);
        orderDao.update(order);
        return order;
    }
}
