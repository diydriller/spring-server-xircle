package com.xircle.followservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

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
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val config: MutableMap<String, Any?> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaBroker
        config[ConsumerConfig.GROUP_ID_CONFIG] = "user-group"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        config[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory =
            ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}