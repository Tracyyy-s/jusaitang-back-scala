package com.tracy.competition

import java.lang.reflect.Field
import java.util

import com.tracy.competition.domain.entity.User
import com.tracy.competition.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Tracy
 * @date 2021/2/9 17:03
 */
@SpringBootTest
@RunWith(classOf[SpringRunner])
class AppTest2 {

  @Autowired val userService: UserService = null

  @Test
  def test01(): Unit = {

    val user: User = userService.findUserByUserName("10000")

    val clazz: Class[_ <: User] = user.getClass

    val fields: Array[Field] = clazz.getDeclaredFields

    fields.foreach(field => {
      field.setAccessible(true)

      val value: AnyRef = field.get(user)
      println(field.getName + ": " + field.get(user))
    })
  }

}
