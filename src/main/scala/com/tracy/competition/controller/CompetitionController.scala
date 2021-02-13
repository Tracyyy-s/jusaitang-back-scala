package com.tracy.competition.controller

import java.util
import java.util.List

import com.tracy.competition.domain.entity.{Competition, Team, User, UserCompetition}
import com.tracy.competition.service.{CompetitionService, TeamService}
import com.tracy.competition.utils.{POIUtils, RedisUtil, ResponseMessage}
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RequestMethod, ResponseBody}
import scala.collection.JavaConverters._
/**
 * @author Tracy
 * @date 2021/2/9 15:14
 */
@Controller
@RequestMapping(Array("/competition")) class CompetitionController {
  @Autowired private val teamService: TeamService = null
  @Autowired private val redisUtil: RedisUtil = null
  @Autowired private val competitionService: CompetitionService = null

  /**
   * 根据比赛id获得比赛
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/findCompetitionById"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findCompetitionById(competitionId: String): ResponseMessage = try {
    val competition: Competition = competitionService.findCompetitionById(competitionId)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("competition", competition)
    println(competition)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 获得该竞赛已报名的用户列表
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/findUserByCompetitionId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findUserByCompetitionId(competitionId: String): ResponseMessage = try {
    val users: util.List[User] = competitionService.findUserByCompetitionId(competitionId)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("users", users)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 获得所有比赛
   *
   * @return
   */
  @RequestMapping(value = Array("/findAllCompetition"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findAllCompetition: ResponseMessage = try {
    val competitions: util.List[Competition] = competitionService.findAllCompetition
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("competitions", competitions)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 根据当前用户id获得已参加的竞赛列表
   *
   * @return
   */
  @RequestMapping(value = Array("/findCompetitionListByUserId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findCompetitionListByUserId: ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    println(user)
    try {
      val userCompetitions: util.List[UserCompetition] = competitionService.findCompetitionListByUserId(user.getUserId)
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("userCompetitions", userCompetitions)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }

  /**
   * 查询所有组队比赛
   *
   * @return
   */
  @RequestMapping(value = Array("/findAllTeamCompetition"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findAllTeamCompetition: ResponseMessage = try {
    val competitions: util.List[Competition] = competitionService.findAllTeamCompetition
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("competitions", competitions)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 通过竞赛id,查询报名该竞赛的用户列表
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/getUserReportList"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def getUserReportList(competitionId: String): ResponseMessage = try {
    val users: util.List[User] = competitionService.findUserList(competitionId)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("users", users)
    redisUtil.set("competitionId", competitionId)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  @GetMapping(Array("/export")) def exportData: ResponseEntity[Array[Byte]] = {
    val list: util.List[User] = competitionService.findUserList(redisUtil.get("competitionId").toString)
    POIUtils.userExcel(list)
  }

  @GetMapping(Array("/exportTeam")) def exportTeamData: ResponseEntity[Array[Byte]] = { //根据竞赛id及已报名的状态（3），获取队伍列表
    val teamList: util.List[Team] = teamService.findTeamByCompetitionIdAndRegistered(redisUtil.get("competitionId").toString)
    for (team <- teamList.asScala) { //获取队伍成员放入队伍
      team.setUsers(teamService.findUserListByTeamIdAndCaptainId(team.getTeamId, team.getCaptain.getUserId))
    }
    POIUtils.teamExcel(teamList)
  }
}