package com.example.mybookkeeper.subaccounts;

public class SubAccount {
    private String subaccountID;
    private String subaccountName;
    boolean isSubChecked;

    public SubAccount(String subaccountID, String subaccountName, String isSubChecked) {
        this.subaccountID = subaccountID;
        this.subaccountName = subaccountName;
        //this.isSubChecked = isSubChecked;
    }

    public String getSubAccountName() {
        return subaccountName;
    }

    public void setSubAccountName(String accountName) {
        this.subaccountName = subaccountName;
    }

    public String getSubAccountID() {
        return subaccountID;
    }

    public void setSubAccountID(String subaccountID) {
        this.subaccountID = subaccountID;
    }

    public boolean getSubChecked() {
        return isSubChecked;
    }

    public void setSubChecked(boolean isSubChecked) {
        this.isSubChecked = isSubChecked;
    }
}
