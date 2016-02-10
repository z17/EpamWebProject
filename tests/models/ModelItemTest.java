package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModelItemTest {
    ModelItem model = new ModelItem();

    @Test
    public void testGetPageNumber() throws Exception {

        assertTrue(model.getPageNumber("") == 1);
        assertTrue(model.getPageNumber("/") == 1);
        assertTrue(model.getPageNumber("/page/") == 1);
        assertTrue(model.getPageNumber("/page/0") == 1);
        assertTrue(model.getPageNumber("/page/1") == 1);
        assertTrue(model.getPageNumber("page/2423") == 1);
        assertTrue(model.getPageNumber("/page/43") == 43);
        assertTrue(model.getPageNumber("/page/1234sdgf") == 1);
    }

    @Test
    public void testGetMenu() throws Exception {
        assertTrue(model.getMenu().size() > 0);
    }

    @Test
    public void testGetMenu1() throws Exception {
        assertTrue(model.getMenu("/").size() > 0);
        assertTrue(model.getMenu("/page/0").equals(model.getMenu("/")));
        assertTrue(model.getMenu("/page/1").size() > 0);
        assertTrue(model.getMenu("/sdgsdg").equals(model.getMenu("/")));
        assertTrue(model.getMenu("/page/55") == null);
    }

    @Test
    public void testGetNumberOfPages() throws Exception {
        assertTrue(model.getNumberOfPages() == 2);

    }
}