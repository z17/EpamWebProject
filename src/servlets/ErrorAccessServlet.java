package servlets;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ошибка доступа
 */
@WebServlet("/error-access")
public class ErrorAccessServlet extends HttpServlet{
    private static final Logger LOG = Logger.getLogger(ErrorAccessServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.warn("access error");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/error-access.jsp");
        requestDispatcher.forward(request, response);
    }
}
