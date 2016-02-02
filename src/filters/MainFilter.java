package filters;

import languages.Languages;
import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Фильтр с установкой типа, кодировки и языка
 */
@WebFilter(urlPatterns = "*")
public class MainFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(MainFilter.class);
    private static final String TYPE = "text/html";
    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType(TYPE);
        servletRequest.setCharacterEncoding(ENCODING);
        servletResponse.setContentType(TYPE);
        servletResponse.setCharacterEncoding(ENCODING);

        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        String langParam = servletRequest.getParameter("lang");
        if (langParam != null) {
            Languages lang;
            switch (langParam) {
                case "en":
                    lang = Languages.EN;
                    break;
                case "ru":
                    lang = Languages.RU;
                    break;
                default:
                    LOG.warn("try to change invalid language " + langParam);
                    lang = Languages.RU;
                    break;
            }
            LOG.info("change language to " + lang);
            // todo: заменить language на константу тут и в теге
            session.setAttribute("language", lang);
        } else {
            Languages currentLang = (Languages) session.getAttribute("language");
            if (currentLang == null) {
                LOG.info("set default language");
                session.setAttribute("language", Languages.RU);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
