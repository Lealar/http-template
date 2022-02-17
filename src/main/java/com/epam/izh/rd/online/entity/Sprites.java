package com.epam.izh.rd.online.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprites {

    @JsonSetter("front_default")
    private String front_default;

    public String getFront_default() {
        return this.front_default;
    }
}
