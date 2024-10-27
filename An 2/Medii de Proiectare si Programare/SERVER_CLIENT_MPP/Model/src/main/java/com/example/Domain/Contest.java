package com.example.Domain;

import java.util.UUID;

public class Contest extends Entity<UUID>{
    private DistEnum distance;
    private StyleEnum style;

    public DistEnum getDistance() {
        return distance;
    }

    public void setDistance(DistEnum distance) {
        this.distance = distance;
    }

    public StyleEnum getStyle() {
        return style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public Contest(UUID id, DistEnum distance, StyleEnum style) {
        super.setId(id);
        this.distance = distance;
        this.style = style;
    }
}
