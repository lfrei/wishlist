package com.redbeard.graphql.wishlist.repository;

import com.redbeard.graphql.wishlist.domain.Wish;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Repository;

@Repository
public class WishDataFetcher implements DataFetcher<Wish> {

    @Override
    public Wish get(DataFetchingEnvironment environment) {
        String wishId = environment.getArgument("id");
        return Wish.examples()
                .stream()
                .filter(wish -> wish.getId().equals(wishId))
                .findFirst()
                .orElse(null);
    }
}
