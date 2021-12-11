# Wishlist using GraphQL

A simple implementation of a GraphQL Server with [graphql-java](https://github.com/graphql-java/graphql-java). Use branch `dgs` to see the same implementation with the [dgs-framework by netflix](https://github.com/netflix/dgs-framework/)

## Schema

```
type Query {
    wishById(id: ID): Wish
}

type Wish {
    id: ID
    name: String
    description: String
    ageRating: Int
    giftedFrom: Person
    givenTo: Person
}

type Person {
    id: ID
    name: String
    age: Int
}
```
