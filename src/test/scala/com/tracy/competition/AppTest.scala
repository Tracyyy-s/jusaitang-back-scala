package com.tracy.competition

import com.tracy.competition.dao.{AdviceDao, UserDao}
import com.tracy.competition.domain.entity.{College, University, User}
import com.tracy.competition.domain.enums.SuccessEnum
import com.tracy.competition.utils.ResponseMessage
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Tracy
 * @date 2021/2/9 0:38
 */
@SpringBootTest
@RunWith(classOf[SpringRunner])
class AppTest {

  @Autowired
  val userDao: UserDao = null

  @Test
  def test(): Unit = {

    val university = new University
    university.setColleges(new java.util.ArrayList[College]())

    println(s"university = ${university}")
  }

  @Test
  def test02(): Unit = {
    val user: java.util.List[User] = userDao.getAllUser

    println(user)

    val user1: User = userDao.findUserByUserId("f1")
    println(user1)
  }

  @Test
  def test03(): Unit = {
    println(SuccessEnum.S_LOGIN_SUCCESS.getSuccessMsg)

    val resp: ResponseMessage = ResponseMessage(SuccessEnum.S_LOGIN_SUCCESS)

    println(resp.status + " " + resp.msg)
  }

  @Test
  def test04(): Unit = {
    val arr: Array[Int] = Array(1,2,3,4)

    def sum(arg: Int*): Int = {
      arg.sum
    }

    println(sum(arr: _*))

    val a :Int = 4
  }

  @Autowired val adviceDao: AdviceDao = null

  @Test
  def test05(): Unit = {

    println(adviceDao.getAllAdvice(1))
  }
}
