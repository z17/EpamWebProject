package dao;


import cp.ConnectionPool;
import entity.Item;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ItemDao implements InterfaceDao<Item> {
    static String TABLE_NAME = "item";

    @Override
    public ArrayList<Item> get() {
        ArrayList<Item> result = new ArrayList<>();
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
        return result;
    }

    public ArrayList<Item> getByArrayId(Collection<Integer> ids) {
        ArrayList<Item> result = new ArrayList<>();

        ArrayList<Integer> t = new ArrayList<>(ids);
        String idsStr = StringUtils.join(ids, ", ");    // к сожалению mysql не поддерживает setArray и createArrayOf
        String select = "SELECT id, name, in_stock, price, description, image FROM " + TABLE_NAME + " WHERE id in ("+idsStr+")";
        System.out.println(select);
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

    private ArrayList<Item> getItemsList(PreparedStatement ps) throws SQLException {
        ArrayList<Item> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boolean inStock = rs.getBoolean("in_stock");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                String image = rs.getString("image");
                result.add(new Item(id, name, inStock, price, description, image));
            }
        }
        return result;
    }

    @Override
    public Item getById(int id) {
        return null;
    }

    @Override
    public void create(Item item) {

    }

    @Override
    public void update(Item item) {

    }

    @Override
    public void delete(int id) {

    }
}
