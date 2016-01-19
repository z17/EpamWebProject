package dao;


import cp.ConnectionPool;
import entity.Item;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemDao implements InterfaceDao<Item> {
    // кешируем т.к. item нам нужны постоянно.
    private static Map<Integer, Item> allItems = null;
    private static String TABLE_NAME = "item";

    @Override
    public Map<Integer, Item> get() {
        if (allItems != null) {
            synchronized (ItemDao.class) {
                if (allItems != null) {
                    return allItems;
                }
            }
        }

        Map<Integer, Item> result = new HashMap<>();
        String select = "SELECT id, name, in_stock, price, description, image FROM " + TABLE_NAME;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ) {
            result = getItemsList(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allItems = result;
        return result;
    }

    public Map<Integer, Item> getByArrayId(Collection<Integer> ids) {
        Map<Integer, Item> result = null;

        String idsStr = StringUtils.join(ids, ", ");    // к сожалению mysql не поддерживает setArray и createArrayOf
        String select = "SELECT id, name, in_stock, price, description, image FROM " + TABLE_NAME + " WHERE id in ("+idsStr+")";
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ) {
            result = getItemsList(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<Integer, Item> getItemsList(PreparedStatement ps) throws SQLException {
        Map<Integer, Item> result = new HashMap<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boolean inStock = rs.getBoolean("in_stock");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                String image = rs.getString("image");
                result.put(id, new Item(id, name, inStock, price, description, image));
            }
        }
        return result;
    }

    @Override
    public Item getById(int id) {
        return null;
    }

    @Override
    public int create(Item item) {
        allItems = null;
        return 0;
    }

    @Override
    public void update(Item item) {
        allItems = null;

    }

    @Override
    public void delete(int id) {
        allItems = null;

    }
}
