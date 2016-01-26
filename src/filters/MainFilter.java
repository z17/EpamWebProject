package filters;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "*")
public class MainFilter implements Filter {
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

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
