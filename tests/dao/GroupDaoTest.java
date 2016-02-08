package dao;

import org.junit.Test;

import static org.junit.Assert.*;

public class GroupDaoTest {
    GroupDao dao = new GroupDao();

    @Test
    public void testGet() throws Exception {
        assertTrue(dao.get().size()>0);
    }

    @Test
    public void testGetById() throws Exception {
        assertTrue(dao.getById(1) != null);
    }
}