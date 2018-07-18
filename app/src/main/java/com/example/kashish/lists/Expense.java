package com.example.kashish.lists;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
public class Expense {
    private String name;
    private int age;
    private String date;
    private String time;
    private int score;
    private long id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
