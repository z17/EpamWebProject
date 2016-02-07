package settings;

import languages.Languages;

/**
 * Параметры, которые не будут меняться в процессе работы приложения
 */
public interface Constants {
    String SESSION_LANGUAGE_PARAM = "language";
    String CONTENT_TYPE = "text/html";
    String CONTENT_ENCODING = "UTF-8";
    int DEFAULT_ITEMS_PER_PAGE = 5;
    String PAGE_PREFIX = "/page/";

    String PAGE_ERROR_404_URL = "/error-404";
    String PAGE_ERROR_ACCESS_URL = "/error-access";

    int DEFAULT_GROUP_ID = 1;

    String PASSWORD_SALT = "amt423SAdg23f";

    int PASSWORD_LENGTH_MIN = 5;
    int PASSWORD_LENGTH_MAX = 15;

    Languages DEFAULT_LOCALE = Languages.RU;
}
