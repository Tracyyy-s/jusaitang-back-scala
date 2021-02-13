package com.tracy.competition.config

import com.fasterxml.jackson.annotation.{JsonAutoDetect, JsonTypeInfo, PropertyAccessor}
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.{Jackson2JsonRedisSerializer, StringRedisSerializer}

/**
 * @author Tracy
 * @date 2021/2/9 12:09
 */
@Configuration
class RedisConfig {


  /**
   * 实例化 redisTemplate
   *
   * @param factory RedisConnectionFactory
   * @return 实例化对象
   */
  @Bean def redisTemplate(factory: RedisConnectionFactory): RedisTemplate[String, Object] = {
    //实例化
    val redisTemplate = new RedisTemplate[String, Object]
    redisTemplate.setConnectionFactory(factory)

    //创建jackson2json的redis序列化器
    val jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer[Object](classOf[Object])
    val om = new ObjectMapper
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)

    jackson2JsonRedisSerializer.setObjectMapper(om)

    val stringRedisSerializer = new StringRedisSerializer
    //key采用string序列化方式
    redisTemplate.setKeySerializer(stringRedisSerializer)
    //hash的key也采用string序列化方式
    redisTemplate.setHashKeySerializer(stringRedisSerializer)
    //value采用jackson2JsonRedisSerializer方式序列化
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer)
    //hash的value也采用jackson2JsonRedisSerializer方式序列化
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer)
    redisTemplate.afterPropertiesSet()

    redisTemplate
  }
}
