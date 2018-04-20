package com.example.mabdullah.timetracingapp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mabdullah on 30.03.2018.
 */

public class User {

    @JsonProperty("id")
    private int id ;

    public int getLoki_id() {
        return loki_id;
    }

    public void setLoki_id(int loki_id) {
        this.loki_id = loki_id;
    }

    @JsonProperty("$loki")
    private int loki_id ;
    @JsonProperty("username")
    private String name;
    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    public int getId() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
