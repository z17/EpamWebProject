package models;

import languages.Languages;
import org.junit.Test;

import static org.junit.Assert.*;

public class LanguageModelTest {
    LanguageModel model = new LanguageModel();
    @Test
    public void testGetLanguage() throws Exception {
        assertTrue(model.getLanguage("ru", null) == Languages.RU);
        assertTrue(model.getLanguage("en", null) == Languages.EN);
        assertTrue(model.getLanguage("asdgfa", null) == Languages.RU);
        assertTrue(model.getLanguage("asgfaf", Languages.RU) == Languages.RU);
        assertTrue(model.getLanguage("asgfaf", Languages.EN) == Languages.RU);
        assertTrue(model.getLanguage(null, Languages.EN) == Languages.EN);
        assertTrue(model.getLanguage(null, Languages.RU) == Languages.RU);
        assertTrue(model.getLanguage(null, null) == Languages.RU);
    }
}