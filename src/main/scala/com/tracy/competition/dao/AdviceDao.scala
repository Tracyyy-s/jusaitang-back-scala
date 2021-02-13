package com.tracy.competition.dao

import java.util

import com.tracy.competition.domain.entity.Advice
import org.apache.ibatis.annotations.{Mapper, Param}


/**
 * @author Tracy
 * @date 2020/11/4 17:09
 */
@Mapper
trait AdviceDao {
  /**
   * 插入反馈建议
   *
   * @param advice advice
   */
  def insertAdvice(advice: Advice): Unit

  /**
   * 获取所有的投诉建议
   *
   * @return
   */
  def getAllAdvice(@Param("adviceState") adviceState: Integer): util.List[Advice]

  /**
   * 更新意见
   *
   * @param advice advice
   */
  def updateAdvice(advice: Advice): Unit
}
