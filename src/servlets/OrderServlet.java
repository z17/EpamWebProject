package servlets;

import entity.Item;
import entity.Order;
import entity.User;
import models.ModelOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Страница заказа
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();

        ModelOrder model = new ModelOrder();
        Map<Item, Integer> items = model.getCurrentOrder(cookies);

        HttpSession session = request.getSession(true);

        String submit = request.getParameter("submit");
        User currentUser = (User)session.getAttribute("user");
        if (submit != null && items != null) {
            model.createOrder(currentUser, items);

            // удаляем куку
            Cookie orderCookie = new Cookie("order", "");
            orderCookie.setMaxAge(0);
            response.addCookie(orderCookie);
        } else {
            request.setAttribute("currentOrder", items);
        }

        if(currentUser != null) {
            List<Order> ordersList = model.getUserOrders(currentUser);
            request.setAttribute("ordersList", ordersList);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/order.jsp");
        requestDispatcher.forward(request, response);
    }
}
