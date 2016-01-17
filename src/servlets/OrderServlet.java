package servlets;

import entity.Item;
import models.ModelOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
        String orderStr = null;
        if (cookies != null) {
            for (Cookie current : cookies) {
                if (current.getName().equals("order")) {
                    orderStr = current.getValue();
                    break;
                }
            }
        }

        ModelOrder model = new ModelOrder();
        if (orderStr != null) {
            Map<Item, Integer> items = model.getCurrentOrder(orderStr);
            request.setAttribute("currentOrder", items);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/order.jsp");
        requestDispatcher.forward(request, response);
    }
}
