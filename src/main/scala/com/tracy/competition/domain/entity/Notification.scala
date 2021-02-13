package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:13
 */
class Notification extends Serializable {
  @BeanProperty var notificationId: String = _

  @BeanProperty var notificationContent: String = _

  @BeanProperty var competition: Competition = _

  @BeanProperty var notificationTime: java.lang.Long = 0L

  @BeanProperty var notificationTitle: String = _

  @BeanProperty var notificationType: Integer = _

  @BeanProperty var notificationState: Integer = _

  @BeanProperty var files: java.util.List[File] = _

  @BeanProperty var userIds:java.util.List[String] = _

  override def toString: String = {
    "Notification{" +
      "notificationId='" + notificationId + '\'' +
      ", notificationContent='" + notificationContent + '\'' +
      ", competition=" + competition +
      ", notificationTime='" + notificationTime + '\'' +
      ", notificationTitle='" + notificationTitle + '\'' +
      ", notificationType=" + notificationType +
      ", notificationState=" + notificationState +
      ", files=" + files +
      '}'
  }
}
