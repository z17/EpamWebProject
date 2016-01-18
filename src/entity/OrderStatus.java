package entity;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
    NEW(1),
    EXECUTED(2),
    READY(3),
    PAID(4);

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
        //todo: что делать если значение не найдено?
        return map.get(id);
    }
}
