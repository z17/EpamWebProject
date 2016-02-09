package dao;

import cp.ConnectionPool;
import entity.Bill;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO для счетов
 */
public class BillDao implements InterfaceDao<Bill> {
    private static final Logger LOG = Logger.getLogger(BillDao.class);

    /**
     * @return Список всеъ счетов
     */
    @Override
    public List<Bill> get() {
        List<Bill> result = null;
        String select = "SELECT id, order_id, paid, sum FROM bill";
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){

            try (ResultSet rs = ps.executeQuery()) {
                result = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    boolean paid = rs.getBoolean("paid");
                    int sum = rs.getInt("sum");
                    int orderId = rs.getInt("order_id");
                    result.add(new Bill(id, orderId, paid, sum));
                }
            }
        } catch (SQLException e) {
            LOG.error("connection error", e);
        }
        return result;
    }

    /**
     * @param id счета
     * @return Счет
     */
    @Override
    public Bill getById(final int id) {
        return null;
    }

    /**
     * Получает счёт по id заказа
     * @param orderId - id заказа
     * @return Счет или null
     */
    public Bill getByOrderId(final int orderId) {
        String select = "SELECT id, order_id, paid, sum FROM bill WHERE order_id = ? LIMIT 1";
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

    /**
     * Добавляет счёт
     * @param item добавляемый в БД счёт
     * @return id нового счета
     */
    @Override
    public Bill create(final Bill item) {
        Bill newBill = null;
        String insert = "INSERT INTO bill (order_id, paid, sum) VALUES (?, ?, ?)";
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
                    int newId = rs.getInt(1);
                    newBill =  new Bill(newId, item.getOrderId(), item.isPaid(), item.getSum());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("connection error", e);
        }
        return newBill;
    }

    /**
     * Обновляет счёт по его id
     * @param item - обновляемый счёт
     */
    @Override
    public void update(final Bill item) {
        String update = "UPDATE bill set order_id = ?, paid = ?, sum = ? WHERE id = ?";

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

    /**
     *
     * @param id счета
     */
    @Override
    public void delete(final int id) {
        // удаляем сам заказ
        String delete = "DELETE FROM bill WHERE id = ?";
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
}
