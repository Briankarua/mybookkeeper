package com.example.mybookkeeper.managers;

import java.util.Objects;

public class Manager {
    private int managerId;
    private String managerPhone;
    private String managerPassword;
    private String managerName;
    private double totalReceipts;
    private double totalExpenses;
    private double totalBalances;

    public Manager(int managerId, String managerName, String managerPhone, String managerPassword) {
        this.managerId = managerId;
        this.managerPhone = managerPhone;
        this.managerPassword = managerPassword;
        this.managerName = managerName;
    }

    public Manager(String managerName, String managerPhone, String managerPassword) {
        this.managerPhone = managerPhone;
        this.managerPassword = managerPassword;
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public String getManagerName() {
        return managerName;
    }

    public int getManagerID() {
        return managerId;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setTotalReceipts(double totalReceipts) {
        this.totalReceipts = totalReceipts;
    }

    public double getTotalReceipts() {
        return totalReceipts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return managerId == manager.managerId
                && Double.compare(manager.totalReceipts, totalReceipts) == 0
                && Double.compare(manager.totalExpenses, totalExpenses) == 0
                && Double.compare(manager.totalBalances, totalBalances) == 0
                && Objects.equals(managerPhone, manager.managerPhone)
                && Objects.equals(managerPassword, manager.managerPassword)
                && Objects.equals(managerName, manager.managerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerId, managerPhone, managerPassword, managerName, totalReceipts, totalExpenses);
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getTotalBalances() {
        return totalBalances;
    }

    public void setTotalBalances(double totalBalances) {
        this.totalBalances = totalBalances;
    }
}
