package servlets;

import models.ModelUser;
import models.messages.StatusUserDataMessages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Авторизация
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String submit = request.getParameter("submit");

        if (submit != null) {
            String name = request.getParameter("name");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            ModelUser model = new ModelUser();
            ArrayList<StatusUserDataMessages> messages = model.createUser(name, login, password);
            request.setAttribute("messages", messages);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp");
        requestDispatcher.forward(request, response);
    }
}
