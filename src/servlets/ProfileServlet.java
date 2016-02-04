package servlets;

import entity.User;
import models.ModelUser;
import models.messages.UserMessages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Профиль
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
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
        User currentUser = (User)session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("/login");
            return;
        }

        String submitInfo = request.getParameter("submit-info");
        String submitPass = request.getParameter("submit-pass");
        if (submitInfo != null) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            ModelUser model = new ModelUser();
            ArrayList<UserMessages> messages = model.updateUserInfo(currentUser, name, email, phone, address);
            request.setAttribute("messages", messages);
        } else if (submitPass != null) {
            String password = request.getParameter("password");
            String newPassword = request.getParameter("new-password");
            String confirmPassword = request.getParameter("confirm-password");
            ModelUser model = new ModelUser();
            ArrayList<UserMessages> messages = model.updateUserPassword(currentUser, password, newPassword, confirmPassword);
            request.setAttribute("messages", messages);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
        requestDispatcher.forward(request, response);
    }
}
