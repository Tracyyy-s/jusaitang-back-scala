package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 0:54
 */
class University extends Serializable {

  @BeanProperty var universityId: String = _
  @BeanProperty var universityName: String = _
  @BeanProperty var universityCity: String = _
  @BeanProperty var colleges: java.util.List[College] = _

  override def toString: String = {
    "University{" +
      "universityId='" + universityId + '\'' +
      ", universityName='" + universityName + '\'' +
      ", universityCity='" + universityCity + '\'' +
      ", colleges=" + colleges +
      '}'
  }
}
