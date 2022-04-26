package com.example.mybookkeeper.accounts;

public class AccountTotal {
    private Account account;
    private double total;

    public AccountTotal(Account account, double total) {
        this.account = account;
        this.total = total;
    }

    public Account getAccTotal() {
        return account;
    }

    public void getAccTotal(Account account) {
        this.account = account;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}