package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.{Team, UserTeam}

/**
 * @author Tracy
 * @date 2021/2/9 13:36
 */
trait RecruitService {
  /**
   * 根据队伍状态获取队伍列表
   *
   * @param teamState teamState
   * @return
   */
  def findAllTeamByState(teamState: Integer): util.List[Team]

  /**
   * 查询该用户的所有加入的队伍列表
   *
   * @param userId userId
   * @return
   */
  def findAllUserTeam(userId: String): util.List[UserTeam]
}
