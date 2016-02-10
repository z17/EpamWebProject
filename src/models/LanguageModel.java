package models;

import languages.Languages;
import org.apache.log4j.Logger;
import settings.Constants;

public class LanguageModel {
    private static final Logger LOG = Logger.getLogger(LanguageModel.class);
    public Languages getLanguage(String param, Languages currentLanguage) {
        Languages lang;
        if (param != null) {
            switch (param) {
                case "en":
                    lang = Languages.EN;
                    break;
                case "ru":
                    lang = Languages.RU;
                    break;
                default:
                    LOG.warn("try to change invalid language " + param);
                    lang = Constants.DEFAULT_LOCALE;
                    break;
            }
            LOG.info("change language to " + lang);
        } else {
            if (currentLanguage == null) {
                LOG.info("set default language");
                lang = Constants.DEFAULT_LOCALE;
            } else {
                lang = currentLanguage;
            }
        }
        return lang;
    }
}
