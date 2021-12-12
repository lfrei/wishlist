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

## Thoughts about GraphQL

### Language Support

All major technologies support the graphql specification by now [1].

### Client control over what data is fetched

The client is able to control what data should be requested. 
In REST, if an endpoint is used by multiple clients there is often more data in the response than needed.
With graphql, the client can request only the needed data, resulting in smaller and more suitable queries.
This is also helpful when dealing with devices with smaller bandwidth [2].

### Performing expensive queries

Because of the design of graphql, the client can decide what data should be requested. This could lead to 
very expensive queries where a client requests a lot of fields from a lot of resources [2]. This can have a big impact
on the server and its resources (sometimes multiple involved microservices), without being able to control it.
Similar to SQL, it is very hard to optimize for such expensive / complex queries.

### Aggregate data from multiple sources

Graphql can reduce the round trip, when data from multiple data sources (e.g. multiple microservices) is needed.
In REST this would require multiple HTTP calls. In graphql this can be solved by defining multiple data types to
aggregate the data from multiple sources (see example) [3]. With this, graphql can be used as aggregation gateway 
or act like a BFF [4].

### Writes & Mutations

Graphql supports writes by using the `mutation` keyword [5]. Similar to REST, 
any query could theoretically modify data, but using `mutation` is recommended. 
Graphql is mostly used for read queries, while writes are still made using REST [3].

### Error Handling

There is no uniform error handling compared to REST, where the HTTP codes provide a clear structure
for unsuccessful responses. In graphql errors, there is only a `message` field required by the graphql spec, 
other fields are optional [6]. Because the response is always HTTP code `200`, errors need to be handled manually. 
Some libs define their own contract [7].

### Source

- [1] https://graphql.org/code/
- [2] https://blog.logrocket.com/why-you-shouldnt-use-graphql/
- [3] Sam Newman, "Building Microservices Second Edition", O'Reilly, 2021
- [4] https://samnewman.io/patterns/architectural/bff/
- [5] https://graphql.org/learn/queries/#mutations
- [6] https://spec.graphql.org/October2021/#sec-Errors
- [7] https://netflix.github.io/dgs/error-handling/#the-graphqlerror-interface
