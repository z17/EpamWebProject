package languages;

import org.junit.Test;
import tags.Message;

import static org.junit.Assert.*;

public class MessagesTest {
    @Test
    public void testGetMessage() throws Exception {
        assertTrue(Messages.getMessage("main.title").equals(Messages.getMessage("main.title", Languages.RU)));
        assertTrue(Messages.getMessage("sdsgd").equals(""));
        assertTrue(Messages.getMessage("main.title").length() > 0);
    }
}