package models.orderstrategy;

import edu.umd.cs.findbugs.annotations.NonNull;
import entity.Order;
import org.apache.log4j.Logger;

/**
 * Стратегия для действий с заказами
 */
public class ActionStrategy {
    private Action action;

    private static final Logger LOG = Logger.getLogger(ActionStrategy.class);

    /**
     * Установка метода стратегии
     * @param actionName enum метода
     */
    public void setAction(@NonNull OrderAction actionName) {
        switch (actionName) {
            case ADD:
                action = new ActionAdd();
                break;
            case DELETE:
                action = new ActionDelete();
                break;
            case EXECUTE:
                action = new ActionExecute();
                break;
            case READY:
                action = new ActionReady();
                break;
            case CLOSE:
                action = new ActionClose();
                break;
            default:
                LOG.error("error order select strategy");
        }
    }

    /**
     * Выполнение метода над order
     * @param order заказ
     * @return заказ
     */
    public Order doAction(Order order) {
        if (action != null) {
            return action.action(order);
        } else {
            LOG.error("NULL action");
            return null;
        }
    }
}
