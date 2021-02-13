package com.tracy.competition.config.exception

import com.tracy.competition.domain.enums.ErrorEnum
import com.tracy.competition.utils.ResponseMessage
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseBody}

/**
 * @author Tracy
 * @date 2021/2/9 11:35
 */
@ControllerAdvice
@ResponseBody
class GlobalExceptionHandler {

  /**
   * 空指针异常处理handler
   *
   * @return
   */
  @ExceptionHandler(Array(classOf[NullPointerException]))
  def nullPointerException(): ResponseMessage = {
    ResponseMessage(ErrorEnum.E_NULLPOINTER)
  }
}
