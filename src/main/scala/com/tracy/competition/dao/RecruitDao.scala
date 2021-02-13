package com.tracy.competition.dao

import java.util
import java.util.List

import com.tracy.competition.domain.entity.{Team, UserTeam}
import org.apache.ibatis.annotations.{Mapper, Param}

/**
 * @author Tracy
 * @date 2021/2/9 17:15
 */
@Mapper trait RecruitDao {
  /**
   * 根据队伍状态获取队伍列表
   *
   * @param teamState teamState
   * @param captainId captainId
   * @return
   */
  def findAllTeamByState(@Param("teamState") teamState: Integer, @Param("captainId") captainId: String): util.List[Team]

  /**
   * 查询该用户的所有加入的队伍列表
   *
   * @param userId userId
   * @return
   */
  def findAllUserTeam(userId: String): util.List[UserTeam]
}

