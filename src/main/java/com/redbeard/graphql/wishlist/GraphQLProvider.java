package com.redbeard.graphql.wishlist;

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
public class GraphQLProvider {

    private GraphQL graphQL;

    private final GraphQLDataFetchers graphQLDataFetchers;

    public GraphQLProvider(GraphQLDataFetchers graphQLDataFetchers) {
        this.graphQLDataFetchers = graphQLDataFetchers;
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @Value("/schema/schema.graphqls")
    private ClassPathResource schema;

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
                        .dataFetcher("wishById", graphQLDataFetchers.getWishByIdDataFetcher()))
                .type(newTypeWiring("Wish")
                        .dataFetcher("givenTo", graphQLDataFetchers.getGivenToDataFetcher())
                        .dataFetcher("giftedFrom", graphQLDataFetchers.getGiftedFromDataFetcher()))
                .build();
    }
}
