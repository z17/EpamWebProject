package settings;

import java.util.ResourceBundle;

public class ProjectSetting {
    private static String RESOURCE_PATH = "settings.params";
    private static ProjectSetting instance = new ProjectSetting();

    private ResourceBundle properties;


    public static ProjectSetting getInstance() {
        return instance;
    }

    public ProjectSetting() {

        properties = ResourceBundle.getBundle(RESOURCE_PATH);
    }

    public String getValue(String key) {
        return properties.getString(key);
    }
}
