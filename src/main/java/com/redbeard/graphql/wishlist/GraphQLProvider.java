package com.redbeard.graphql.wishlist;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

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

    @PostConstruct
    public void init() throws IOException {
        GraphQLSchema graphQLSchema = buildSchema(loadSchema());
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private String loadSchema() throws IOException {
        URL url = Resources.getResource("schema/schema.graphqls");
        return Resources.toString(url, Charsets.UTF_8);
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
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
