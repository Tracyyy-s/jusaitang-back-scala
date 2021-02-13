package com.tracy.competition.domain.entity

import scala.beans.BeanProperty

/**
 * @author Tracy
 * @date 2021/2/9 1:13
 */
class File extends Serializable {
  @BeanProperty var fileId: String = _

  @BeanProperty var filePath: String = _

  @BeanProperty var fileName: String = _

  @BeanProperty var competition: Competition = _

  @BeanProperty var notification: Notification = _

  override def toString: String = {
    "File{" +
      "fileId='" + fileId + '\'' +
      ", filePath='" + filePath + '\'' +
      ", fileName='" + fileName + '\'' +
      ", competition=" + competition +
      ", notification=" + notification +
      '}'
  }
}
