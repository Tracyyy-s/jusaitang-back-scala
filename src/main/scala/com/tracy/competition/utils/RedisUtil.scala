package com.tracy.competition.utils

import java.util.concurrent.TimeUnit

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * @author Tracy
 * @date 2021/2/9 12:14
 */
@Component
class RedisUtil {

  @Autowired private val redisTemplate: RedisTemplate[String, Object] = null

  /**
   * 指定缓存失效时间
   *
   * @param key  key
   * @param time 时间(秒)
   * @return 操作结果 true成功 false失败
   */
  def expire(key: String, time: Long): Boolean = try {
    if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS)
    true
  } catch {
    case e: Exception =>
      e.printStackTrace()
      false
  }

  /**
   * 根据key 获取过期时间
   *
   * @param key 不能为空
   * @return 返回0代表永久有效
   */
  def getExpire(key: String): Long = redisTemplate.getExpire(key, TimeUnit.SECONDS)

  /**
   * 判断key是否存在
   *
   * @param key key
   * @return true存在 false不存在
   */
  def hasKey(key: String): Boolean = try redisTemplate.hasKey(key)
  catch {
    case e: Exception =>
      e.printStackTrace()
      false
  }

  /**
   * 删除缓存
   *
   * @param key 可以传一个值或者多个
   */
  def del(key: String*): Unit = {
    if (key != null && key.nonEmpty)
      if (key.length == 1) redisTemplate.delete(key(0))
  }

  /**
   * 普通缓存获取
   *
   * @param key key
   * @return 值
   */
  def get(key: String): Object = if (key == null) null
  else redisTemplate.opsForValue.get(key)

  /**
   * 普通缓存放入
   *
   * @param key   key
   * @param value value
   * @return 操作结果 成功返回true,失败返回false
   */
  def set(key: String, value: Object): Boolean = try {
    redisTemplate.opsForValue.set(key, value)
    true
  } catch {
    case e: Exception =>
      e.printStackTrace()
      false
  }

  /**
   * 普通缓存放入并设置时间
   *
   * @param key   key
   * @param value value
   * @param time  时间(秒) 如果设置时间小于等于0将设置为无限期
   * @return 返回true成功, 返回false失败
   */
  def set(key: String, value: Object, time: Long): Boolean = try if (time > 0) {
    redisTemplate.opsForValue.set(key, value, time, TimeUnit.SECONDS)
    true
  }
  else set(key, value)
  catch {
    case e: Exception =>
      e.printStackTrace()
      false
  }


  /**
   * 递增
   *
   * @param key   key
   * @param delta 递增的值(要加几)
   * @return
   */
  def incr(key: String, delta: Long): Long = if (delta < 0) throw new RuntimeException("递增因子必须大于0")
  else redisTemplate.opsForValue.increment(key, delta)

  /**
   * 递减
   *
   * @param key   key
   * @param delta 递减的值(要减几)
   * @return
   */
  def decr(key: String, delta: Long): Long = {
    if (delta < 0)
      throw new RuntimeException("递减因子必须大于0")
    else
      redisTemplate.opsForValue.increment(key, -delta)
  }

  /**
   * 获取哈希表中的值
   *
   * @param key  键(not null)
   * @param item 哈希表中的键(not null)
   * @return 值
   */
  def hget(key: String, item: String): Object = redisTemplate.opsForHash.get(key, item)

}
