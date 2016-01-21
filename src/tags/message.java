package tags;

import languages.Languages;
import languages.Messages;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class Message extends TagSupport {
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
        Languages locale = (Languages)session.getAttribute("language");

        try {
            out.print(Messages.getMessage(text, locale));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
