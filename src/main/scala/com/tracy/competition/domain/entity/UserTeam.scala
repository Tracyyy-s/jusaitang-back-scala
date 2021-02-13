package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:14
 */
class UserTeam extends Serializable {
  @BeanProperty var userId: String = _

  @BeanProperty var teamId: String = _

  override def toString: String = {
    "UserTeam{" +
      "userId='" + userId + '\'' +
      ", teamId='" + teamId + '\'' +
      '}'
  }
}
