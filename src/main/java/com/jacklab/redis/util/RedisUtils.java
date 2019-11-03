package com.jacklab.redis.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * set cache expired by key
	 * @param key
	 * @param timeout
	 * @return
	 */
	public boolean expire(String key, long timeout) {
		if (timeout > 0) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}
	
	/**
	 * check if key exists in cache
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean deleteKeys(String...key) {
		if(key!=null && key.length>0) {
			if(key.length==1)
				return redisTemplate.delete(key[0]);
			else {
				Long num = redisTemplate.delete(Arrays.asList(key));
				return num>0?true:false;
			}
		}
		return false;
	}
	
	
	// --------------------- Map -------------------- //
	/**
	 * 
	 * @param key
	 * @param hashKey
	 * @return An object from hashKey stored by key 
	 */
	public Object hGet(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}
	
	
	public Map<Object, Object> hMGet(String key){
		return redisTemplate.opsForHash().entries(key);
	}
	
	public void hMSet(String key, Map<Object, Object> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	public void hSet(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}
	
	public void hDel(String key, String...hashKeys) {
		redisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	public boolean hHasKey(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}
	
	// --------------------- Set -------------------- //
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> sGetAllValues(String key){  
		return redisTemplate.opsForSet().members(key);
	}
	 
	/**
	 * check if value exists in current set by key
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sHasValueFromKey(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	} 
	
	public long sGetSetSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}
	
	public long sSet(String key, Object...values) {
		return redisTemplate.opsForSet().add(key, values);
	}
	
	public long sSetWithExpire(String key, long timeout, Object...values) {
		long count = redisTemplate.opsForSet().add(key, values);
		if(timeout > 0)
			expire(key, timeout);
		return count;
	}
	
	public long sRemoveSetValues(String key, Object...values) {
		return redisTemplate.opsForSet().remove(key, values);
		
	}
	
}
