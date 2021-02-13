package com.tracy.competition.utils

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:15
 */
class CompetitionNotificationVO {
  @BeanProperty var notificationId: String = _

  @BeanProperty var competitionId: String = _

  @BeanProperty var competitionName: String = _

  @BeanProperty var notificationTitle: String = _

  @BeanProperty var competitionContent: String = _

  @BeanProperty var competitionSite: String = _

  @BeanProperty var competitionType: String = _

  @BeanProperty var competitionLevel: Integer = _

  @BeanProperty var competitionTime: String = _

  @BeanProperty var collegeId: String = _

  @BeanProperty var notificationState: Integer = _

  @BeanProperty var competitionState: Integer = _

  @BeanProperty var competitionPeopleSum: Integer = _

  override def toString: String = {
    "CompetitionNotificationVO{" +
      "notificationId='" + notificationId + '\'' +
      ", competitionId='" + competitionId + '\'' +
      ", competitionName='" + competitionName + '\'' +
      ", notificationTitle='" + notificationTitle + '\'' +
      ", competitionContent='" + competitionContent + '\'' +
      ", competitionSite='" + competitionSite + '\'' +
      ", competitionType='" + competitionType + '\'' +
      ", competitionLevel=" + competitionLevel +
      ", competitionTime='" + competitionTime + '\'' +
      ", collegeId='" + collegeId + '\'' +
      ", notificationState=" + notificationState +
      ", competitionState=" + competitionState +
      ", competitionPeopleSum=" + competitionPeopleSum +
      '}'
  }
}
