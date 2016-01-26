package dao;

import cp.ConnectionPool;
import entity.Bill;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.Map;

public class BillDao implements InterfaceDao<Bill> {
    private static final Logger LOG = Logger.getLogger(BillDao.class);
    private static String TABLE_NAME = "bill";

    @Override
    public Collection<Bill> get() {
        return null;
    }

    @Override
    public Bill getById(int id) {
        return null;
    }

    public Bill getByOrderId(int orderId) {
        String select = "SELECT id, order_id, paid, sum FROM " + TABLE_NAME + " WHERE order_id = ? LIMIT 1";
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
                if (rs.next()) {
                    int id = rs.getInt("id");
                    boolean paid = rs.getBoolean("paid");
                    int sum = rs.getInt("sum");
                    return new Bill(id, orderId, paid, sum);
                }
            }
        } catch (SQLException e) {
            LOG.error("connection error", e);
        }
        return null;
    }

    @Override
    public int create(Bill item) {
        int newId = 0;
        String insert = "INSERT INTO " + TABLE_NAME + " (order_id, paid, sum) VALUES (?, ?, ?)";
        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, item.getOrderId());
            ps.setBoolean(2, item.isPaid());
            ps.setInt(3, item.getSum());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            LOG.error("connection error", e);
        }
        return newId;
    }

    @Override
    public void update(Bill item) {
        String update = "UPDATE "+TABLE_NAME+" set order_id = ?, paid = ?, sum = ? WHERE id = ?";

        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(update)
        ) {
            ps.setInt(1, item.getOrderId());
            ps.setBoolean(2, item.isPaid());
            ps.setInt(3, item.getSum());
            ps.setInt(4, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("connection error", e);
        }

    }

    @Override
    public void delete(int id) {

    }
}
