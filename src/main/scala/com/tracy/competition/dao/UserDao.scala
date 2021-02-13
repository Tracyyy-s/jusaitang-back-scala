package com.tracy.competition.dao

import java.util

import com.tracy.competition.domain.entity.User
import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/9 17:19
 */
@Mapper
trait UserDao {
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
   * 修改用户信息
   *
   * @param user user
   */
  def updateUser(user: User): Unit

  /**
   * 修改用户密码
   *
   * @param user user
   */
  def updatePassword(user: User): Unit

  /**
   * 根据用户id获取用户信息
   *
   * @param captainId captainId
   * @return
   */
  def findUserByUserId(captainId: String): User

  /**
   * 获得所有用户
   *
   * @return
   */
  def getAllUser: util.List[User]
}

