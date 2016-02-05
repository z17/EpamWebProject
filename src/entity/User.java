package entity;

/**
 * Пользователь
 */
public class User {
    private int id;
    private String name;
    private Group group;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String address;

    public User(int id, String name, Group group, String login, String password, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public User(String name, Group group, String login, String password, String email, String phone, String address) {
        this.name = name;
        this.group = group;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public User(String name, Group group, String login, String password) {
        this.name = name;
        this.group = group;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Опреледяет имеет ли пользователь админ права
     * @return статус доступа
     */
    public boolean isAdminAccess() {
        return getGroup().getId() == 2;
    }
}
