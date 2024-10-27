package com.example.Domain;

import java.util.UUID;

public class Participant extends Entity<UUID>{
    private String name;
    private int age;

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


    public Participant(UUID id,String name, int age) {
        super.setId(id);
        this.name = name;
        this.age = age;
    }
}
