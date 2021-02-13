package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:13
 */
class Apply extends Serializable {
  @BeanProperty var applyId: String = _

  @BeanProperty var applyTime: java.lang.Long = 0L

  @BeanProperty var applyContent: String = _

  @BeanProperty var applyDisposeTime: java.lang.Long = 0L

  @BeanProperty var applyState: Integer = _

  @BeanProperty var user: User = _

  @BeanProperty var team: Team = _

  override def toString: String = {
    "Apply{" +
      "applyId='" + applyId + '\'' +
      ", applyTime=" + applyTime +
      ", applyContent='" + applyContent + '\'' +
      ", applyDisposeTime=" + applyDisposeTime +
      ", applyState=" + applyState +
      ", user=" + user +
      ", team=" + team +
      '}'
  }
}
