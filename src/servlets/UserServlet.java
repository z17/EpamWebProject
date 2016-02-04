package servlets;

import entity.User;
import models.ModelUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Авторизация
 */
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // todo выделить всюду /error-access и /error-404 в константы
        HttpSession session = request.getSession(true);
        ModelUser model = new ModelUser();
        User user = (User)session.getAttribute("user");
        if (user == null || !model.isUserAccessToProfiles(user)) {
            response.sendRedirect("/error-access");
            return;
        }

        User userProfile = model.getUserFromUrl(request.getRequestURI());
        if (userProfile == null) {
            response.sendRedirect("/error-404");
            return;
        }

        request.setAttribute("user", userProfile);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user.jsp");
        requestDispatcher.forward(request, response);
    }
}
