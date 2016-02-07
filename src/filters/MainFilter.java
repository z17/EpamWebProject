package filters;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import languages.Languages;
import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import settings.Constants;

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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType(Constants.CONTENT_TYPE);
        servletRequest.setCharacterEncoding(Constants.CONTENT_ENCODING);
        servletResponse.setContentType(Constants.CONTENT_TYPE);
        servletResponse.setCharacterEncoding(Constants.CONTENT_ENCODING);

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
            session.setAttribute(Constants.SESSION_LANGUAGE_PARAM, lang);
        } else {
            Languages currentLang = (Languages) session.getAttribute(Constants.SESSION_LANGUAGE_PARAM);
            if (currentLang == null) {
                LOG.info("set default language");
                session.setAttribute(Constants.SESSION_LANGUAGE_PARAM, Languages.RU);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
