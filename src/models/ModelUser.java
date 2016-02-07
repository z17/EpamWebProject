package models;

import dao.GroupDao;
import dao.UserDao;
import entity.Group;
import entity.User;
import models.messages.UserMessages;
import org.apache.log4j.Logger;
import settings.Constants;
import settings.ProjectSetting;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Модель, отвечающая за работу с пользователями
 */
public class ModelUser {
    private static final Logger LOG = Logger.getLogger(ModelUser.class);
    private static final Group DEFAULT_GROUP;

    // получаем объект с дефолтной группой
    static {
        GroupDao groupDao = new GroupDao();
        DEFAULT_GROUP = groupDao.getById(Constants.DEFAULT_GROUP_ID);
    }

    /**
     * Создаёт пользователя
     * @param name имя
     * @param login логин
     * @param password пароль
     * @return ошибки создания или CORRECT_SIGNUP
     */
    public ArrayList<UserMessages> createUser(final String name, final String login, final String password) {
        ArrayList<UserMessages> validate = validateSignupUserData(name, login, password);
        if (validate.get(0) == UserMessages.CORRECT_SIGNUP) {
            String encodedPassword = encryptPassword(password);
            if (encodedPassword != null) {
                User user = new User(name, DEFAULT_GROUP, login, encodedPassword);
                UserDao dao = new UserDao();
                dao.create(user);
            } else {
                validate.add(UserMessages.UNKNOWN_ERROR);
            }
        }

        return validate;
    }

    /**
     * Авторизация
     * @param login логин
     * @param password пароль
     * @return null или объект пользователя
     */
    public User login(final String login, final String password) {
        if (login == null || password == null) {
            return null;
        }

        UserDao dao = new UserDao();
        User user = dao.getByLogin(login);
        if (user == null) {
            return null;
        }

        if (!verifyPassword(password, user.getPassword())) {
            return null;
        }
        return user;
    }


    /**
     * Обновляет данные пользователя
     * @param user пользователь
     * @param name Имя
     * @param email email
     * @param phone Телефон
     * @param address Адрес
     * @return Ошибки обновления или CORRECT_UPDATE
     */
    public ArrayList<UserMessages> updateUserInfo(final User user, final String name, final String email, final String phone, final String address) {
        ArrayList<UserMessages> validate = new ArrayList<>();
        if (user == null) {
            validate.add(UserMessages.UNKNOWN_ERROR);
        }
        if (name == null || name.length() == 0) {
            validate.add(UserMessages.EMPTY_NAME);
        }
        if (validate.size() == 0) {
            UserDao dao = new UserDao();
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            dao.update(user);
            validate.add(UserMessages.CORRECT_UPDATE);
        }
        return validate;
    }

    /**
     * Обновляет пароль пользователя
     * @param user пользоватль
     * @param password пароль
     * @param newPassword новый пароль
     * @param confirmPassword подтверждение пароля
     * @return ошибки пароля или CORRECT_UPDATE
     */
    public ArrayList<UserMessages> updateUserPassword(final User user, final String password, final String newPassword, final String confirmPassword) {
        ArrayList<UserMessages> validate = new ArrayList<>();
        if (user == null) {
            validate.add(UserMessages.UNKNOWN_ERROR );
        } else {
            if (password == null || password.length() == 0) {
                validate.add(UserMessages.EMPTY_PASSWORD);
            } else if (!verifyPassword(password, user.getPassword())) {
                validate.add(UserMessages.PASSWORD_ERROR);
            }

            if (!isValidPassword(newPassword)) {
                validate.add(UserMessages.PASSWORD_INCORRECT);
            } else if (!newPassword.equals(confirmPassword)) {
                validate.add(UserMessages.PASSWORD_NOT_MATCH);
            }
        }

        if (validate.size() == 0) {
            UserDao dao = new UserDao();
            String newEncodedPassword = encryptPassword(newPassword);
            if (newEncodedPassword != null) {
                user.setPassword(newEncodedPassword);
                dao.update(user);
                validate.add(UserMessages.CORRECT_UPDATE);
            } else {
                validate.add(UserMessages.UNKNOWN_ERROR);
            }
        }
        return validate;
    }

    /**
     * Валидация данных регистрации
     * @param name имя
     * @param login логин
     * @param password пароль
     * @return сообщения об ошибке или CORRECT_SIGNUP
     */
    private ArrayList<UserMessages> validateSignupUserData(final String name, final String login, final String password) {
        ArrayList<UserMessages> validate = new ArrayList<>();

        if (name == null || name.length() == 0) {
            validate.add(UserMessages.EMPTY_NAME);
        }
        if (login == null || login.length() == 0) {
            validate.add(UserMessages.EMPTY_LOGIN);
        }
        if (password == null || password.length() == 0) {
            validate.add(UserMessages.EMPTY_PASSWORD);
        } else if (!isValidPassword(password)) {
            validate.add(UserMessages.PASSWORD_INCORRECT);
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

    /**
     * Проверка валидности пароля
     * @param password пароль
     * @return bool
     */
    private boolean isValidPassword(final String password) {
        return !(password.length() < Constants.PASSWORD_LENGTH_MIN || password.length() > Constants.PASSWORD_LENGTH_MAX);
    }

    /**
     * Кодирование пароля
     * @param password пароль
     * @return закодированный пароль
     */
    private String encryptPassword(final String password) {
        try {
            String passwordWithSalt = password + Constants.PASSWORD_SALT;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordWithSalt.getBytes("UTF-8")); // Change this to "UTF-16" if neede
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aDigest : digest) {
                sb.append(Integer.toHexString(0xff & aDigest));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOG.error("encrypt password error", e);
        }
        return null;
    }

    /**
     * Проверка совпадения пароля
     * @param password пароль
     * @param encodedPassword закодированный пароль
     * @return bool
     */
    private boolean verifyPassword(final String password, final String encodedPassword) {
        if (password == null || encodedPassword == null) {
            return false;
        }
        return encodedPassword.equals(encryptPassword(password));
    }

    public int getUserIdFromUrl(final String url) {
        String[] path = url.split("/");
        if (path.length >= 3) {
            if (path[1].equals("user")) {
                try {
                    return Integer.parseInt(path[2]);
                } catch (NumberFormatException e) {
                    LOG.warn("error format page number");
                    return 0;
                }
            }
        }
        return 0;


    }

    public User getUserFromUrl(final String url) {
        int id = getUserIdFromUrl(url);
        UserDao dao = new UserDao();
        return dao.getById(id);
    }

    public boolean isUserAccessToProfiles(final User user) {
        return user.isAdminAccess();
    }
}
