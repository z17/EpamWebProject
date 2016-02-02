package models.messages;

/**
 * Статусы обработки формы регистрации
 */
public enum UserMessages {
    EMPTY_NAME,
    EMPTY_LOGIN,
    EMPTY_PASSWORD,
    PASSWORD_ERROR,
    LOGIN_EXIST,
    CORRECT_SIGNUP,
    CORRECT_UPDATE,
    UNKNOWN_ERROR,
    PASSWORD_NOT_MUTCH,
    PASSWORD_INCORRECT
}
