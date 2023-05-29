package com.oscar.configuration

import com.oscar.component.ClusterConfigurationProperties
import com.oscar.PersonRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@EnableR2dbcRepositories(basePackageClasses = [PersonRepository::class])
class AppConfig(val clusterProperties: ClusterConfigurationProperties){
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(RedisClusterConfiguration(clusterProperties.nodes))
    }
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory):RedisTemplate<String, String>  {
        val template = RedisTemplate<String, String>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
