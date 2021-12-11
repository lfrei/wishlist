package com.redbeard.graphql.wishlist;

import com.redbeard.graphql.wishlist.domain.Person;
import com.redbeard.graphql.wishlist.domain.Wish;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    public DataFetcher<Wish> getWishByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String wishId = dataFetchingEnvironment.getArgument("id");
            return Wish.examples()
                    .stream()
                    .filter(wish -> wish.getId().equals(wishId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<Person> getGiftedFromDataFetcher() {
        return dataFetchingEnvironment -> {
            Wish wish = dataFetchingEnvironment.getSource();
            return Person.examples()
                    .stream()
                    .filter(person -> person.getId().equals(wish.getGiftedFrom()))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<Person> getGivenToDataFetcher() {
        return dataFetchingEnvironment -> {
            Wish wish = dataFetchingEnvironment.getSource();
            return Person.examples()
                    .stream()
                    .filter(person -> person.getId().equals(wish.getGivenTo()))
                    .findFirst()
                    .orElse(null);
        };
    }
}
