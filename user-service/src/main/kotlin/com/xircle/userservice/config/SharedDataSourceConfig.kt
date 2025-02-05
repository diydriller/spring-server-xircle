package com.xircle.userservice.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.xircle.userservice.infrastructure.repository.outbox"],
    entityManagerFactoryRef = "sharedEntityManagerFactory",
    transactionManagerRef = "sharedTransactionManager"
)
class SharedDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.shared")
    fun sharedDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun sharedDataSource(
        @Qualifier("sharedDataSourceProperties") properties: DataSourceProperties
    ): DataSource {
        return properties.initializeDataSourceBuilder().build()
    }

    @Bean
    fun sharedEntityManagerFactory(
        @Qualifier("sharedDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan("com.xircle.userservice.infrastructure.model")
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return em
    }

    @Bean
    fun sharedTransactionManager(
        @Qualifier("sharedEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}