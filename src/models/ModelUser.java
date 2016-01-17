package models;

import dao.UserDao;
import entity.User;
import models.messages.UserMessages;

import java.util.ArrayList;

public class ModelUser {
    static int PASSWORD_LENGTH_MIN = 5;
    static int PASSWORD_LENGTH_MAX = 15;
    static int DEFAULT_GROUP_ID = 1;

    public ArrayList<UserMessages> createUser(String name, String login, String password) {
        ArrayList<UserMessages> validate = validateUserData(name, login, password);
        if (validate.get(0) == UserMessages.CORRECT_SIGNUP) {
            User user = new User(name, DEFAULT_GROUP_ID, login, password);
            UserDao dao = new UserDao();
            dao.create(user);
        }

        return validate;
    }

    public User login(String login, String password) {
        if (login == null || password == null) {
            return null;
        }

        UserDao dao = new UserDao();
        User user = dao.getByLogin(login);
        if (user == null) {
            return null;
        }

        // todo:  зашифровать пароли
        if (!user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    private ArrayList<UserMessages> validateUserData(String name, String login, String password) {
        ArrayList<UserMessages> validate = new ArrayList<>();

        if (name == null || name.length() == 0) {
            validate.add(UserMessages.EMPTY_NAME);
        }
        if (login == null || login.length() == 0) {
            validate.add(UserMessages.EMPTY_LOGIN);
        }
        if (password == null || password.length() == 0) {
            validate.add(UserMessages.EMPTY_PASSWORD);
        } else if (password.length() < PASSWORD_LENGTH_MIN || password.length() > PASSWORD_LENGTH_MAX) {
            validate.add(UserMessages.PASSWORD_ERROR);
        }

        UserDao dao = new UserDao();
        User findLogin = dao.getByLogin(login);
        if (findLogin != null) {
            validate.add(UserMessages.LOGIN_EXIST);
        }

        if (validate.size() == 0) {
            validate.add(UserMessages.CORRECT_SIGNUP);
        }

        return validate;
    }


}
