package dao;

import cp.ConnectionPool;
import entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        ){
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
        } catch (SQLException e) {
            e.printStackTrace();
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
