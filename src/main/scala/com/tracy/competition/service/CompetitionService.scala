package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.{Competition, User, UserCompetition}

/**
 * @author Tracy
 * @date 2021/2/9 13:33
 */
trait CompetitionService {
  /**
   * 通过竞赛id查询比赛详情
   *
   * @param competitionId competitionId
   * @return
   */
  def findCompetitionById(competitionId: String): Competition

  /**
   * 查询所有比赛详情
   *
   * @return
   */
  def findAllCompetition: util.List[Competition]

  /**
   * 根据比赛id获得比赛列表
   *
   * @param competitionId competitionId
   * @return
   */
  def findUserByCompetitionId(competitionId: String): util.List[User]

  /**
   * 根据当前用户id获得已参加列表
   *
   * @param userId userId
   * @return
   */
  def findCompetitionListByUserId(userId: String): util.List[UserCompetition]

  /**
   * 查询所有组队比赛列表
   *
   * @return
   */
  def findAllTeamCompetition: util.List[Competition]

  /**
   * 通过竞赛id,查询报名该竞赛的用户列表
   *
   * @param competitionId competitionId
   * @return
   */
  def findUserList(competitionId: String): util.List[User]
}
