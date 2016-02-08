package dao;

import entity.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class ItemDaoTest {
    ItemDao dao = new ItemDao();

    @Test
    public void testGet() throws Exception {
        assertTrue(dao.get().size() > 0);
    }

    @Test
    public void testGet1() throws Exception {
        assertTrue(dao.get(1,2).size() > 0);
    }

    @Test
    public void testGetByArrayId() throws Exception {
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);
        Collection<Item> test = dao.getByArrayId(array);
        assertTrue(test.size()>0);
    }

    @Test
    public void testGetById() throws Exception {
        assertTrue(dao.getById(1) != null);
    }

    @Test
    public void testGetNumber() throws Exception {
        assertTrue(dao.get().size() == dao.getNumber());
    }
}