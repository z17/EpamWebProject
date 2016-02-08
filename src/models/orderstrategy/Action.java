package models.orderstrategy;

import entity.Order;

/**
 * Интерфейс действия над заказом
 */
interface Action {
    Order action(Order order);
}
