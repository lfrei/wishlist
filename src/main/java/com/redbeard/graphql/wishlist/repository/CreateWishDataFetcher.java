package com.redbeard.graphql.wishlist.repository;

import com.redbeard.graphql.wishlist.domain.Wish;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Repository;

@Repository
public class CreateWishDataFetcher implements DataFetcher<Wish> {

    @Override
    public Wish get(DataFetchingEnvironment environment) {
        String id = environment.getArgument("id");
        String name = environment.getArgument("name");
        String description = environment.getArgument("description");
        int ageRating = environment.getArgumentOrDefault("ageRating", 0);

        return Wish.builder()
                .id(id)
                .name(name)
                .description(description)
                .ageRating(ageRating)
                .build();
    }
}
