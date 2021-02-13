package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.UserDao
import com.tracy.competition.domain.entity.User
import com.tracy.competition.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 13:21
 */
@Service
class UserServiceImpl extends UserService{

  @Autowired private val userDao: UserDao = null
  /**
   * 通过用户名，密码查询用户
   *
   * @param user user
   * @return
   */
  override def findByUsernameAndPassword(user: User): User = userDao.findByUsernameAndPassword(user)

  /**
   * 通过用户名查询学院id
   *
   * @param userName userName
   * @return
   */
  override def findCollegeIdByUserName(userName: String): String = userDao.findCollegeIdByUserName(userName)

  /**
   * 通过userName查询所有用户信息
   *
   * @param userName userName
   * @return
   */
  override def findUserByUserName(userName: String): User = userDao.findUserByUserName(userName)

  /**
   * 修改用户密码
   *
   * @param user user
   */
  override def updatePassword(user: User): Unit = userDao.updatePassword(user)

  /**
   * 修改用户信息
   *
   * @param user user
   */
  override def updateUser(user: User): Unit = userDao.updateUser(user)

  /**
   * 通过userId查询所有用户信息
   *
   * @param userId userId
   * @return
   */
  override def findUserByUserId(userId: String): User = userDao.findUserByUserId(userId)

  /**
   * 获得所有用户
   *
   * @return
   */
  override def getAllUser: util.List[User] = userDao.getAllUser
}
