package dao;

import cp.ConnectionPool;
import entity.Order;
import entity.OrderStatus;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderDao implements InterfaceDao<Order> {
    private static String TABLE_NAME = "`order`";
    @Override
    public ArrayList<Order> get() {
        return null;
    }

    @Override
    public Order getById(int id) {
        String select = "SELECT id, user_id, price, status, time FROM " + TABLE_NAME + " WHERE id = ? LIMIT 1";
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
            result = getOrders(ps).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

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

    @Override
    public void update(Order item) {

    }

    @Override
    public void delete(int id) {

    }

    public ArrayList<Order> getByUserId(int id) {
        String select = "SELECT id, user_id, price, status, time FROM " + TABLE_NAME + " WHERE user_id = ?";
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

    public ArrayList<Order> getOrderByStatusArray(Collection<OrderStatus> listStatus) {
        ArrayList<Integer> listStatusId = new ArrayList<>();
        listStatus.stream().forEach((item) -> listStatusId.add(item.getValue()));
        String statusStr = StringUtils.join(listStatusId, ", ");    // к сожалению mysql не поддерживает setArray и createArrayOf
        String select = "SELECT id, user_id, price, status, time FROM " + TABLE_NAME + " WHERE status in ("+statusStr+")";

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

    private ArrayList<Order> getOrders(PreparedStatement ps) throws SQLException {
        ArrayList<Order> result;
        try (ResultSet rs = ps.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int userId = rs.getInt("user_Id");
                int price = rs.getInt("price");
                int status = rs.getInt("status");
                LocalDateTime time = rs.getTimestamp("time").toLocalDateTime();
                result.add(new Order(orderId, userId, time, price, OrderStatus.valueOf(status)));
            }
        }
        return result;
    }
}
