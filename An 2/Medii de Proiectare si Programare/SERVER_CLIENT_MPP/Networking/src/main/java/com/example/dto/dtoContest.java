package com.example.dto;

import java.io.Serializable;
import java.util.UUID;

import com.example.Domain.DistEnum;
import com.example.Domain.StyleEnum;

public class dtoContest implements Serializable {
    private UUID id;
    private DistEnum distance;
    private StyleEnum style;

    public dtoContest(UUID id, DistEnum distance, StyleEnum style) {
        this.id = id;
        this.distance = distance;
        this.style = style;
    }

    public UUID getId() {
        return id;
    }

    public  DistEnum getDistance() {
        return distance;
    }

    public void setDistance(DistEnum distance) {
        this.distance = distance;
    }

    public  StyleEnum getStyle() {
        return style;
    }


    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "dtoContest{" +
                "distance='" + distance + '\'' +
                ", style='" + style + '\'' +
                '}';
    }
}
