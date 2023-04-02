package com.demo.Spring.Boot.Rest.API;

public class Person {
    private String name;
    private String about;
    private int birthYear;

    private String password;



    public Person(String name, String about, int birthYear, String password) {
        this.name = name;
        this.about = about;
        this.birthYear = birthYear;
        this.password=password;
    }

    public Person(String name, String about, int birthYear) {
        this.name = name;
        this.about = about;
        this.birthYear = birthYear;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
