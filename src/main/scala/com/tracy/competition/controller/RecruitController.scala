package com.tracy.competition.controller

import java.util
import java.util.{ArrayList, List}

import com.tracy.competition.domain.entity.{Team, User, UserTeam}
import com.tracy.competition.service.{RecruitService, TeamService}
import com.tracy.competition.utils.ResponseMessage
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * @author Tracy
 * @date 2021/2/9 15:35
 */
@Controller
@RequestMapping(Array("/recruit")) class RecruitController {
  @Autowired private val recruitService: RecruitService = null
  @Autowired private val teamService: TeamService = null

  /**
   * 根据队伍状态获取正在招募的队伍列表
   *
   * @return
   */
  @RequestMapping(value = Array("/findAllRecruitTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findAllRecruitTeam: ResponseMessage = try { //队伍状态为1的为未满员，正在招募
    val teams: util.List[Team] = recruitService.findAllTeamByState(1)
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    val userTeams: util.List[UserTeam] = recruitService.findAllUserTeam(user.getUserId)
    //存放未加入且正在招募的队伍
    val teams1: util.List[Team] = new util.ArrayList[Team]
    var i: Int = 0
    import scala.collection.JavaConverters._
    import scala.util.control.Breaks._
    for (team <- teams.asScala) {
      i = 0
      breakable({
        while ( {
          i < userTeams.size
        }) {
          if (team.getTeamId == userTeams.get(i).getTeamId)
            break //todo: break is not supported

          i += 1
        }
      })
      if (i == userTeams.size) teams1.add(team)
    }
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("teams", teams1)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  @RequestMapping(value = Array("/recruitUser"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def recruitUser(teamId: String): ResponseMessage = {
    val team: Team = new Team
    team.setTeamId(teamId)
    //设置队伍状态为1,即招募中
    team.setTeamState(1)
    try {
      teamService.updateTeam(team)
      ResponseMessage("1", "发起招募成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "发起招募失败")
    }
  }

  @RequestMapping(value = Array("/cancelRecruit"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def cancelRecruit(teamId: String): ResponseMessage = {
    val team: Team = new Team
    team.setTeamId(teamId)
    //设置队伍状态为0,即未招募
    team.setTeamState(0)
    try {
      teamService.updateTeam(team)
      ResponseMessage("1", "取消招募成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "取消招募失败")
    }
  }
}

