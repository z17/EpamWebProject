package dao;


import cp.ConnectionPool;
import entity.Item;
import languages.Languages;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class ItemDao implements InterfaceDao<Item> {
    private static final Logger LOG = Logger.getLogger(ItemDao.class);

    // кешируем т.к. item нам нужны постоянно.
    private static LinkedHashMap<Integer, Item> allItems = null;
    private static String TABLE_NAME = "item";

    /**
     * Возвращает все элементы меню
     * @return collection
     */
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

    /**
     * Возвращает массив элементов меню с start до end
     * @param start начало
     * @param end конец
     * @return массив или null
     */
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

    /**
     * Возвращает элемент по его id
     * @param id элемента
     * @return элемент
     */
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

    /**
     * Добавляет в БД новый элемент
     * @param item элемент
     * @return id добавленного
     */
    @Override
    public int create(Item item) {
        allItems = null;
        return 0;
    }

    /**
     * Обновляет элемент в БД
     * @param item обновляемый элемент
     */
    @Override
    public void update(Item item) {
        allItems = null;

    }

    /**
     * Удаляет
     * @param id удаляемого
     */
    @Override
    public void delete(int id) {
        allItems = null;

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
     * Заполняем поле allItems данными из БД для кеширования
     */
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
            LOG.error("connection error", e);
        }
        allItems = result;
    }

    /**
     * @return количество элементов
     */
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
