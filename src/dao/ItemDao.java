package dao;


import cp.ConnectionPool;
import entity.Item;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class ItemDao implements InterfaceDao<Item> {
    private static final Logger LOG = Logger.getLogger(ItemDao.class);

    // кешируем т.к. item нам нужны постоянно.
    private static LinkedHashMap<Integer, Item> ALL_ITEMS = null;
    private static String TABLE_NAME = "item";

    /**
     * Возвращает все элементы меню
     * @return collection
     */
    @Override
    public Collection<Item> get() {
        if (ALL_ITEMS == null) {
            synchronized (ItemDao.class) {
                if (ALL_ITEMS == null) {
                    fillingData();
                }
            }
        }
        return ALL_ITEMS.values();
    }

    /**
     * Возвращает массив элементов меню с start до end
     * @param start начало
     * @param end конец
     * @return массив или null
     */
    public ArrayList<Item> get(int start, int end) {
        if (ALL_ITEMS == null) {
            synchronized (ItemDao.class) {
                if (ALL_ITEMS == null) {
                    fillingData();
                }
            }
        }

        ArrayList<Item> res = new ArrayList<>();
        res.addAll(ALL_ITEMS.values());
        if (start > res.size()) {
            return null;
        }

        if (end > res.size()) {
            end = res.size();
        } else {
            end--;
        }
        start--;
        return new ArrayList<>( res.subList(start, end));

    }

    /**
     * Возвращает коллекцию элементов по списку id
     * @param ids массив id
     * @return соответствующие элементы
     */
    public Collection<Item> getByArrayId(final Collection<Integer> ids) {
        if (ALL_ITEMS == null) {
            synchronized (ItemDao.class) {
                if (ALL_ITEMS == null) {
                    fillingData();
                }
            }
        }

        Collection<Item> result = new ArrayList<>();
        for (Integer id: ids) {
            Item current = ALL_ITEMS.get(id);
            if (current != null) {
                result.add(current);
            }
        }
        return result;
    }

    /**
     * Возвращает элемент по его id
     * @param id элемента
     * @return элемент
     */
    @Override
    public Item getById(final int id) {
        if (ALL_ITEMS == null) {
            synchronized (ItemDao.class) {
                if (ALL_ITEMS == null) {
                    fillingData();
                }
            }
        }
        return ALL_ITEMS.get(id);
    }

    /**
     * Добавляет в БД новый элемент
     * @param item элемент
     * @return id добавленного
     */
    @Override
    public int create(final Item item) {
        ALL_ITEMS = null;
        return 0;
    }

    /**
     * Обновляет элемент в БД
     * @param item обновляемый элемент
     */
    @Override
    public void update(final Item item) {
        ALL_ITEMS = null;

    }

    /**
     * Удаляет
     * @param id удаляемого
     */
    @Override
    public void delete(final int id) {
        ALL_ITEMS = null;

    }


    /**
     * Обрабатывает PreparedStatement возвращая коллекцию элемеентов или null
     * @param ps PreparedStatement
     * @return Коллекция элементов
     * @throws SQLException
     */
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

    /**
     * @return количество элементов
     */
    public int getNumber() {
        if (ALL_ITEMS == null) {
            synchronized (ItemDao.class) {
                if (ALL_ITEMS == null) {
                    fillingData();
                }
            }
        }
        return ALL_ITEMS.size();
    }

    /**
     * Заполняем поле ALL_ITEMS данными из БД для кеширования
     */
    private void fillingData() {
        LinkedHashMap<Integer, Item> result = new LinkedHashMap<>();
        String select = "SELECT id, name, in_stock, price, description, image FROM item ORDER BY id DESC";
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
            LOG.error("connection error", e);
        }
        ALL_ITEMS = result;
    }

}
