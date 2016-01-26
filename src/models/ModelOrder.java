package models;

import com.sun.org.apache.xpath.internal.operations.Or;
import dao.BillDao;
import dao.ItemDao;
import dao.OrderDao;
import dao.OrderItemDao;
import entity.*;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelOrder {
    private static final Logger LOG = Logger.getLogger(ModelOrder.class);
    private static String[] ACTIONS = {"executed", "delete", "ready", "close"};

    public Map<Item, Integer> getCurrentOrder(String orderCookieValue) {
        HashMap<Integer, Integer> itemsId = parseOrderCookie(orderCookieValue);
        ItemDao itemDao = new ItemDao();
        Collection<Item> items = itemDao.getByArrayId(itemsId.keySet());
        Map<Item, Integer> result = new HashMap<>();
        for (Item item : items) {
            result.put(item, itemsId.get(item.getId()));
        }
        return result;
    }

    // благодаря тому, что json в куках java не видит, нам приходится так извращаться
    private HashMap<Integer, Integer> parseOrderCookie(String cookie) {

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

    public void createOrder(User currentUser, Map<Item, Integer> items) {
        OrderDao orderDao = new OrderDao();
        int price = 0;
        LocalDateTime time = LocalDateTime.now();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            price += entry.getKey().getPrice() * entry.getValue();
        }
        int orderId = orderDao.create(new Order(currentUser.getId(), time, price, OrderStatus.NEW));

        OrderItemDao orderItemDao = new OrderItemDao();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            orderItemDao.create(new OrderItem(orderId, entry.getKey().getId(), entry.getValue()));
        }
    }

    public ArrayList<Order> getUserOrders(User currentUser) {
        OrderDao orderDao = new OrderDao();
        return orderDao.getByUserId(currentUser.getId());
    }

    public ArrayList<Order> getActiveOrders() {
        OrderDao orderDao = new OrderDao();
        Collection<OrderStatus> listStatus = new ArrayList<>();
        listStatus.add(OrderStatus.NEW);
        listStatus.add(OrderStatus.EXECUTED);
        listStatus.add(OrderStatus.READY);
        return orderDao.getOrderByStatusArray(listStatus);
    }

    public int getOrderIdFromUrl(String requestURI) {
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

    public Order getOrderById(int orderId) {
        OrderDao dao = new OrderDao();
        return dao.getById(orderId);
    }

    public boolean isAdminAccessAllowed(User user) {
        if (user == null) {
            return false;
        }
        // todo: вынести отдельно проверку прав, а не так хардкорить
        return user.getGroupId() == 2;
    }

    public boolean isOrderAccessAllowed(Order order, User user) {
        if (order == null || user == null) {
            return false;
        }
        return order.getUserId() == user.getId() || isAdminAccessAllowed(user);
    }

    public Order doAction(String action, User user, Order order) {
        // корректность данных
        if (order == null || !isValidAction(action)) {
            return order;
        }

        // доступ к выполнению функции
        if (!isAdminAccessAllowed(user) && !isActionAllow(order, action)) {
            return order;
        }

        OrderDao dao = new OrderDao();
        if (action.equals("delete")) {
            dao.delete(order.getId());
            return null;
        }
        if (action.equals("executed")) {
            order.setStatus(OrderStatus.EXECUTED);
        }
        if (action.equals("ready")) {
            createBill(order);
            order.setStatus(OrderStatus.READY);
        }
        if (action.equals("close")) {
            paidBill(order);
            order.setStatus(OrderStatus.PAID);
        }
        dao.update(order);
        return order;
    }

    private void paidBill(Order order) {
        BillDao dao = new BillDao();
        Bill bill = dao.getByOrderId(order.getId());
        bill.setPaid(true);
        dao.update(bill);
    }

    private void createBill(Order order) {
        BillDao dao = new BillDao();
        dao.create(new Bill(order.getId(), false, order.getPrice()));
    }

    private boolean isValidAction(String action) {
        return Arrays.asList(ACTIONS).contains(action);
    }

    private boolean isActionAllow(Order order, String action) {
        // статусы могут меняться
        // с нового заказа на выполнение (или удалить заказ)
        if (order.getStatus() == OrderStatus.NEW && (action.equals("executed") || action.equals("delete"))) {
            return true;
        }
        // с выполнения на готово
        if (order.getStatus() == OrderStatus.EXECUTED && action.equals("ready")) {
            return true;
        }
        // с готово на закрыто
        if (order.getStatus() == OrderStatus.READY && action.equals("close")) {
            return true;
        }

        return false;
    }
}
