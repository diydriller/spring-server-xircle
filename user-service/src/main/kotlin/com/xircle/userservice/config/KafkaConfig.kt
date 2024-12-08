package com.xircle.userservice.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.kafka.transaction.KafkaTransactionManager

@Configuration
@EnableKafka
class KafkaConfig {
    @Value("\${kafka.broker}")
    private val kafkaBroker: String? = null

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, Any> {
        val config: MutableMap<String, Any?> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaBroker
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        config[ProducerConfig.TRANSACTIONAL_ID_CONFIG] = "tx-"
        config[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = true
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun kafkaTransactionManager(producerFactory: ProducerFactory<String, Any>): KafkaTransactionManager<String, Any> {
        return KafkaTransactionManager(producerFactory)
    }
}