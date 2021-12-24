package com.example.mybookkeeper.accounts;

public class Account {
    private int accountId;
    private String accountName;
    private String accDescription;

    Account(String accountID, String accountName, String accDescription) {
        this.accountName = accountName;
        this.accDescription = accDescription;
    }

    public Account(String accountName, String accDescription) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accDescription = accDescription;
    }

    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getAccDescription() {
        return accDescription;
    }
    public void setAccDescription(String accDescription) {
        this.accDescription = accDescription;
    }
}