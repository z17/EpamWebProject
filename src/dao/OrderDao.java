package dao;

import cp.ConnectionPool;
import entity.Order;
import entity.OrderItem;
import entity.OrderStatus;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * DAO для заказов
 */
public class OrderDao implements InterfaceDao<Order> {
    private static String TABLE_NAME = "`order`";

    /**
     * Возвращает все заказы
     * @return список заказов
     */
    @Override
    public Collection<Order> get() {
        return null;
    }

    /**
     * Возвращает заказ по id
     * @param id заказа
     * @return заказ
     */
    @Override
    public Order getById(int id) {
        String select = "SELECT \n" +
                "  id,\n" +
                "  user_id,\n" +
                "  price,\n" +
                "  status,\n" +
                "  time\n" +
                "FROM `order` \n" +
                "WHERE id = ? LIMIT 1";
        ConnectionPool pool = ConnectionPool.getInstance();
        Order result =  null;
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){
            ps.setInt(1, id);
            ArrayList<Order> orders = getOrders(ps);
            if (orders.size() > 0) {
                result = orders.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Добавляет новый заказ в БД
     * @param item заказ
     * @return id заказа в БД
     */
    @Override
    public int create(Order item) {
        String insert = "INSERT INTO " + TABLE_NAME + " (user_id, price, status, time) VALUES (?, ?, ?, ?)";
        int newId = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, item.getUserId());
            ps.setInt(2, item.getPrice());
            ps.setInt(3, item.getStatus().getValue());
            ps.setTimestamp(4, Timestamp.valueOf(item.getTime()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    /**
     * Обновляет запись в БД
     * @param item заказ
     */
    @Override
    public void update(Order item) {
        String update = "UPDATE "+TABLE_NAME+" set user_id = ?, price = ?, status = ?, time = ? WHERE id = ?";

        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(update)
        ) {
            ps.setInt(1, item.getUserId());
            ps.setInt(2, item.getPrice());
            ps.setInt(3, item.getStatus().getValue());
            ps.setTimestamp(4, Timestamp.valueOf(item.getTime()));
            ps.setInt(5, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет запись
     * @param id записи
     */
    @Override
    public void delete(int id) {
        // удаляем связи
        OrderItemDao dao = new OrderItemDao();
        dao.deleteByOrderId(id);

        // удаляем сам заказ
        String delete = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        ConnectionPool pool = ConnectionPool.getInstance();

        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(delete)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получает записи по id пользователя
     * @param id заказа
     * @return список заказов
     */
    public ArrayList<Order> getByUserId(int id) {
        String select = "SELECT id, user_id, price, status, time FROM " + TABLE_NAME + " WHERE user_id = ? ORDER BY id DESC";
        ConnectionPool pool = ConnectionPool.getInstance();
        ArrayList<Order> result =  null;
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){
            ps.setInt(1, id);
            result = getOrders(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получает записи по списку стутосов
     * @param listStatus колелкция статусов
     * @return список заказов
     */
    public ArrayList<Order> getOrderByStatusArray(Collection<OrderStatus> listStatus) {
        ArrayList<Integer> listStatusId = new ArrayList<>();
        listStatus.stream().forEach((item) -> listStatusId.add(item.getValue()));
        String statusStr = StringUtils.join(listStatusId, ", ");    // к сожалению mysql не поддерживает setArray и createArrayOf
        String select = "SELECT id, user_id, price, status, time FROM " + TABLE_NAME + " WHERE status in ("+statusStr+") ORDER BY id DESC";

        ConnectionPool pool = ConnectionPool.getInstance();
        ArrayList<Order> result =  null;
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){
            result = getOrders(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Заполняет коллекцию объектов из PreparedStatement
     * @param ps PreparedStatement
     * @return массив заказов
     * @throws SQLException
     */
    private ArrayList<Order> getOrders(PreparedStatement ps) throws SQLException {
        ArrayList<Order> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int userId = rs.getInt("user_Id");
                int price = rs.getInt("price");
                int status = rs.getInt("status");
                LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();
                result.add(new Order(orderId, userId, time, price, OrderStatus.valueOf(status)));
            }
        }

        for (Order one : result) {
            OrderItemDao dao = new OrderItemDao();
            ArrayList<OrderItem> orderItems = dao.getByOrderId(one.getId());

            ItemDao itemDao = new ItemDao();
            for (OrderItem currentOrderItem: orderItems) {
                    one.addItem(currentOrderItem.getCount(), itemDao.getById(currentOrderItem.getIdItem()));
            }
        }
        return result;
    }
}
