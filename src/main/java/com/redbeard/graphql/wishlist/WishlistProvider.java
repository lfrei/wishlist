package com.redbeard.graphql.wishlist;

import com.netflix.graphql.dgs.*;
import com.redbeard.graphql.wishlist.domain.Person;
import com.redbeard.graphql.wishlist.domain.Wish;

@DgsComponent
public class WishlistProvider {

    @DgsQuery
    public Wish wishById(@InputArgument String id) {
        return Wish.examples()
                .stream()
                .filter(wish-> wish.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @DgsData(parentType = "Wish", field = "givenTo")
    public Person givenTo(DgsDataFetchingEnvironment dfe) {

        Wish wish = dfe.getSource();
        return Person.examples()
                .stream()
                .filter(person -> person.getId().equals(wish.getGivenTo()))
                .findFirst()
                .orElse(null);
    }

    @DgsData(parentType = "Wish", field = "giftedFrom")
    public Person giftedFrom(DgsDataFetchingEnvironment dfe) {

        Wish wish = dfe.getSource();
        return Person.examples()
                .stream()
                .filter(person -> person.getId().equals(wish.getGiftedFrom()))
                .findFirst()
                .orElse(null);
    }
}