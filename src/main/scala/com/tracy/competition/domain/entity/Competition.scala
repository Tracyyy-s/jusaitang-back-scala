package com.tracy.competition.domain.entity

import scala.beans.BeanProperty


/**
 * @author Tracy
 * @date 2021/2/9 1:13
 */
class Competition extends Serializable {
  @BeanProperty var competitionId: String = _

  @BeanProperty var college: College = _

  @BeanProperty var users: java.util.List[User] = _

  @BeanProperty var competitionName: String = _

  @BeanProperty var competitionState: Integer = _

  @BeanProperty var competitionFile: String = _

  @BeanProperty var competitionContent: String = _

  @BeanProperty var competitionType: String = _

  @BeanProperty var competitionTime: String = _

  @BeanProperty var competitionStoptime: java.lang.Long = 0L

  @BeanProperty var competitionLevel: Integer = _

  @BeanProperty var competitionSite: String = _

  @BeanProperty var competitionPeopleSum: Integer = _

  @BeanProperty var notifications: java.util.List[Notification] = _

  @BeanProperty var files: java.util.List[File] = _

  @BeanProperty var win: Win = _

  @BeanProperty var teams: java.util.List[Team] = _

  override def toString: String = {
    "Competition{" +
      "competitionId='" + competitionId + '\'' +
      ", college=" + college +
      ", users=" + users +
      ", competitionName='" + competitionName + '\'' +
      ", competitionState=" + competitionState +
      ", competitionFile='" + competitionFile + '\'' +
      ", competitionContent='" + competitionContent + '\'' +
      ", competitionType='" + competitionType + '\'' +
      ", competitionTime='" + competitionTime + '\'' +
      ", competitionStoptime=" + competitionStoptime +
      ", competitionLevel=" + competitionLevel +
      ", competitionSite='" + competitionSite + '\'' +
      ", competitionPeopleSum=" + competitionPeopleSum +
      ", notifications=" + notifications +
      ", files=" + files +
      ", win=" + win +
      ", teams=" + teams +
      '}'
  }
}
