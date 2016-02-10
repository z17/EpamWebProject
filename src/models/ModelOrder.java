package models;

import dao.ItemDao;
import dao.OrderDao;
import entity.*;
import models.orderstrategy.ActionStrategy;
import models.orderstrategy.OrderAction;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Модель, отвечающая за работу с заказами
 */
public class ModelOrder {
    private static final Logger LOG = Logger.getLogger(ModelOrder.class);

    /**
     * Получает текущее состояние корзины
     * @param cookies строка вида id[id элемента]=[количество]
     * @return Ассоциативный массив элемент => количество
     */
    public Map<Item, Integer> getCurrentOrder(final Cookie[] cookies) {
        String orderCookieValue = getCookieValue(cookies);
        if (orderCookieValue != null) {
            HashMap<Integer, Integer> itemsId = parseOrderCookie(orderCookieValue);
            ItemDao itemDao = new ItemDao();
            Collection<Item> items = itemDao.getByArrayId(itemsId.keySet());
            Map<Item, Integer> result = new HashMap<>();
            for (Item item : items) {
                result.put(item, itemsId.get(item.getId()));
            }
            return result;
        }
        return null;
    }


    /**
     * Создаём заказ
     * @param user пользователь, который создаёт
     * @param items его заказ вида элемент => количество
     */
    public void createOrder(final User user, final Map<Item, Integer> items) {
        int price = 0;
        LocalDateTime time = LocalDateTime.now();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            price += entry.getKey().getPrice() * entry.getValue();
        }
        Order newOrder = new Order(user.getId(), time, price, OrderStatus.NEW);
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            newOrder.addItem(entry.getValue(), entry.getKey());
        }
        ActionStrategy strategy = new ActionStrategy();
        strategy.setAction(OrderAction.ADD);
        strategy.doAction(newOrder);
    }

    /**
     * Получаем заказы пользователя
     * @param user пользователь
     * @return список заказов
     */
    public List<Order> getUserOrders(final User user) {
        OrderDao orderDao = new OrderDao();
        return orderDao.getByUserId(user.getId());
    }

    /**
     * Получаем все активные заказы
     * @return список заказов
     */
    public List<Order> getActiveOrders() {
        OrderDao orderDao = new OrderDao();
        Collection<OrderStatus> listStatus = new ArrayList<>();
        listStatus.add(OrderStatus.NEW);
        listStatus.add(OrderStatus.EXECUTE);
        listStatus.add(OrderStatus.READY);
        return orderDao.getOrderByStatusArray(listStatus);
    }

    /**
     * Проверяем имеется ли у пользователя админский доступ
     * @param user пользователь
     * @return true or false
     */
    public boolean isAdminAccessAllowed(final User user) {
        if (user == null) {
            return false;
        }
        return user.isAdminAccess();
    }

    /**
     * Проверяем имеет ли пользователь доступ к заказу
     * @param order заказ
     * @param user пользователь
     * @return true or false
     */
    public boolean isOrderAccessAllowed(final Order order, User user) {
        if (order == null || user == null) {
            return false;
        }
        return order.getUserId() == user.getId() || isAdminAccessAllowed(user);
    }

    /**
     * Выполнение действия с заказом
     * @param action название действия
     * @param user пользователь
     * @param order заказ
     * @return возвращает изменённый заказ (или null в случае удаления)
     */
    public Order doAction(final String action, final User user, final Order order) {
        // корректность данных
        if (order == null) {
            return null;
        }

        // доступ к выполнению функции
        if (!isAdminAccessAllowed(user) && !isActionAllow(order, action)) {
            return order;
        }

        ActionStrategy strategy = new ActionStrategy();
        strategy.setAction(OrderAction.nameFromString(action));
        return strategy.doAction(order);
    }

    /**
     * Возвращает заказ по его адресу или null
     * @param url адрес
     * @return заказ
     */
    public Order getSingleOrder(String url) {
        if (url == null) {
            return null;
        }
        int orderId = getOrderIdFromUrl(url);
        if (orderId == 0) {
            return null;
        }
        Order order = getOrderById(orderId);
        return order;
    }

    /**
     * Проверяем разрешено ли действие в текущем состоянии заказа
     * @param order заказ
     * @param action действие
     * @return true or false
     */
    private boolean isActionAllow(final Order order, final String action) {
        // статусы могут меняться
        // с нового заказа на выполнение (или удалить заказ)
        if (order.getStatus() == OrderStatus.NEW && (action.equals("executed") || action.equals("delete"))) {
            return true;
        }
        // с выполнения на готово
        if (order.getStatus() == OrderStatus.EXECUTE && action.equals("ready")) {
            return true;
        }
        // с готово на закрыто
        if (order.getStatus() == OrderStatus.READY && action.equals("close")) {
            return true;
        }

        return false;
    }

    /**
     * Парсит строку с заказом для получения значения id элемента - количество
     * @param cookie строка вида id[id элемента]=[количество]
     * @return Карта вида id => количество
     */
    // благодаря тому, что json в куках java не видит, нам приходится так извращаться
    private HashMap<Integer, Integer> parseOrderCookie(final String cookie) {

        HashMap<Integer, Integer> result = new HashMap<>();

        String patternLinks = "id([0-9]*)=([0-9]*)";
        Pattern patternFindLinks = Pattern.compile(patternLinks);
        Matcher links = patternFindLinks.matcher(cookie);
        while (links.find()) {
            int id = Integer.parseInt(links.group(1));
            int value = Integer.parseInt(links.group(2));
            result.put(id, value);
        }
        return result;
    }

    private String getCookieValue(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie current : cookies) {
                if (current.getName().equals("order")) {
                    return current.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Получаем id заказа из url или 0 если некорректный url
     * @param requestURI url вида /order/[0-9]*
     * @return id заказа или 0
     *
     */
    private int getOrderIdFromUrl(final String requestURI) {
        String[] path = requestURI.split("/");
        if (path.length >= 3) {
            if (path[1].equals("order")) {
                try {
                    return Integer.parseInt(path[2]);
                } catch (NumberFormatException e) {
                    LOG.warn("error format page number");
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * Получаем заказ по id
     * @param orderId id заказа
     * @return заказ
     */
    private Order getOrderById(final int orderId) {
        OrderDao dao = new OrderDao();
        return dao.getById(orderId);
    }

}
