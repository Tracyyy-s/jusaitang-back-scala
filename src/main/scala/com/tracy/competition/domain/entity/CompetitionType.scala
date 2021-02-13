package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:13
 */
class CompetitionType extends Serializable {
  @BeanProperty var competitionTypeId: String = _
  @BeanProperty var competitionTypeName: String = _

  override def toString: String = {
    "CompetitionType{" +
      "competitionTypeId='" + competitionTypeId + '\'' +
      ", competitionTypeName='" + competitionTypeName + '\'' +
      '}'
  }
}
