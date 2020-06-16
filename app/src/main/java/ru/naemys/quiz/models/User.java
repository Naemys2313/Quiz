package ru.naemys.quiz.models;

public class User {
    public static final int user = 0;
    public static final int administrator = 1;

    private String uid;
    private String login;
    private String email;
    private int privilege;

    public User() {


    }

    public User(String uid, String login, String email, int privilege) {
        this.uid = uid;
        this.login = login;
        this.email = email;
        this.privilege = privilege;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
}
