package servlets;

import entity.User;
import models.ModelUser;
import settings.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Страница пользователя
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
        HttpSession session = request.getSession(true);
        ModelUser model = new ModelUser();
        User user = (User)session.getAttribute("user");
        if (user == null || !model.isUserAccessToProfiles(user)) {
            response.sendRedirect(Constants.PAGE_ERROR_ACCESS_URL);
            return;
        }

        User userProfile = model.getUserFromUrl(request.getRequestURI());
        if (userProfile == null) {
            response.sendRedirect(Constants.PAGE_ERROR_404_URL);
            return;
        }

        request.setAttribute("user", userProfile);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user.jsp");
        requestDispatcher.forward(request, response);
    }
}
