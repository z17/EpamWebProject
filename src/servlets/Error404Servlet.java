package servlets;

import org.apache.log4j.Logger;
import settings.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 404 ошибка
 */
@WebServlet(Constants.PAGE_ERROR_404_URL)
public class Error404Servlet extends HttpServlet{
    private static final Logger LOG = Logger.getLogger(Error404Servlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.warn("404 error");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error-404.jsp");
        requestDispatcher.forward(request, response);
    }
}
