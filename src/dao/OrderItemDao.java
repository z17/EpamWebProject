package dao;

import cp.ConnectionPool;
import entity.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OrderItemDao implements InterfaceDao<OrderItem> {
    private static String TABLE_NAME = "order_item";
    @Override
    public Collection<OrderItem> get() {
        return null;
    }

    @Override
    public OrderItem getById(int id) {
        return null;
    }

    @Override
    public int create(OrderItem item) {
        int newId = 0;
        String insert = "INSERT INTO " + TABLE_NAME + " (order_id, item_id, count) VALUES (?, ?, ?)";
        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, item.getIdOrder());
            ps.setInt(2, item.getIdItem());
            ps.setInt(3, item.getCount());
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
    public void update(OrderItem item) {

    }

    @Override
    public void delete(int id) {

    }

    public ArrayList<OrderItem> getByOrderId(int orderId) {
        String select = "SELECT id, item_id, count FROM " + TABLE_NAME + " WHERE order_id = ?";
        ArrayList<OrderItem> result = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int itemId = rs.getInt("item_id");
                    int count = rs.getInt("count");
                    result.add(new OrderItem(id, orderId, itemId, count));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteByOrderId(int orderId) {
        String delete = "DELETE FROM " + TABLE_NAME + " WHERE order_id = ?";
        ConnectionPool pool = ConnectionPool.getInstance();

        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(delete)
        ) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
