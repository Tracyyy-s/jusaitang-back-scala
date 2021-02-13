package com.tracy.competition.domain.entity

import java.util
import java.util.List

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:06
 */
class Role extends Serializable {
  @BeanProperty var roleId: String = _

  @BeanProperty var roleName: String = _

  @BeanProperty var promises: util.List[Promise] = _

  @BeanProperty var users: util.List[User] = _

  override def toString: String = {
    "Role{" +
      "roleId='" + roleId + '\'' +
      ", roleName='" + roleName + '\'' +
      ", promises=" + promises +
      ", users=" + users +
      '}'
  }
}
