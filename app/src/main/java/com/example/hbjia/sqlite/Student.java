package com.example.hbjia.sqlite;

/**
 * Created by hbjia on 2014/12/15.
 */
public class Student {
    public int _id;
    public String name;
    public int age;
    public String info;

    public Student() {
    }

    public Student(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
