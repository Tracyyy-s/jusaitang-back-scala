package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.{Apply, UserCompetition}

/**
 * @author Tracy
 * @date 2021/2/9 13:30
 */
trait ApplyService {

  /**
   * 保存报名信息
   *
   * @param userCompetition userCompetition
   * @return
   */
  def insertApply(userCompetition: UserCompetition): Unit

  /**
   * 根据用户id和竞赛id查询报名信息
   *
   * @param userCompetition userCompetition
   * @return
   */
  def findApplyByUserIdAndCompetitionId(userCompetition: UserCompetition): UserCompetition

  /**
   * 根据用户id和竞赛id删除报名信息
   *
   * @param userCompetition userCompetition
   */
  def deleteApply(userCompetition: UserCompetition): Unit

  /**
   * 根据竞赛id获取获奖列表
   *
   * @param competitionId competitionId
   * @return
   */
  def findWinByCompetitionId(competitionId: String): util.List[UserCompetition]

  /**
   * 保存队伍申请
   *
   * @param apply apply
   * @return
   */
  def insertTeamApply(apply: Apply): Unit

  /**
   * 通过处理加入队伍的结果，修改申请信息
   *
   * @param apply apply
   */
  def updateApplyByDispose(apply: Apply): Unit

  /**
   * 获取用户所有申请中的申请
   *
   * @param userId userId
   * @param applyState applyState
   * @return
   */
  def findMyApplyList(userId: String, applyState: Integer): util.List[Apply]

  /**
   * 获取用户所有已结束的申请，状态参数，正在申请的状态0，除去状态为0的都为已结束申请
   *
   * @param userId userId
   * @param applyState applyState
   * @return
   */
  def findMyHistoryApplyList(userId: String, applyState: Integer): util.List[Apply]

  /**
   * 根据用户id,申请状态，队伍id查找记录
   *
   * @param apply apply
   * @return
   */
  def findApplyByUserIdAndApplyStateAndTeamId(apply: Apply): Boolean

  /**
   * 根据申请id删除申请
   *
   * @param applyId applyId
   */
  def deleteTeamApply(applyId: String): Unit
}
