package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.{ApplyDao, TeamDao}
import com.tracy.competition.domain.entity.{Apply, Team, User}
import com.tracy.competition.service.TeamService
import org.apache.commons.collections4.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Isolation, Propagation, Transactional}

/**
 * @author Tracy
 * @date 2021/2/9 14:29
 */
@Service
class TeamServiceImpl extends TeamService {

  @Autowired private val teamDao: TeamDao = null
  @Autowired private val applyDao: ApplyDao = null

  /**
   * 保存新创建队伍信息
   *
   * @param team team
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def insertTeam(team: Team): Unit = {
    teamDao.insertTeam(team)
    //将自己增加到队伍中
    teamDao.insertUserTeam(team.getTeamId, team.getCaptain.getUserId)
  }

  /**
   * 获取当前用户创建的所有队伍
   *
   * @param userId userId
   * @return
   */
  override def findMyTeam(userId: String): util.List[Team] = teamDao.findMyTeam(userId)

  /**
   * 根据队伍id和自己的id获得除自己以外的成员列表
   *
   * @param teamId teamId
   * @param userId userId
   * @return
   */
  override def findUsersByTeamIdAndNotNowUser(teamId: String, userId: String): util.List[User] = {
    teamDao.findUsersByTeamIdAndNotNowUser(teamId, userId)
  }

  /**
   * 根据队伍信息修改数据库队伍数据
   *
   * @param team team
   * @return
   */
  override def updateMyTeam(team: Team): Unit = teamDao.updateMyTeam(team)

  /**
   * 删除队伍中的成员
   *
   * @param teamId teamId
   * @param userId userId
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def deleteTeamUser(teamId: String, userId: String): Unit = {
    teamDao.deleteTeamUser(teamId, userId)
    teamDao.updateTeamHeadcount(teamId)
  }

  /**
   * 解散队伍，并清空队员
   *
   * @param teamId teamId
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def deleteTeam(teamId: String): Unit = {
    //通过队伍id删除所有队员
    teamDao.deleteTeamUserByTeamId(teamId)
    //删除所有申请
    applyDao.deleteApplyByTeamId(teamId)
    teamDao.deleteTeam(teamId)
  }

  /**
   * 获取当前用户加入的所有队伍
   *
   * @param userId userId
   * @return
   */
  override def findJoinTeam(userId: String): util.List[Team] = teamDao.findJoinTeam(userId)

  /**
   * 根据用户id和比赛id删除一个队伍成员
   *
   * @param teamId teamId
   * @param userId userId
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def deleteTeamOneUser(teamId: String, userId: String): Unit = {
    teamDao.deleteTeamUser(teamId, userId)
    //获得当前队伍
    val team: Team = teamDao.findTeamById(teamId)
    //判断当前队伍状态,如果为已满人或已报名，修改队伍状态，反之不修改
    if ((team.getTeamState == 2) || (team.getTeamState == 3)) team.setTeamState(0)
    //队伍人数减1
    team.setTeamHeadcount(team.getTeamHeadcount - 1)
    teamDao.updateTeamStateAndHeadCount(team)
  }

  /**
   * 根据用户所有比赛获取所有处于正在申请状态申请
   *
   * @param teams teams
   * @return
   */
  override def findAllMyTeamApply(teams: util.List[Team]): util.List[Apply] = {
    if (CollectionUtils.isEmpty(teams)) null else teamDao.findAllMyTeamApply(teams)
  }

  /**
   * 根据用户所有比赛获取该用户处理的所有通过或拒绝的申请
   *
   * @param teams teams
   * @return
   */
  override def findAllMyHistoryTeamApply(teams: util.List[Team]): util.List[Apply] = {
    if (CollectionUtils.isEmpty(teams))
      null
    //获取审批过的申请列表
    else
      teamDao.findAllMyDispose(teams)
  }

  /**
   * 报名，修改队伍信息
   *
   * @param team team
   */
  override def updateTeam(team: Team): Unit = teamDao.updateTeam(team)

  /**
   * 根据用户id和竞赛id获取队伍
   *
   * @param userId        userId
   * @param competitionId competitionId
   * @return
   */
  override def findTeamByCaptainIdAndCompetitionId(userId: String, competitionId: String): Team = {
    teamDao.findTeamByCaptainIdAndCompetitionId(userId, competitionId)
  }

  /**
   * 取消队伍报名
   *
   * @param teamId teamId
   */
  override def updateTeamCancelApply(teamId: String): Unit = {
    teamDao.updateTeamCancelApply(teamId)
  }

  /**
   * 根据竞赛id及已报名的状态（3），获取队伍列表
   *
   * @param competitionId competitionId
   * @return
   */
  override def findTeamByCompetitionIdAndRegistered(competitionId: String): util.List[Team] = {
    teamDao.findTeamByCompetitionIdAndRegistered(competitionId)
  }

  /**
   * 根据队伍id和队长id获取队伍所有成员信息
   *
   * @param teamId    teamId
   * @param captainId captainId
   * @return
   */
  override def findUserListByTeamIdAndCaptainId(teamId: String, captainId: String): util.List[User] = {
    //获取队伍成员信息
    val users: util.List[User] = teamDao.findUserListByTeamId(teamId)
    println(users)
    println(users.size)
    users
  }
}
