package dao;

import cp.ConnectionPool;
import entity.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class UserDao implements InterfaceDao<User> {
    private static final Logger LOG = Logger.getLogger(UserDao.class);

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
    public User getById(final int id) {
        String select = "SELECT id, name, group_id, login, password, email, phone, address FROM user WHERE id = ? LIMIT 1";
        ConnectionPool pool = ConnectionPool.getInstance();
        User result =  null;
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){
            ps.setInt(1, id);

            ArrayList<User> users = getUsers(ps);
            if (users.size() > 0) {
                result = users.get(0);
            }
        } catch (SQLException e) {
            LOG.error("connection error", e);
        }
        return result;
    }

    /**
     * Пользователь по логину
     * @param login пользователя
     * @return пользователь или null
     */
    public User getByLogin(final String login) {
        String select = "SELECT id, name, group_id, login, password, email, phone, address FROM user WHERE login = ? LIMIT 1";
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

            ArrayList<User> users = getUsers(ps);
            if (users.size() > 0) {
                result = users.get(0);
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
    public User create(final User item) {
        User newUser = null;
        String insert = "INSERT INTO user (name, group_id, login, password) VALUES (?, ?, ?, ?)";
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
                    int newId = rs.getInt(1);
                    newUser = new User(newId, item.getName(), item.getGroup(), item.getLogin(), item.getPassword(), item.getEmail(), item.getPhone(), item.getAddress());
                }
            }

        } catch (SQLException e) {
            LOG.error("connection error", e);
        }
        return newUser;
    }

    /**
     * Обновляет данные пользователя
     * @param item пользователь
     */
    @Override
    public void update(final User item) {
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
    public void delete(final int id) {

    }


    /**
     * Заполняет коллекцию объектов из PreparedStatement
     * @param ps PreparedStatement
     * @return массив заказов
     * @throws SQLException
     */
    private ArrayList<User> getUsers(PreparedStatement ps) throws SQLException {
        ArrayList<User> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String login = rs.getString("login");
                int groupId = rs.getInt("group_id");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                GroupDao groupDao = new GroupDao();
                Group group = groupDao.getById(groupId);
                result.add(new User(id, name, group, login,password, email,phone,address));
            }
        }
        return result;
    }

}
