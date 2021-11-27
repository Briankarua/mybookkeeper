package com.example.mybookkeeper.accounts;

public class Account {
    private String accountID;
    private String accountName;
    boolean isChecked;

    public Account(String accountID, String accountName, String isChecked) {
        this.accountID = accountID;
        this.accountName = accountName;
        //this.isChecked = isChecked;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
/*
public class Account {
    String accountName;
    boolean isChecked;

    public Account(String accountName, boolean isChecked) {
        this.accountName = accountName;
        this.isChecked = isChecked;
    }

    public Account(String accountName, String isChecked) {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setSelected(boolean selected) {
        isChecked = selected;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
*/

/*
public class Account {
    private String accountID;
    private String accountName;

    public Account(String accountID, String accountName) {
        this.accountID = accountID;
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
}*/
