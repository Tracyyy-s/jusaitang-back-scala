package com.tracy.competition.domain.entity

import java.util
import java.util.List

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:06
 */
class Promise extends Serializable {
  @BeanProperty var promiseId: String = _

  @BeanProperty var promiseName: String = _

  @BeanProperty var roles: java.util.List[Role] = _

  override def toString: String = {
    "Promise{" +
      "promiseId='" + promiseId + '\'' +
      ", promiseName='" + promiseName + '\'' +
      ", roles=" + roles +
      '}'
  }
}
