package servlets;

import entity.Item;
import models.ModelItem;
import org.apache.log4j.Logger;
import settings.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Страницы с содержимым меню
 */
@WebServlet({"/index.jsp" , "/page/*"})
public class MainServlet extends HttpServlet{
    private static final Logger LOG = Logger.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String url = request.getRequestURI();
        ModelItem model = new ModelItem();


        Collection<Item> menu = model.getMenu(url);
        request.setAttribute("menu", menu);
        if (menu == null) {
            LOG.warn("error number of page");
            response.sendRedirect(Constants.PAGE_ERROR_404_URL);
            return;
        }

        request.setAttribute("currentPage", model.getPageNumber(url));
        request.setAttribute("numberOfPages", model.getNumberOfPages());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");

        requestDispatcher.forward(request, response);
    }
}
