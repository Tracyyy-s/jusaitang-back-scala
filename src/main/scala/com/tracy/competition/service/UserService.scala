package com.tracy.competition.service

import java.util
import java.util.List

import com.tracy.competition.domain.entity.User

/**
 * @author Tracy
 * @date 2021/2/9 13:13
 */
trait UserService {
  /**
   * 通过用户名，密码查询用户
   *
   * @param user user
   * @return
   */
  def findByUsernameAndPassword(user: User): User

  /**
   * 通过用户名查询学院id
   *
   * @param userName userName
   * @return
   */
  def findCollegeIdByUserName(userName: String): String

  /**
   * 通过userName查询所有用户信息
   *
   * @param userName userName
   * @return
   */
  def findUserByUserName(userName: String): User

  /**
   * 修改用户密码
   *
   * @param user user
   */
  def updatePassword(user: User): Unit

  /**
   * 修改用户信息
   *
   * @param user user
   */
  def updateUser(user: User): Unit

  /**
   * 通过userId查询所有用户信息
   *
   * @param userId userId
   * @return
   */
  def findUserByUserId(userId: String): User

  /**
   * 获得所有用户
   *
   * @return
   */
  def getAllUser: util.List[User]
}
