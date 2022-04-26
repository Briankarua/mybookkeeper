package com.example.mybookkeeper.managers;

public class ManagerTotal {
    private Manager manager;
    private double total;

    public ManagerTotal(Manager manager, double total) {
        this.manager = manager;
        this.total = total;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
