package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModelItemTest {


    @Test
    public void testGetPageNumber() throws Exception {
        ModelItem model = new ModelItem();

        assertTrue(model.getPageNumber("") == 1);
        assertTrue(model.getPageNumber("/") == 1);
        assertTrue(model.getPageNumber("/page/") == 1);
        assertTrue(model.getPageNumber("/page/0") == 1);
        assertTrue(model.getPageNumber("/page/1") == 1);
        assertTrue(model.getPageNumber("page/2423") == 1);
        assertTrue(model.getPageNumber("/page/43") == 43);
        assertTrue(model.getPageNumber("/page/1234sdgf") == 1);
    }
}