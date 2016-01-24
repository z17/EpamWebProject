package dao;


import cp.ConnectionPool;
import entity.Item;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.*;

public class ItemDao implements InterfaceDao<Item> {
    // кешируем т.к. item нам нужны постоянно.
    private static LinkedHashMap<Integer, Item> allItems = null;
    private static String TABLE_NAME = "item";

    @Override
    public Collection<Item> get() {
        if (allItems == null) {
            synchronized (ItemDao.class) {
                if (allItems == null) {
                    fillingData();
                }
            }
        }
        return allItems.values();
    }

    public ArrayList<Item> get(int start, int end) {
        if (allItems == null) {
            synchronized (ItemDao.class) {
                if (allItems == null) {
                    fillingData();
                }
            }
        }

        ArrayList<Item> res = new ArrayList<>();
        res.addAll(allItems.values());

        if (end > res.size()) {
            end = res.size();
        } else {
            end--;
        }
        start--;
        return new ArrayList<>( res.subList(start, end));

    }

    public Collection<Item> getByArrayId(Collection<Integer> ids) {
        if (allItems == null) {
            synchronized (ItemDao.class) {
                if (allItems == null) {
                    fillingData();
                }
            }
        }

        Collection<Item> result = new ArrayList<>();
        for (Integer id: ids) {
            Item current = allItems.get(id);
            if (current != null) {
                result.add(current);
            }
        }
        return result;
    }

    @Override
    public Item getById(int id) {
        if (allItems == null) {
            synchronized (ItemDao.class) {
                if (allItems == null) {
                    fillingData();
                }
            }
        }
        return allItems.get(id);
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


    private LinkedHashMap<Integer, Item> getItemsList(PreparedStatement ps) throws SQLException {
        LinkedHashMap<Integer, Item> result = new LinkedHashMap<>();
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

    private void fillingData() {
        LinkedHashMap<Integer, Item> result = new LinkedHashMap<>();
        String select = "SELECT id, name, in_stock, price, description, image FROM " + TABLE_NAME + " ORDER BY id DESC";
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
    }

    public int getNumber() {

        if (allItems == null) {
            synchronized (ItemDao.class) {
                if (allItems == null) {
                    fillingData();
                }
            }
        }
        return allItems.size();
    }
}
