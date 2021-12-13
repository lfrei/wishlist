package com.redbeard.graphql.wishlist.repository;

import com.redbeard.graphql.wishlist.domain.Person;
import com.redbeard.graphql.wishlist.domain.Wish;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Repository;

@Repository
public class GivenToPersonDataFetcher implements DataFetcher<Person> {

    @Override
    public Person get(DataFetchingEnvironment environment) {
        Wish wish = environment.getSource();
        return Person.examples()
                .stream()
                .filter(person -> person.getId().equals(wish.getGivenTo()))
                .findFirst()
                .orElse(null);
    }
}
