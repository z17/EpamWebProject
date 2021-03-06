package dao;

import cp.ConnectionPool;
import entity.Group;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GroupDao implements InterfaceDao<Group> {
    private static final Logger LOG = Logger.getLogger(GroupDao.class);
    private static LinkedHashMap<Integer, Group> ALL_GROUPS = null;

    @Override
    public List<Group> get() {
        if (ALL_GROUPS == null) {
            synchronized (GroupDao.class) {
                if (ALL_GROUPS == null) {
                    fillingData();
                }
            }
        }
        return new ArrayList<>(ALL_GROUPS.values());
    }

    @Override
    public Group getById(final int id) {
        if (ALL_GROUPS == null) {
            synchronized (GroupDao.class) {
                if (ALL_GROUPS == null) {
                    fillingData();
                }
            }
        }
        return ALL_GROUPS.get(id);
    }

    @Override
    public Group create(final Group item) {
        throw new NotImplementedException("");
    }

    @Override
    public void update(final Group item) {
        throw new NotImplementedException("");
    }

    @Override
    public void delete(final int id) {
        throw new NotImplementedException("");
    }

    private void fillingData() {
        ALL_GROUPS = new LinkedHashMap<>();
        String select = "SELECT id, name FROM `group` ORDER BY id DESC";
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    ALL_GROUPS.put(id, new Group(id, name));
                }
            }
        } catch (SQLException e) {
            LOG.error("connection error", e);
        }
    }

}