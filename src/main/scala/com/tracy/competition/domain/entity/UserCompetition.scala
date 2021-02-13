package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:14
 */
class UserCompetition extends Serializable {
  @BeanProperty var competition: Competition = _

  @BeanProperty var user: User = _

  @BeanProperty var date: java.lang.Long = 0L

  @BeanProperty var winRanking: Integer = _

  @BeanProperty var winLevelName:String = _

  override def toString: String = {
    "UserCompetition{" +
      "competition=" + competition +
      ", user=" + user +
      ", date=" + date +
      ", winRanking=" + winRanking +
      ", winLevelName='" + winLevelName + '\'' +
      '}'
  }
}
