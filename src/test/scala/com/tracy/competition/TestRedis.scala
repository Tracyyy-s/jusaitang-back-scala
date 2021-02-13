package com.tracy.competition

import com.tracy.competition.utils.RedisUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Tracy
 * @date 2021/2/13 20:21
 */
@SpringBootTest
@RunWith(classOf[SpringRunner])
class TestRedis {

  @Autowired val redisUtil: RedisUtil = null

  @Test
  def test01(): Unit = {
    println(redisUtil.hget("users", "10000"))
  }
}
