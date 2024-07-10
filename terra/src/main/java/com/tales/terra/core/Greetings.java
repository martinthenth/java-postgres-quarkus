package com.tales.terra.core;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class Greetings implements PanacheRepository<Greeting> {
    public Greeting createGreeting(String name) {
        Greeting greeting = new Greeting();
        greeting.name = name;

        persist(greeting);

        return greeting;
    }

    public Greeting findByName(String name) {
        return find("name", name).firstResult();
    }
}
