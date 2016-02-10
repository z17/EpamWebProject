package settings;

import languages.Languages;

/**
 * Параметры, которые не будут меняться в процессе работы приложения
 */
public final class Constants {
    public static final String SESSION_LANGUAGE_PARAM = "language";
    public static final String SESSION_USER_PARAM = "user";
    public static final String CONTENT_TYPE = "text/html";
    public static final String CONTENT_ENCODING = "UTF-8";
    public static final int DEFAULT_ITEMS_PER_PAGE = 5;
    public static final String PAGE_PREFIX = "/page/";

    public static final String PAGE_ERROR_404_URL = "/error-404";
    public static final String PAGE_ERROR_ACCESS_URL = "/error-access";

    public static final int DEFAULT_GROUP_ID = 1;

    public static final String PASSWORD_SALT = "amt423SAdg23f";

    public static final int PASSWORD_LENGTH_MIN = 5;
    public static final int PASSWORD_LENGTH_MAX = 15;

    public static final Languages DEFAULT_LOCALE = Languages.RU;
}
