package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 0:36
 */
class User extends Serializable {

  @BeanProperty var userId: String = _

  @BeanProperty var userName: String = _

  @BeanProperty var password: String = _

  @BeanProperty var name: String = _

  @BeanProperty var phone: String = _

  @BeanProperty var email: String = _

  @BeanProperty var sex: String = _

  @BeanProperty var period: Integer = _

  @BeanProperty var college: College = _

  @BeanProperty var userClassName: String = _

  @BeanProperty var Roles: java.util.Set[Role] = _

  override def toString: String = {
    "User{" +
      "userId='" + userId + '\'' +
      ", userName='" + userName + '\'' +
      ", password='" + password + '\'' +
      ", name='" + name + '\'' +
      ", phone='" + phone + '\'' +
      ", email='" + email + '\'' +
      ", sex='" + sex + '\'' +
      ", period=" + period +
      ", college=" + college +
      ", userClassName='" + userClassName + '\'' +
      ", Roles=" + Roles +
      '}'
  }

}