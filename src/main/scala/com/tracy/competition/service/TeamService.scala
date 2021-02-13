package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.{Apply, Team, User}

/**
 * @author Tracy
 * @date 2021/2/9 13:37
 */
trait TeamService {
  /**
   * 保存新创建队伍信息
   *
   * @param team team
   * @return
   */
  def insertTeam(team: Team): Unit

  /**
   * 获取当前用户创建的所有队伍
   *
   * @param userId userId
   * @return
   */
  def findMyTeam(userId: String): util.List[Team]

  /**
   * 根据队伍id和自己的id获得除自己以外的成员列表
   *
   * @param teamId teamId
   * @param userId userId
   * @return
   */
  def findUsersByTeamIdAndNotNowUser(teamId: String, userId: String): util.List[User]

  /**
   * 根据队伍信息修改数据库队伍数据
   *
   * @param team team
   * @return
   */
  def updateMyTeam(team: Team): Unit

  /**
   * 删除队伍中的成员
   *
   * @param teamId teamId
   * @param userId userId
   */
  def deleteTeamUser(teamId: String, userId: String): Unit

  /**
   * 解散队伍，并清空队员
   *
   * @param teamId teamId
   */
  def deleteTeam(teamId: String): Unit

  /**
   * 获取当前用户加入的所有队伍
   *
   * @param userId userId
   * @return
   */
  def findJoinTeam(userId: String): util.List[Team]

  /**
   * 根据用户id和比赛id删除一个队伍成员
   *
   * @param teamId teamId
   * @param userId userId
   */
  def deleteTeamOneUser(teamId: String, userId: String): Unit

  /**
   * 根据用户所有比赛获取所有处于正在申请状态申请
   *
   * @param teams teams
   * @return
   */
  def findAllMyTeamApply(teams: util.List[Team]): util.List[Apply]

  /**
   * 根据用户所有比赛获取该用户处理的所有通过或拒绝的申请
   *
   * @param teams teams
   * @return
   */
  def findAllMyHistoryTeamApply(teams: util.List[Team]): util.List[Apply]

  /**
   * 报名，修改队伍信息
   *
   * @param team team
   */
  def updateTeam(team: Team): Unit

  /**
   * 根据用户id和竞赛id获取队伍
   *
   * @param userId userId
   * @param competitionId competitionId
   * @return
   */
  def findTeamByCaptainIdAndCompetitionId(userId: String, competitionId: String): Team

  /**
   * 取消队伍报名
   *
   * @param teamId teamId
   */
  def updateTeamCancelApply(teamId: String): Unit

  /**
   * 根据竞赛id及已报名的状态（3），获取队伍列表
   *
   * @param competitionId competitionId
   * @return
   */
  def findTeamByCompetitionIdAndRegistered(competitionId: String): util.List[Team]

  /**
   * 根据队伍id和队长id获取队伍所有成员信息
   *
   * @param teamId teamId
   * @param captainId captainId
   * @return
   */
  def findUserListByTeamIdAndCaptainId(teamId: String, captainId: String): util.List[User]
}
