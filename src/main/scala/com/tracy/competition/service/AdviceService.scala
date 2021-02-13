package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.Advice

/**
 * @author Tracy
 * @date 2021/2/9 11:11
 */
trait AdviceService {
  /**
   * 插入反馈建议
   *
   * @param advice advice
   */
  def insertAdvice(advice: Advice): Unit

  /**
   * 获取所有的投诉建议
   *
   * @param adviceState adviceState
   * @return
   */
  def getAllAdvice(adviceState: Integer): util.List[Advice]

  /**
   * 更新处理后的建议信息
   *
   * @param advice advice
   */
  def updateAdvice(advice: Advice): Unit
}
