package models;

import dao.UserDao;
import entity.User;
import models.messages.UserMessages;
import settings.ProjectSetting;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ModelUser {
    private static int PASSWORD_LENGTH_MIN = 5;
    private static int PASSWORD_LENGTH_MAX = 15;
    private static int DEFAULT_GROUP_ID = 1;
    private static String SETTINGS_SALT = "auth.salt";

    private String encryptPassword(String password) {
        try {
            ProjectSetting setting = ProjectSetting.getInstance();
            password = password + setting.getValue(SETTINGS_SALT);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if neede
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aDigest : digest) {
                sb.append(Integer.toHexString(0xff & aDigest));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // todo обработать ситуацию, когда пароль не зашифрован
        return null;
    }

    public ArrayList<UserMessages> createUser(String name, String login, String password) {
        ArrayList<UserMessages> validate = validateUserData(name, login, password);
        if (validate.get(0) == UserMessages.CORRECT_SIGNUP) {
            password = encryptPassword(password);
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

        password = encryptPassword(password);
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
