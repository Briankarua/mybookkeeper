package com.example.mybookkeeper.managers;

public class Manager {
     private int managerId;
     private String managerName;
     private String task;
     Manager(String managerName, String task) {
            this.managerName = managerName;
            this.task = task;
        }
     public Manager(int managerId, String managerName, String task) {
            this.managerId = managerId;
            this.managerName = managerName;
            this.task = task;
        }
     public int getManagerId() {
            return managerId;
        }
     public void setManagerId(int managerId) {
            this.managerId = managerId;
        }
     public String getManagerName() {
            return managerName;
        }
     public void setName(String managerName) {
            this.managerName = managerName;
        }
     public String getTask() {
            return task;
        }
     public void setTask(String task) {
            this.task = task;
        }
}
