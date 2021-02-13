package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.CompetitionTypeDao
import com.tracy.competition.domain.entity.CompetitionType
import com.tracy.competition.service.CompetitionTypeService
import com.tracy.competition.utils.RedisUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 14:17
 */
@Service
class CompetitionTypeServiceImpl extends CompetitionTypeService {

  @Autowired private val redisUtil: RedisUtil = null
  @Autowired private val competitionTypeDao: CompetitionTypeDao = null

  /**
   * 获取全部比赛类型
   *
   * @return
   */
  override def findAllCompetitionType: util.List[CompetitionType] = {
    var competitionTypes: util.List[CompetitionType] = null
    if (redisUtil.hasKey("competitionTypes")) {
      competitionTypes = redisUtil.get("competitionTypes").asInstanceOf[util.List[CompetitionType]]
      println("Redis缓存取出的竞赛类型")
    }
    else {
      competitionTypes = competitionTypeDao.findAllCompetitionType
      redisUtil.set("competitionTypes", competitionTypes)
      println("数据库取出的竞赛类型")
    }
    competitionTypes
  }
}
