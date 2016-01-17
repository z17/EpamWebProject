package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        System.out.println(cookies);
        if (cookies != null) {
            for (Cookie current : cookies) {
                System.out.println(current.getName() + current.getValue());
                if (current.getName().equals("order")) {
                    orderStr = current.getValue();
                    break;
                }
            }
        }
        Cookie c = new Cookie("test", "val");
        response.addCookie(c);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/order.jsp");
        requestDispatcher.forward(request, response);
    }
}
