package settings;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectSettingTest {

    @Test
    public void testGetValue() throws Exception {
        ProjectSetting projectSetting = ProjectSetting.getInstance();
        assertTrue(projectSetting.getValue("1231") == null);
        assertTrue(projectSetting.getValue("db.poolsize").equals("5"));
    }
}