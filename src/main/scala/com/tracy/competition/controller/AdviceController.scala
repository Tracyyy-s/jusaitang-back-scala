package com.tracy.competition.controller

import java.util
import java.util.UUID

import com.tracy.competition.domain.entity.Advice
import com.tracy.competition.service.impl.AdviceServiceImpl
import com.tracy.competition.utils.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}

/**
 * @author Tracy
 * @date 2021/2/9 10:25
 */
@RestController
@RequestMapping(Array("/advice"))
class AdviceController {

  @Autowired
  private val adviceService: AdviceServiceImpl = null

  /**
   * 新增反馈建议
   *
   * @param advice advice
   * @return
   */
  @RequestMapping(value = Array("/submitAdvice"), method = Array(RequestMethod.POST))
  def submitAdvice(@RequestBody advice: Advice): ResponseMessage = {
    //设置投诉建议人为当前用户
    //    advice.setUser(SecurityUtils.getSubject.getPrincipal.asInstanceOf[User])
    advice.setAdviceId(UUID.randomUUID.toString)
    advice.setAdviceDate(System.currentTimeMillis)

    //状态0未处理，1已处理
    advice.setAdviceState(0)
    println(advice)
    try {
      adviceService.insertAdvice(advice)
      ResponseMessage("1", "提交成功！")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "提交失败！")
    }
  }

  /**
   * 通过状态获得所有反馈建议，状态 0未处理，1已处理
   *
   * @param adviceState adviceState
   * @return
   */
  @RequestMapping(value = Array("/getAllAdvice"), method = Array(RequestMethod.GET))
  def getAllAdvice(adviceState: Integer): ResponseMessage = {
    try {
      val advices: util.List[Advice] = adviceService.getAllAdvice(adviceState)
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功！")
      responseMessage.getData.put("advices", advices)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败！")
    }
  }

  /**
   * 处理反馈建议，根据id
   *
   * @param adviceId adviceId
   * @return
   */
  @RequestMapping(value = Array("/disposeAdvice"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def disposeAdvice(adviceId: String): ResponseMessage = {
    val advice = new Advice
    advice.setAdviceId(adviceId)
    advice.setDisposeTime(System.currentTimeMillis)
    advice.setAdviceState(1)
    try {
      adviceService.updateAdvice(advice)
      ResponseMessage("1", "处理成功，可到反馈建议管理->已处理反馈 查看")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "处理失败")
    }
  }
}
