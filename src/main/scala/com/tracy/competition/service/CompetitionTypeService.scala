package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.CompetitionType

/**
 * @author Tracy
 * @date 2021/2/9 13:34
 */
trait CompetitionTypeService {
  /**
   * 获取全部比赛类型
   *
   * @return
   */
  def findAllCompetitionType: util.List[CompetitionType]
}
