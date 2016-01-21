package filters;

import languages.Languages;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "*")
public class LanguageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String queryString = ((HttpServletRequest)servletRequest).getQueryString();
        if (queryString != null) {
            String[] params = queryString.split("&");
            for(String param: params) {
                String[] paramValues = param.split("=");
                if (paramValues.length == 2 && paramValues[0].equals("lang")) {
                    HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
                    Languages lang;
                    if (paramValues[1].equals("en")) {
                        lang = Languages.EN;
                    } else {
                        lang = Languages.RU;
                    }
                    // todo: заменить language на константу тут и в теге
                    session.setAttribute("language", lang);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
