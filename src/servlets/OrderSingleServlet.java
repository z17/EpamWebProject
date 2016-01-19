package servlets;

import entity.Order;
import entity.User;
import models.ModelOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/order/*")
public class OrderSingleServlet extends HttpServlet {
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
        int orderId = model.getOrderIdFromUrl(request.getRequestURI());

        Order order = null;
        if (orderId > 0) {
            order = model.getOrderById(orderId);
            request.setAttribute("order", order);
        }

        if (order == null) {
            response.sendRedirect("/error-404");
            return;
        }

        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");
        if (!model.isOrderAccessAllowed(order, user)) {
            response.sendRedirect("/error-access");
            return;

        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/order-single.jsp");
        requestDispatcher.forward(request, response);
    }
}