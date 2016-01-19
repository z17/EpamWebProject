package models;

import dao.ItemDao;
import entity.Item;

import java.util.ArrayList;
import java.util.Map;

public class ModelItem {
    public Map<Integer, Item> getMenu() {
        ItemDao dao = new ItemDao();
        return dao.get();
    }
}
