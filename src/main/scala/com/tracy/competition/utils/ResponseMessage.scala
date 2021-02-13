package com.tracy.competition.utils

import com.tracy.competition.domain.enums.{ErrorEnum, SuccessEnum}

import scala.beans.BeanProperty


/**
 * @author Tracy
 * @date 2021/2/9 10:28
 */
class ResponseMessage extends Serializable {
  @BeanProperty var status: String = _
  @BeanProperty var msg: String = _
  @BeanProperty var data = new java.util.HashMap[String, Object]

  def this(status: String, msg: String) {
    this()
    this.status = status
    this.msg = msg
  }

  def this(successEnum: SuccessEnum) {
    this()
    this.status = successEnum.getSuccessCode
    this.msg = successEnum.getSuccessMsg
  }

  def this(errorEnum: ErrorEnum) {
    this()
    this.status = errorEnum.getErrorCode
    this.msg = errorEnum.getErrorMsg
  }
}

object ResponseMessage {
  def apply(status: String, msg: String): ResponseMessage = new ResponseMessage(status: String, msg: String)

  def apply(successEnum: SuccessEnum): ResponseMessage = new ResponseMessage(successEnum: SuccessEnum)

  def apply(errorEnum: ErrorEnum): ResponseMessage = new ResponseMessage(errorEnum: ErrorEnum)
}
