package com.example.mybookkeeper.managers;

public class Contacts {
     private int id;
     private String name;
     private String job;
     Contacts(String name, String job) {
            this.name = name;
            this.job = job;
        }
     Contacts(int id, String name, String job) {
            this.id = id;
            this.name = name;
            this.job = job;
        }
     int getId() {
            return id;
        }
     public void setId(int id) {
            this.id = id;
        }
     public String getName() {
            return name;
        }
     public void setName(String name) {
            this.name = name;
        }
     String getJob() {
            return job;
        }
     public void setJob(String job) {
            this.job = job;
        }
}
