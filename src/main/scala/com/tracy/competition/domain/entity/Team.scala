package com.tracy.competition.domain.entity


import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:13
 */
class Team extends Serializable {
  @BeanProperty var teamId: String = _

  @BeanProperty var captain: User = _

  @BeanProperty var applyTime: java.lang.Long = 0L

  @BeanProperty var teamName: String = _

  @BeanProperty var teamState: Integer = _

  @BeanProperty var teamHeadcount: Integer = _

  @BeanProperty var competition: Competition = _

  @BeanProperty var users: java.util.List[User] = _

  @BeanProperty var teamContent: String = _

  @BeanProperty var applies: java.util.List[Apply] = _

  override def toString: String = {
    "Team{" +
      "teamId='" + teamId + '\'' +
      ", captain=" + captain +
      ", applyTime=" + applyTime +
      ", teamName='" + teamName + '\'' +
      ", teamState=" + teamState +
      ", teamHeadcount=" + teamHeadcount +
      ", competition=" + competition +
      ", users=" + users +
      ", teamContent='" + teamContent + '\'' +
      ", applies=" + applies +
      '}'
  }
}
