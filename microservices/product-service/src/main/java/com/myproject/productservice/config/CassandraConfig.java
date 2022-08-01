package com.myproject.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {
    private static final String keyspace = "product_service";
    private static final String basePackages = "com.myproject.productservice.entity";

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {basePackages};
    }

    @Override
    protected List<String> getStartupScripts() {
        return List.of(
                "INSERT INTO product_service.products (id, description, name) VALUES ('4', 'Iphone 12 Plus RED Color 64 GB', 'IPHONE_12_PLUS_RED');",
                "INSERT INTO product_service.products (id, description, name) VALUES ('3', 'Iphone 12 Plus BLUE Color 64 GB', 'IPHONE_12_PLUS_BLUE');",
                "INSERT INTO product_service.products (id, description, name) VALUES ('2', 'Iphone 12 Plus GREEN Color 64 GB', 'IPHONE_12_PLUS_GREEN');",
                "INSERT INTO product_service.products (id, description, name) VALUES ('1', 'Iphone 12 Plus RED Color 64 GB', 'IPHONE_12_PLUS_YELLOW');");
    }

    @Override
    protected List<String> getShutdownScripts() {
        return List.of("truncate table product_service.products;");
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return List.of(specification);
    }

}