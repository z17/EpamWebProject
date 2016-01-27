package servlets;

import dao.BillDao;
import entity.Bill;
import entity.Order;
import entity.User;
import models.ModelOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Страница отдельного заказа
 */
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

        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");

        Order order = null;
        if (orderId > 0) {
            order = model.getOrderById(orderId);
        }

        if (order == null) {
            response.sendRedirect("/error-404");
            return;
        }

        if (!model.isOrderAccessAllowed(order, user)) {
            response.sendRedirect("/error-access");
            return;
        }

        String action = request.getParameter("action");
        if (action != null) {
            order = model.doAction(action, user, order);
        }

        if (order != null) {
            BillDao billDao = new BillDao();
            Bill bill = billDao.getByOrderId(order.getId());
            request.setAttribute("bill", bill);
        }
        request.setAttribute("order", order);
        request.setAttribute("displayActionForm", model.isAdminAccessAllowed(user));


        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/order-single.jsp");
        requestDispatcher.forward(request, response);
    }
}
