package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.AdviceDao
import com.tracy.competition.domain.entity.Advice
import com.tracy.competition.service.AdviceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 11:11
 */
@Service
class AdviceServiceImpl extends AdviceService {

  @Autowired private val adviceDao: AdviceDao = null

  /**
   * 插入反馈建议
   *
   * @param advice advice
   */
  override def insertAdvice(advice: Advice): Unit = {
    adviceDao.insertAdvice(advice)
  }

  /**
   * 获取所有的投诉建议
   *
   * @param adviceState adviceState
   * @return
   */
  override def getAllAdvice(adviceState: Integer): util.List[Advice] = {
    adviceDao.getAllAdvice(adviceState)
  }

  /**
   * 更新处理后的建议信息
   *
   * @param advice advice
   */
  override def updateAdvice(advice: Advice): Unit = {
    adviceDao.updateAdvice(advice)
  }
}
