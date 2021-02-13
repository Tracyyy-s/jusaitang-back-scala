package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:14
 */
class UserNotification extends Serializable {
  @BeanProperty var state: Integer = _

  @BeanProperty var userId: String = _

  @BeanProperty var notificationId: String = _

  override def toString: String = {
    "UserNotification{" +
      "state=" + state +
      ", userId='" + userId + '\'' +
      ", notificationId='" + notificationId + '\'' +
      '}'
  }
}
