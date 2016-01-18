package dao;

import cp.ConnectionPool;
import entity.OrderItem;

import java.sql.*;
import java.util.ArrayList;

public class OrderItemDao implements InterfaceDao<OrderItem> {
    private static String TABLE_NAME = "order_item";
    @Override
    public ArrayList<OrderItem> get() {
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
        System.out.println(insert);
        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            System.out.println(item.getIdOrder());
            System.out.println(item.getIdItem());
            System.out.println(item.getCount());
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
}
