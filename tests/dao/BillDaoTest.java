package dao;

import entity.Bill;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class BillDaoTest {
    BillDao dao = new BillDao();

    @Test
    public void testGet() throws Exception {
        Collection<Bill> test = dao.get();
        assertTrue(test.size() > 0);
    }


    @Test
    public void testGetByOrderId() throws Exception {
        Bill test = dao.getByOrderId(4);
        assertTrue(test != null);
    }

    @Test
    public void testCreate() throws Exception {
        Bill bill = new Bill(1, true, 50);
        int id = dao.create(bill);
        Bill getBill = dao.getByOrderId(1);
        assertTrue(bill.getOrderId() == getBill.getOrderId());
        dao.delete(getBill.getId());
    }


    @Test
    public void testDelete() throws Exception {
        Bill bill = new Bill(1, true, 50);

        Collection<Bill> test1 = dao.get();
        int id = dao.create(bill);
        Collection<Bill> test2 = dao.get();
        dao.delete(id);
        Collection<Bill> test3 = dao.get();

        assertTrue(test1.size() == test3.size());
        assertTrue(test1.size() + 1 == test2.size());
    }
}