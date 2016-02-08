package models.orderstrategy;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Доступные с Order действия
 */
public enum OrderAction {
    ADD("add"),
    DELETE("delete"),
    EXECUTE("execute"),
    READY("ready"),
    CLOSE("close");

    private static final Logger LOG = Logger.getLogger(OrderAction.class);

    private final String value;
    private static Map<String, OrderAction> map = new HashMap<>();

    static {
        for (OrderAction item : OrderAction.values()) {
            map.put(item.value, item);
        }
    }

    OrderAction(String value) {
        this.value = value;
    }

    /**
     * Возвращает action по его STR названию
     * @param name значение
     * @return статус
     */
    public static OrderAction nameFromString(String name) {
        OrderAction result = map.get(name);
        if (result == null) {
            LOG.error("try to get invalid OrderStatus");
        }
        return result;
    }

}
