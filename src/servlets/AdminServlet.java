package servlets;

import entity.Order;
import entity.User;
import models.ModelOrder;
import settings.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервлет панели управления заказами
 * Направляет на /error-access в случае отсутсвия доступа у пользователя
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ModelOrder model = new ModelOrder();
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(Constants.SESSION_USER_PARAM);
        if (!model.isAdminAccessAllowed(user)) {
            response.sendRedirect(Constants.PAGE_ERROR_ACCESS_URL);
            return;
        }

        List<Order> ordersList = model.getActiveOrders();
        request.setAttribute("ordersList", ordersList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        requestDispatcher.forward(request, response);
    }
}
