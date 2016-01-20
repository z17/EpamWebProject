package dao;

import cp.ConnectionPool;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class UserDao implements InterfaceDao<User> {
    static String TABLE_NAME = "user";

    @Override
    public Map<Integer, User> get() {
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }

    public User getByLogin(String login) {
        String select = "SELECT id, name, group_id, login, password FROM " + TABLE_NAME + " WHERE login = ? LIMIT 1";
        ConnectionPool pool = ConnectionPool.getInstance();
        User result =  null;
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int groupId = rs.getInt("group_id");
                    String password = rs.getString("password");
                    result = new User(id, name, groupId, login, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int create(User item) {
        int newId = 0;
        String insert = "INSERT INTO " + TABLE_NAME + " (name, group_id, login, password) VALUES (?, ?, ?, ?)";
        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getGroupId());
            ps.setString(3, item.getLogin());
            ps.setString(4, item.getPassword());
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
    public void update(User item) {

    }

    @Override
    public void delete(int id) {

    }

}
