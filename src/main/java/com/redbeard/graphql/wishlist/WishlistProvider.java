package com.redbeard.graphql.wishlist;

import com.redbeard.graphql.wishlist.repository.GiftedFromPersonDataFetcher;
import com.redbeard.graphql.wishlist.repository.GivenToPersonDataFetcher;
import com.redbeard.graphql.wishlist.repository.WishDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class WishlistProvider {
    private GraphQL graphQL;

    private final WishDataFetcher wishDataFetcher;
    private final GiftedFromPersonDataFetcher giftedFromPersonDataFetcher;
    private final GivenToPersonDataFetcher givenToPersonDataFetcher;

    @Value("/schema/schema.graphqls")
    private ClassPathResource schema;

    public WishlistProvider(WishDataFetcher wishDataFetcher,
                            GiftedFromPersonDataFetcher giftedFromPersonDataFetcher,
                            GivenToPersonDataFetcher givenToPersonDataFetcher) {

        this.wishDataFetcher = wishDataFetcher;
        this.giftedFromPersonDataFetcher = giftedFromPersonDataFetcher;
        this.givenToPersonDataFetcher = givenToPersonDataFetcher;
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        InputStream schemaInputStream = schema.getInputStream();
        GraphQLSchema graphQLSchema = buildSchema(new InputStreamReader(schemaInputStream));
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(InputStreamReader reader) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(reader);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("wishById", wishDataFetcher))
                .type(newTypeWiring("Wish")
                        .dataFetcher("givenTo", givenToPersonDataFetcher)
                        .dataFetcher("giftedFrom", giftedFromPersonDataFetcher))
                .build();
    }
}
