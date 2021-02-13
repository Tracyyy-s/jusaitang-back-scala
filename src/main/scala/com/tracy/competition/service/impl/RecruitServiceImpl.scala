package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.RecruitDao
import com.tracy.competition.domain.entity.{Team, User, UserTeam}
import com.tracy.competition.service.RecruitService
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 14:26
 */
@Service
class RecruitServiceImpl extends RecruitService {

  @Autowired private val recruitDao: RecruitDao = null

  /**
   * 根据队伍状态获取队伍列表
   *
   * @param teamState teamState
   * @return
   */
  override def findAllTeamByState(teamState: Integer): util.List[Team] = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    recruitDao.findAllTeamByState(teamState, user.getUserId)
  }

  /**
   * 查询该用户的所有加入的队伍列表
   *
   * @param userId userId
   * @return
   */
  override def findAllUserTeam(userId: String): util.List[UserTeam] = {
    recruitDao.findAllUserTeam(userId)
  }
}
