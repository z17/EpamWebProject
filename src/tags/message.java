package tags;

import languages.Languages;
import languages.Messages;
import org.apache.log4j.Logger;
import settings.Constants;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * jstl тег message для вывода сообщения в выбранном языке
 */
public class Message extends TagSupport {
    private static final Logger LOG = Logger.getLogger(Message.class);

    private String text;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        Languages locale = (Languages)session.getAttribute(Constants.SESSION_LANGUAGE_PARAM);

        try {
            out.print(Messages.getMessage(text, locale));
        } catch (IOException e) {
            LOG.error("message tag print error", e);
        }

        return SKIP_BODY;
    }
}