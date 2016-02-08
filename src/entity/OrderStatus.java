package entity;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Статусы заказов
 */
public enum OrderStatus {
    NEW(1),
    EXECUTE(2),
    READY(3),
    CLOSE(4);

    private static final Logger LOG = Logger.getLogger(OrderStatus.class);

    private final int value;
    private static Map<Integer, OrderStatus> map = new HashMap<>();

    static {
        for (OrderStatus item : OrderStatus.values()) {
            map.put(item.value, item);
        }
    }

    OrderStatus(int value) {
        this.value = value;
    }

    /**
     * Возврашает int значение каждого статуса, соответствующее значению в БД
     * @return int
     */
    public int getValue() {
        return value;
    }

    /**
     * Возвращает статус по его численному значению
     * @param id значение
     * @return статус
     */
    public static OrderStatus valueOf(int id) {
        OrderStatus result = map.get(id);
        if (result == null) {
            LOG.error("try to get invalid OrderStatus");
        }
        return result;
    }
}
