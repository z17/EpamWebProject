package dao;

import cp.ConnectionPool;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao implements InterfaceDao<User> {
    static String TABLE_NAME = "user";

    @Override
    public ArrayList<User> get() {
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
    public void create(User item) {
        String insert = "INSERT INTO " + TABLE_NAME + " (name, group_id, login, password) VALUES (?, ?, ?, ?)";
        System.out.println(insert);
        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(insert)
        ) {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getGroupId());
            ps.setString(3, item.getLogin());
            ps.setString(4, item.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User item) {

    }

    @Override
    public void delete(int id) {

    }

}
