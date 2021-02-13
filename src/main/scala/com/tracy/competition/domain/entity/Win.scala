package com.tracy.competition.domain.entity

import scala.beans.BeanProperty


/**
 * @author Tracy
 * @date 2021/2/9 1:14
 */
class Win extends Serializable {
  @BeanProperty var winId: String = _

  @BeanProperty var winRanking: Integer = _

  @BeanProperty var winName: String = _

  @BeanProperty var users: java.util.List[User] = _

  @BeanProperty var competition: Competition = _

  override def toString: String = {
    "Win{" +
      "winId='" + winId + '\'' +
      ", winRanking=" + winRanking +
      ", winName='" + winName + '\'' +
      ", users=" + users +
      ", competition=" + competition +
      '}'
  }
}
