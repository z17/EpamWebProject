package languages;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Интернационализированные сообщения
 */
public class Messages {
    private static final Logger LOG = Logger.getLogger(Messages.class);
    private static Languages DEFAULT_LOCALE = Languages.RU;
    private static Map<Languages, ResourceBundle> files = new HashMap<>();

    static {
        reload();
    }

    /**
     * Загружает языковые файлы
    */
    public static void reload() {
        ResourceBundle ru = ResourceBundle.getBundle("languages.text", new Locale("ru", "RU"));
        ResourceBundle en = ResourceBundle.getBundle("languages.text", new Locale("en", "US"));
        files.put(Languages.RU, ru);
        files.put(Languages.EN, en);
    }

    /**
     * Получаем сообщение в зависимости от локали
     * По-умолчанию язык из DEFAULT_LOCALE
     * @param message ключ сообщения
     * @param locale локаль
     * @return сообщение в заданном языке или пустую строку
     */
    public static String getMessage(String message, Languages locale) {
        try {
            if (locale == null) {
                LOG.warn("null locale");
                return files.get(DEFAULT_LOCALE).getString(message);
            }
            return files.get(locale).getString(message);
        } catch (MissingResourceException e) {
            LOG.warn("Message no found", e);
            return message;
        }
    }

    /**
     * Получает сообщение из локали по-умолчанию
     * @param message ключ сообщения
     * @return сообщение или пустая строка
     */
    public static String getMessage(String message) {
        return getMessage(message, null);
    }
}
