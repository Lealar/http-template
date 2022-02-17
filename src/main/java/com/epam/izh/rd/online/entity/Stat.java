package com.epam.izh.rd.online.entity;

import com.epam.izh.rd.online.entity.Pokemon;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {

    @JsonProperty("base_stat")
    private short statValue;

    public short getStatValue() {
        return statValue;
    }
}
