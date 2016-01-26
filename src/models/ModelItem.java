package models;

import dao.ItemDao;
import entity.Item;
import org.apache.log4j.Logger;
import settings.ProjectSetting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ModelItem {
    private static final Logger LOG = Logger.getLogger(ModelItem.class);

    private static String PAGE_PREFIX = "/page/";
    private static String SETTINGS_ITEMS_PER_PAGE = "pages.items_per_page";
    private static int DEFAULT_ITEMS_PER_PAGE = 5;

    public Collection<Item> getMenu() {
        ItemDao dao = new ItemDao();
        return dao.get();
    }

    public Collection<Item> getMenu(String url) {
        int page = getPageNumber(url);
        ProjectSetting setting = ProjectSetting.getInstance();
        int itemsPerPage;
        try {
            itemsPerPage = Integer.parseInt(setting.getValue(SETTINGS_ITEMS_PER_PAGE));
        } catch (NumberFormatException e) {
            LOG.error("error format items per page");
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }
        int startItem = itemsPerPage * (page - 1) + 1;
        int endItem = itemsPerPage * page + 1;

        ItemDao dao = new ItemDao();
        return dao.get(startItem, endItem);
    }

    public int getPageNumber(String url) {
        if (!url.contains(PAGE_PREFIX)) {
            return 1;
        }
        String numberStr = url.substring(PAGE_PREFIX.length());

        int pageNumber;

        try {
            pageNumber = Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            pageNumber = 1;
        }

        // номер страницы не может быть равен нулю или меньше 0
        if (pageNumber < 1) {
            pageNumber = 1;
        }

        return pageNumber;
    }

    public int getNumberOfPages() {
        ItemDao dao = new ItemDao();
        int count = dao.getNumber();

        ProjectSetting setting = ProjectSetting.getInstance();
        int itemsPerPage;
        try {
            itemsPerPage = Integer.parseInt(setting.getValue(SETTINGS_ITEMS_PER_PAGE));
        } catch (NumberFormatException e) {
            LOG.error("error format items per page");
            itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
        }
        return (int)Math.ceil((double)count / itemsPerPage);
    }
}
