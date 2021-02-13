package com.tracy.competition.dao

import java.util

import com.tracy.competition.domain.entity.{Apply, Team, User}
import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/9 17:17
 */
@Mapper
trait TeamDao {
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
   * 根据队伍id和自己的id增加到队伍中
   *
   * @param teamId teamId
   * @param userId userId
   * @return
   */
  def insertUserTeam(teamId: String, userId: String): Unit

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
   * 通过队伍id删除所有队员
   *
   * @param teamId teamId
   */
  def deleteTeamUserByTeamId(teamId: String): Unit

  /**
   * 获取当前用户加入的所有队伍
   *
   * @param userId userId
   * @return
   */
  def findJoinTeam(userId: String): util.List[Team]

  /**
   * 根据用户所有比赛获取所有已处理的申请
   *
   * @param teams teams
   * @return
   */
  def findAllMyDispose(teams: util.List[Team]): util.List[Apply]

  /**
   * 根据用户所有比赛获取所有待处理的申请
   *
   * @param teams teams
   * @return
   */
  def findAllMyTeamApply(teams: util.List[Team]): util.List[Apply]

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
   * 修改队伍的状态和人数
   *
   * @param team team
   */
  def updateTeamStateAndHeadCount(team: Team): Unit

  /**
   * 根据竞赛id及已报名的状态（3），获取队伍列表
   *
   * @param competitionId competitionId
   * @return
   */
  def findTeamByCompetitionIdAndRegistered(competitionId: String): util.List[Team]

  /**
   * 修改队伍人数 为当前人数-1
   *
   * @param teamId teamId
   */
  def updateTeamHeadcount(teamId: String): Unit

  /**
   * 根据队伍id获取除队长以外的队员信息
   *
   * @param teamId teamId
   * @return
   */
  def findUserListByTeamId(teamId: String): util.List[User]

  /**
   * 根据id查询队伍
   *
   * @param teamId teamId
   * @return
   */
  def findTeamById(teamId: String): Team
}
