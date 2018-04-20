package com.example.mabdullah.timetracingapp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mabdullah on 30.03.2018.
 */

public class Project  {
    public int getLoki_id() {
        return loki_id;
    }

    public void setLoki_id(int loki_id) {
        this.loki_id = loki_id;
    }

    @JsonProperty("$loki")
    int loki_id;
    @JsonProperty("id")
    int id;
    @JsonProperty("name")
    String name;
    @JsonProperty("beschreibung")
    String beschreibung;

    public Project(int id, String name, String beschreibung) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
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

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
