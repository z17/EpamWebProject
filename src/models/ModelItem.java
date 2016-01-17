package models;

import dao.ItemDao;
import entity.Item;

import java.util.ArrayList;

public class ModelItem {
    public ArrayList<Item> getMenu() {
        ItemDao dao = new ItemDao();
        return dao.get();
    }
}
