package languages;

import java.util.*;

public class Messages {
    private static Languages DEFAULT_LOCALE = Languages.RU;
    private static Map<Languages, ResourceBundle> files = new HashMap<>();

    static {
        reload();
    }

    public static void reload() {
        ResourceBundle ru = ResourceBundle.getBundle("languages.text", new Locale("ru", "RU"));
        ResourceBundle en = ResourceBundle.getBundle("languages.text", new Locale("en", "US"));
        files.put(Languages.RU, ru);
        files.put(Languages.EN, en);
    }

    public static String getMessage(String message, Languages locale) {
        try {
            if (locale == null) {
                return files.get(DEFAULT_LOCALE).getString(message);
            }
            return files.get(locale).getString(message);
        } catch (MissingResourceException e) {
            return "";
        }
    }
}
