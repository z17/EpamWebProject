package settings;

import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ProjectSetting {
    private static final Logger LOG = Logger.getLogger(ProjectSetting.class);

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
        try {
            return properties.getString(key);
        } catch (MissingResourceException e) {
            LOG.error("invalid setting key");
            return null;
        }
    }
}
