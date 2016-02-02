package dao;

import cp.ConnectionPool;
import entity.Group;
import entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Collection;

public class UserDao implements InterfaceDao<User> {
    private static final Logger LOG = Logger.getLogger(UserDao.class);
    static String TABLE_NAME = "user";

    /**
     * @return Списоз пользователей
     */
    @Override
    public Collection<User> get() {
        return null;
    }

    /**
     * Пользователь по id
     * @param id пользователя
     * @return пользователь
     */
    @Override
    public User getById(int id) {
        return null;
    }

    /**
     * Пользователь по логину
     * @param login пользователя
     * @return пользователь или null
     */
    public User getByLogin(String login) {
        String select = "SELECT user.id, user.name, group_id, login, password, email, phone, address FROM user WHERE login = ? LIMIT 1";
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
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    GroupDao groupDao = new GroupDao();
                    Group currentGroup = groupDao.getById(groupId);
                    result = new User(id, name, currentGroup, login, password, email, phone, address);
                }
            }
        } catch (SQLException e) {
            LOG.error("connection error", e);
        }
        return result;
    }

    /**
     * Создаёт пользователя
     * @param item пользователь
     * @return id нового пользователя
     */
    @Override
    public int create(User item) {
        int newId = 0;
        String insert = "INSERT INTO " + TABLE_NAME + " (name, group_id, login, password) VALUES (?, ?, ?, ?)";
        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getGroup().getId());
            ps.setString(3, item.getLogin());
            ps.setString(4, item.getPassword());
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

    /**
     * Обновляет данные пользователя
     * @param item пользователь
     */
    @Override
    public void update(User item) {
        String update = "UPDATE user set group_id = ?, login = ?, password = ?, name = ?, email = ?, phone = ?, address = ? WHERE id = ?";

        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(update)
        ) {
            ps.setInt(1, item.getGroup().getId());
            ps.setString(2, item.getLogin());
            ps.setString(3, item.getPassword());
            ps.setString(4, item.getName());
            ps.setString(5, item.getEmail());
            ps.setString(6, item.getPhone());
            ps.setString(7, item.getAddress());
            ps.setInt(8, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет пользователя
     * @param id пользователя
     */
    @Override
    public void delete(int id) {

    }

}
