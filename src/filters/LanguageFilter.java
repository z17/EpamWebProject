package filters;

import languages.Languages;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "*")
public class LanguageFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(LanguageFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String queryString = ((HttpServletRequest)servletRequest).getQueryString();
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        boolean setLanguage = false;

        if (queryString != null) {
            String[] params = queryString.split("&");
            for(String param: params) {
                String[] paramValues = param.split("=");
                if (paramValues.length == 2 && paramValues[0].equals("lang")) {
                    Languages lang;
                    switch (paramValues[1]) {
                        case "en":
                            lang = Languages.EN;
                            break;
                        case "ru":
                            lang = Languages.RU;
                            break;
                        default:
                            LOG.warn("try to change invalid language " + paramValues[1]);
                            lang = Languages.RU;
                            break;
                    }
                    LOG.info("change language to " + lang);
                    // todo: заменить language на константу тут и в теге
                    session.setAttribute("language", lang);
                    setLanguage = true;
                }
            }
        }

        if (!setLanguage) {
            Languages currentLang = (Languages) session.getAttribute("language");
            if (currentLang == null) {
                LOG.info("set default language");
                session.setAttribute("language", Languages.RU);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
