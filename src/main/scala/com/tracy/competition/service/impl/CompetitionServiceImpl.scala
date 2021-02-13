package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.CompetitionDao
import com.tracy.competition.domain.entity.{Competition, User, UserCompetition}
import com.tracy.competition.service.CompetitionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 14:14
 */
@Service
class CompetitionServiceImpl extends CompetitionService {

  @Autowired private val competitionDao: CompetitionDao = null

  /**
   * 通过竞赛id查询比赛详情
   *
   * @param competitionId competitionId
   * @return
   */
  override def findCompetitionById(competitionId: String): Competition = {
    competitionDao.findCompetitionById(competitionId)
  }

  /**
   * 查询所有比赛详情
   *
   * @return
   */
  override def findAllCompetition: util.List[Competition] = competitionDao.findAllCompetition;

  /**
   * 根据比赛id获得比赛列表
   *
   * @param competitionId competitionId
   * @return
   */
  override def findUserByCompetitionId(competitionId: String): util.List[User] = {
    competitionDao.findUserByCompetitionId(competitionId)
  }

  /**
   * 根据当前用户id获得已参加列表
   *
   * @param userId userId
   * @return
   */
  override def findCompetitionListByUserId(userId: String): util.List[UserCompetition] = {
    val competitions: util.List[UserCompetition] = competitionDao.findCompetitionListByUserId(userId)
    //获取组队赛已报名列表
    val competitions1: util.List[UserCompetition] = competitionDao.findTeamCompetitionListByUserId(userId)
    println("------------" + competitions1)
    competitions.addAll(competitions1)
    competitions
  }

  /**
   * 查询所有组队比赛列表
   *
   * @return
   */
  override def findAllTeamCompetition: util.List[Competition] = {
    competitionDao.findAllTeamCompetition
  }

  /**
   * 通过竞赛id,查询报名该竞赛的用户列表
   *
   * @param competitionId competitionId
   * @return
   */
  override def findUserList(competitionId: String): util.List[User] = {
    competitionDao.findUserList(competitionId)
  }
}
