package com.example.mybookkeeper.login;

public class Credentials {
    private String Password;
    private String Username;

    Credentials(String username, String password){
        this.Username = username;
        this.Password = password;
    }

    public String setPassword(String password){
        Password = password;
        return password;
    }

    public String getGetPassword() {
        return Password;
    }

    public String setUsername(String username){
        Password = username;
        return username;
    }

    public String getGetUsername() {
        return Username;
    }

}
