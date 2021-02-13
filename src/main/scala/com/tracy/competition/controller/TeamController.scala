package com.tracy.competition.controller

import java.util
import java.util.{ArrayList, List, UUID}

import com.tracy.competition.domain.entity.{Apply, Team, User}
import com.tracy.competition.service.TeamService
import com.tracy.competition.utils.{RedisUtil, ResponseMessage}
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}

/**
 * @author Tracy
 * @date 2021/2/9 15:39
 */
@Controller
@RequestMapping(Array("/team")) class TeamController {
  @Autowired private val redisUtil: RedisUtil = null
  @Autowired private val teamService: TeamService = null

  /**
   * 创建队伍
   *
   * @param team team
   * @return
   */
  @RequestMapping(value = Array("/addTeam"), method = Array(RequestMethod.POST))
  @ResponseBody def addTeam(@RequestBody team: Team): ResponseMessage = { //生成id
    team.setTeamId(UUID.randomUUID.toString)
    //获得当前用户
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    //设置队长为创建该队伍的当前用户
    team.setCaptain(user)
    //初始人数为1
    team.setTeamHeadcount(1)
    //初始状态为0（未满人，但不处于招募）
    team.setTeamState(0)
    try {
      teamService.insertTeam(team)
      ResponseMessage("1", "创建成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "创建失败")
    }
  }

  /**
   * 获取当前用户创建的所有队伍
   *
   * @return
   */
  @RequestMapping(value = Array("/findMyTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findMyTeam: ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try {
      val teams: util.List[Team] = teamService.findMyTeam(user.getUserId)
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("teams", teams)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }

  /**
   * 获取当前用户加入的所有队伍
   *
   * @return
   */
  @RequestMapping(value = Array("/findJoinTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findJoinTeam: ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try {
      val teams: util.List[Team] = teamService.findJoinTeam(user.getUserId)
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("teams", teams)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }

  /**
   * 根据队伍id获得队伍其他成员信息
   *
   * @param teamId teamId
   * @return
   */
  @RequestMapping(value = Array("/findUsersByTeamId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findUsersByTeamId(teamId: String): ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try {
      val users: util.List[User] = teamService.findUsersByTeamIdAndNotNowUser(teamId, user.getUserId)
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("users", users)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }

  /**
   * 根据队伍信息修改数据库队伍数据
   *
   * @param team team
   * @return
   */
  @RequestMapping(value = Array("/updateMyTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def updateMyTeam(@RequestBody team: Team): ResponseMessage = try {
    teamService.updateMyTeam(team)
    ResponseMessage("1", "修改成功")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "修改失败")
  }

  /**
   * 删除队伍中的成员
   *
   * @param teamId teamId
   * @param userId userId
   */
  @RequestMapping(value = Array("/deleteTeamUser"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def deleteTeamUser(teamId: String, userId: String): ResponseMessage = try {
    teamService.deleteTeamUser(teamId, userId)
    ResponseMessage("1", "删除成功")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "删除失败")
  }

  /**
   * 解散队伍，并清空队员
   *
   * @param teamId teamId
   */
  @RequestMapping(value = Array("/deleteTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def deleteTeam(teamId: String): ResponseMessage = try {
    teamService.deleteTeam(teamId)
    ResponseMessage("1", "删除成功")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "删除失败")
  }

  /**
   * 退出队伍
   *
   * @param teamId teamId
   * @return
   */
  @RequestMapping(value = Array("/exitTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def exitTeam(teamId: String): ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try {
      teamService.deleteTeamOneUser(teamId, user.getUserId)
      ResponseMessage("1", "退出成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "退出失败")
    }
  }

  @RequestMapping(value = Array("/joinMyTeamList"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def joinMyTeamList: ResponseMessage = try {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    val teams: util.List[Team] = teamService.findMyTeam(user.getUserId)
    val applies: util.List[Apply] = teamService.findAllMyTeamApply(teams)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("applies", applies)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  @RequestMapping(value = Array("/myDisposeList"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def myDisposeList: ResponseMessage = try {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    val teams: util.List[Team] = teamService.findMyTeam(user.getUserId)
    val applies: util.List[Apply] = teamService.findAllMyHistoryTeamApply(teams)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("applies", applies)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  @RequestMapping(value = Array("/getMyTeamByCompetitionId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def getMyTeamByCompetitionId(competitionId: String): ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try {
      val teams: util.List[Team] = teamService.findMyTeam(user.getUserId)
      val teams1: util.List[Team] = new util.ArrayList[Team]
      if (teams.size != 0) {
        import scala.collection.JavaConversions._
        for (team <- teams) {
          if (team.getCompetition.getCompetitionId == competitionId) teams1.add(team)
        }
      }
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("teams", teams1)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }

  @RequestMapping(value = Array("/getTeamReportList"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def getTeamReportList(competitionId: String): ResponseMessage = try { //根据竞赛id及已报名的状态（3），获取队伍列表
    val teams: util.List[Team] = teamService.findTeamByCompetitionIdAndRegistered(competitionId)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("teams", teams)
    //将id放入缓存，供导出用
    redisUtil.set("competitionId", competitionId)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 根据队伍id和队长id获取队伍所有成员信息
   *
   * @param teamId teamId
   * @param captainId captainId
   * @return
   */
  @RequestMapping(value = Array("/getTeamMembers"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def getTeamMembers(teamId: String, captainId: String): ResponseMessage = try {
    val users: util.List[User] = teamService.findUserListByTeamIdAndCaptainId(teamId, captainId)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("users", users)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }
}

