package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 0:53
 */
class College extends Serializable {

  @BeanProperty var collegeId: String = _

  @BeanProperty var university: University = _

  @BeanProperty var collegeName: String = _

  @BeanProperty var users: java.util.List[User] = _

  override def toString: String = {
    "College{" +
      "collegeId='" + collegeId + '\'' +
      ", university=" + university +
      ", collegeName='" + collegeName + '\'' +
      ", users=" + users +
      '}'
  }
}
