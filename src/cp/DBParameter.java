package cp;

/**
 * Название настроек для базы данных
 */
public abstract class DBParameter {
    private DBParameter() {}

    public static String DB_DRIVER = "db.driver";
    public static String DB_URL = "db.url";
    public static String DB_USER = "db.user";
    public static String DB_PASSWORD = "db.password";
    public static String DB_POOL_SIZE = "db.poolsize";
}
