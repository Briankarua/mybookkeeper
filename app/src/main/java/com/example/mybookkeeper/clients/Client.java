package com.example.mybookkeeper.clients;

public class Client {
    private String clientID;
    private String clientName;
    boolean isChecked;

    public Client(String clientID, String clientName, String isChecked) {
        this.clientID = clientID;
        this.clientName = clientName;
        //this.isChecked = isChecked;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}



