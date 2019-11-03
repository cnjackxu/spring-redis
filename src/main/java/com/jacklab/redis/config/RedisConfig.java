package com.jacklab.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate (RedisConnectionFactory factory){
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		
		// set Redis serializer for Jackson
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		jackson2JsonRedisSerializer.setObjectMapper(mapper);
		
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// set key, value serializer
		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// set hashkey, hashvalue serializer
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
