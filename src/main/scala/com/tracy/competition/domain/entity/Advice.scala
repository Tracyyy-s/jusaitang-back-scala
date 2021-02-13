package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:12
 */
class Advice extends Serializable {
  @BeanProperty var adviceId: String = _

  @BeanProperty var user: User = _

  @BeanProperty var adviceType: String = _

  @BeanProperty var adviceState: Integer = _

  @BeanProperty var adviceContent: String = _

  @BeanProperty var adviceDate: java.lang.Long = 0L

  @BeanProperty var disposeTime: java.lang.Long = 0L

  override def toString: String = {
    "Advice{" +
      "adviceId='" + adviceId + '\'' +
      ", user=" + user +
      ", adviceType='" + adviceType + '\'' +
      ", adviceState=" + adviceState +
      ", adviceContent='" + adviceContent + '\'' +
      ", adviceDate=" + adviceDate +
      ", disposeTime=" + disposeTime +
      '}'
  }
}
