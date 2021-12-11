package com.redbeard.graphql.wishlist.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Wish {
    private String id;
    private String name;
    private String description;
    private int ageRating;
    private String giftedFrom;
    private String givenTo;

    public static List<Wish> examples() {
        return List.of(
                Wish.builder()
                        .id("wish-1")
                        .name("LEGO")
                        .description("LEGO Star Wars AT-AT")
                        .ageRating(6)
                        .giftedFrom("person-1")
                        .givenTo("person-2")
                        .build()
        );
    }
}
