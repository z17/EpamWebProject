package models;

import cp.ConnectionPool;
import dao.ItemDao;
import entity.Item;
import org.apache.log4j.Logger;
import settings.Constants;
import settings.ProjectSetting;

import java.util.Collection;

/**
 * Модель для обработки действий с элементами меню
 */
public class ModelItem {
    private static final Logger LOG = Logger.getLogger(ModelItem.class);

    private static String SETTINGS_ITEMS_PER_PAGE = "pages.items_per_page";

    /**
     * Получает все элементы меню
     * @return список элементов
     */
    public Collection<Item> getMenu() {
        ItemDao dao = new ItemDao();
        return dao.get();
    }

    /**
     * Получает все элементы меню для заданной страницы
     * @param url вида /page/[0-9]*, иначе возвращается контент первой страницы
     * @return меню
     */
    public Collection<Item> getMenu(final String url) {
        int page = getPageNumber(url);
        ProjectSetting setting = ProjectSetting.getInstance();
        int itemsPerPage;
        try {
            itemsPerPage = Integer.parseInt(setting.getValue(SETTINGS_ITEMS_PER_PAGE));
        } catch (NumberFormatException e) {
            LOG.error("error format items per page");
            itemsPerPage = Constants.DEFAULT_ITEMS_PER_PAGE;
        }
        int startItem = itemsPerPage * (page - 1) + 1;
        int endItem = itemsPerPage * page + 1;

        ItemDao dao = new ItemDao();
        return dao.get(startItem, endItem);
    }

    /**
     * Возвращает номер текущей страницы
     * @param url  вида /page/[0-9]*, иначе возвращается единица
     * @return номер страницы
     */
    public int getPageNumber(final String url) {
        if (!url.contains(Constants.PAGE_PREFIX)) {
            return 1;
        }
        String numberStr = url.substring(Constants.PAGE_PREFIX.length());

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

    /**
     * Получает кол-во страниц
     * @return количество страниц
     */
    public int getNumberOfPages() {
        ItemDao dao = new ItemDao();
        int count = dao.getNumber();

        ProjectSetting setting = ProjectSetting.getInstance();
        int itemsPerPage;
        try {
            itemsPerPage = Integer.parseInt(setting.getValue(SETTINGS_ITEMS_PER_PAGE));
        } catch (NumberFormatException e) {
            LOG.error("error format items per page");
            itemsPerPage = Constants.DEFAULT_ITEMS_PER_PAGE;
        }
        return (int)Math.ceil((double)count / itemsPerPage);
    }
}
