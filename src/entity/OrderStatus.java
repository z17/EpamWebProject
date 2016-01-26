package entity;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
    NEW(1),
    EXECUTED(2),
    READY(3),
    PAID(4);

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

    public int getValue() {
        return value;
    }

    public static OrderStatus valueOf(int id) {
        OrderStatus result = map.get(id);
        if (result == null) {
            LOG.error("try to get invalid OrderStatus");
        }
        return result;
    }
}
