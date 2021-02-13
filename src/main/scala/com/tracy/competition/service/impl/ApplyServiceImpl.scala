package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.{ApplyDao, TeamDao}
import com.tracy.competition.domain.entity.{Apply, UserCompetition, UserTeam}
import com.tracy.competition.service.ApplyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Isolation, Propagation, Transactional}

/**
 * @author Tracy
 * @date 2021/2/9 14:07
 */
@Service
class ApplyServiceImpl extends ApplyService {

  @Autowired private val applyDao: ApplyDao = null
  @Autowired private val teamDao: TeamDao = null

  /**
   * 保存报名信息
   *
   * @param userCompetition userCompetition
   * @return
   */
  override def insertApply(userCompetition: UserCompetition): Unit = {
    applyDao.insertApply(userCompetition)
  }


  /**
   * 根据用户id和竞赛id查询报名信息
   *
   * @param userCompetition userCompetition
   * @return
   */
  override def findApplyByUserIdAndCompetitionId(userCompetition: UserCompetition): UserCompetition = {
    applyDao.findApplyByUserIdAndCompetitionId(userCompetition)
  }

  /**
   * 根据用户id和竞赛id删除报名信息
   *
   * @param userCompetition userCompetition
   */
  override def deleteApply(userCompetition: UserCompetition): Unit = {
    applyDao.deleteApply(userCompetition)
  }


  /**
   * 根据竞赛id获取获奖列表
   *
   * @param competitionId competitionId
   * @return
   */
  override def findWinByCompetitionId(competitionId: String): util.List[UserCompetition] = {
    applyDao.findWinByCompetitionId(competitionId)
  }


  /**
   * 保存队伍申请
   *
   * @param apply apply
   * @return
   */
  override def insertTeamApply(apply: Apply): Unit = applyDao.insertTeamApply(apply)

  /**
   * 通过处理加入队伍的结果，修改申请信息
   *
   * @param apply apply
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def updateApplyByDispose(apply: Apply): Unit = {
    applyDao.updateApplyByDispose(apply)
    //如果为通过加入队伍操作
    if (apply.getApplyState == 1) {
      val userTeam = new UserTeam
      userTeam.setUserId(apply.getUser.getUserId)
      userTeam.setTeamId(apply.getTeam.getTeamId)
      System.out.println(userTeam)
      //保存用户-队伍对应信息
      applyDao.insertUserTeam(userTeam)
      teamDao.updateTeamStateAndHeadCount(apply.getTeam)
    }
  }

  /**
   * 获取用户所有申请中的申请
   *
   * @param userId     userId
   * @param applyState applyState
   * @return
   */
  override def findMyApplyList(userId: String, applyState: Integer): util.List[Apply] = {
    applyDao.findMyApplyList(userId, applyState)
  }


  /**
   * 获取用户所有已结束的申请，状态参数，正在申请的状态0，除去状态为0的都为已结束申请
   *
   * @param userId     userId
   * @param applyState applyState
   * @return
   */
  override def findMyHistoryApplyList(userId: String, applyState: Integer): util.List[Apply] = {
    applyDao.findMyHistoryApplyList(userId, applyState)
  }

  /**
   * 根据用户id,申请状态，队伍id查找记录
   *
   * @param apply apply
   * @return
   */
  override def findApplyByUserIdAndApplyStateAndTeamId(apply: Apply): Boolean = {
    applyDao.findApplyByUserIdAndApplyStateAndTeamId(apply) != null
  }

  /**
   * 根据申请id删除申请
   *
   * @param applyId applyId
   */
  override def deleteTeamApply(applyId: String): Unit = applyDao.deleteTeamApply(applyId)
}
