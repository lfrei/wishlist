package com.redbeard.graphql.wishlist.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Person {
    private String id;
    private String name;
    private int age;

    public static List<Person> examples() {
        return List.of(
                Person.builder().id("person-1").name("Lukas").age(30).build(),
                Person.builder().id("person-2").name("Jan").age(8).build()
        );
    }
}
