package entity;

public class User {
    private int id;
    private String name;
    private int groupId;
    private String login;
    private String password;

    public User(int id, String name, int groupId, String login, String password) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.login = login;
        this.password = password;
    }

    public User(String name, int groupId, String login, String password) {
        this.name = name;
        this.groupId = groupId;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
