package com.tracy.competition.controller

import java.util
import java.util.UUID

import com.tracy.competition.domain.entity._
import com.tracy.competition.service.{ApplyService, TeamService}
import com.tracy.competition.utils.ResponseMessage
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}

/**
 * @author Tracy
 * @date 2021/2/9 15:08
 */
@RestController
@RequestMapping(Array("/apply"))
class ApplyController {

  @Autowired private val applyService: ApplyService = null
  @Autowired private val teamService: TeamService = null

  /**
   * 个人赛报名
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/doApply"), method = Array(RequestMethod.POST))
  def doApply(competitionId: String): ResponseMessage = {
    //创建中间表对象，封装报名信息
    val userCompetition: UserCompetition = new UserCompetition
    val competition: Competition = new Competition
    competition.setCompetitionId(competitionId)
    userCompetition.setCompetition(competition)
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    userCompetition.setUser(user)
    //获取当前时间毫秒值,并存入封装对象
    userCompetition.setDate(System.currentTimeMillis)
    try {
      applyService.insertApply(userCompetition)
      ResponseMessage("1", "报名成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "报名失败")
    }
  }

  /**
   * 组队赛报名
   *
   * @param teamId teamId
   * @return
   */
  @RequestMapping(value = Array("/doApplyByTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def doApplyByTeam(teamId: String): ResponseMessage = { //创建队伍对象，封装id和报名时间,队伍状态
    val team: Team = new Team
    team.setTeamId(teamId)
    //设置队伍状态为3,即已报名
    team.setTeamState(3)
    team.setApplyTime(System.currentTimeMillis)
    println(team)
    try {
      teamService.updateTeam(team)
      ResponseMessage("1", "报名成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "报名失败")
    }
  }

  /**
   * 个人赛取消报名
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/cancelApply"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def cancelApply(competitionId: String): ResponseMessage = {
    val userCompetition: UserCompetition = new UserCompetition
    val competition: Competition = new Competition
    competition.setCompetitionId(competitionId)
    userCompetition.setCompetition(competition)
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    userCompetition.setUser(user)
    try {
      applyService.deleteApply(userCompetition)
      ResponseMessage("1", "取消报名成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "取消报名失败")
    }
  }

  /**
   * 管理员取消用户报名
   *
   * @param userCompetition userCompetition
   * @return
   */
  @RequestMapping(value = Array("/adminCancelApply"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def adminCancelApply(@RequestBody userCompetition: UserCompetition): ResponseMessage = {
    println(userCompetition)
    try {
      applyService.deleteApply(userCompetition)
      ResponseMessage("1", "取消报名成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "取消报名失败")
    }
  }

  /**
   * 组队赛取消报名
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/cancelTeamApply"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def cancelTeamApply(competitionId: String): ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try {
      val team: Team = teamService.findTeamByCaptainIdAndCompetitionId(user.getUserId, competitionId)
      if (team != null && team.getApplyTime != null) if (team.getCaptain.getUserId == user.getUserId) {
        teamService.updateTeamCancelApply(team.getTeamId)
        ResponseMessage("1", "取消报名成功")
      }
      else { //非队长取消报名
        ResponseMessage("1", "取消报名失败，队员无权取消报名，请联系队长")
      }
      else ResponseMessage("0", "未报名，取消报名失败")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "取消报名失败")
    }
  }

  /**
   * 管理员取消组队赛报名
   *
   * @param teamId teamId
   * @return
   */
  @RequestMapping(value = Array("/adminCancelTeamApply"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def adminCancelTeamApply(teamId: String): ResponseMessage = try {
    val team: Team = new Team
    team.setTeamId(teamId)
    team.setTeamState(2)
    println(team)
    teamService.updateTeam(team)
    ResponseMessage("1", "取消报名成功")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "取消报名失败")
  }

  /**
   * 获得该用户对于该竞赛的的报名状态/已报名/未报名
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/loadApplyState"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def loadApplyState(competitionId: String): ResponseMessage = {
    val userCompetition: UserCompetition = new UserCompetition
    val competition: Competition = new Competition
    competition.setCompetitionId(competitionId)
    userCompetition.setCompetition(competition)
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    userCompetition.setUser(user)
    try {
      val uc: UserCompetition = applyService.findApplyByUserIdAndCompetitionId(userCompetition)
      val responseMessage: ResponseMessage = ResponseMessage("1", "加载成功")
      responseMessage.getData.put("userCompetition", uc)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "加载失败")
    }
  }

  /**
   * 获得该队伍对于该竞赛的的报名状态/已报名/未报名
   *
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/loadTeamApplyState"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def loadTeamApplyState(competitionId: String): ResponseMessage = try {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    val team: Team = teamService.findTeamByCaptainIdAndCompetitionId(user.getUserId, competitionId)
    if (team != null && team.getApplyTime != null) {
      val responseMessage: ResponseMessage = ResponseMessage("2", "有已报名该比赛队伍")
      responseMessage.getData.put("team", team)
      return responseMessage
    }
    ResponseMessage("1", "无已报名该比赛队伍")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "加载失败")
  }

  /**
   * 发起加入队伍申请
   *
   * @param apply apply
   * @return
   */
  @RequestMapping(value = Array("/joinTeam"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def joinTeam(@RequestBody apply: Apply): ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    apply.setUser(user)
    apply.setApplyState(0)
    //判断是否有正在加入该队伍的申请
    if (applyService.findApplyByUserIdAndApplyStateAndTeamId(apply))
      return ResponseMessage("-1", "您有正在申请加入该队伍的申请，请前往“我的比赛·队伍 -> 我的申请”查看")
    apply.setApplyId(UUID.randomUUID.toString)
    apply.setApplyTime(System.currentTimeMillis)
    try {
      applyService.insertTeamApply(apply)
      ResponseMessage("1", "提交申请成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "提交失败")
    }
  }

  /**
   * 通过加入队伍申请
   *
   * @param apply apply
   * @return
   */
  @RequestMapping(value = Array("/pass"), method = Array(RequestMethod.POST))
  def pass(@RequestBody apply: Apply): ResponseMessage = {
    println(apply)
    apply.setApplyDisposeTime(System.currentTimeMillis)
    //设置申请状态为1，即通过
    apply.setApplyState(1)
    //队伍人数加1
    apply.getTeam.setTeamHeadcount(apply.getTeam.getTeamHeadcount + 1)
    //若队伍人数等于竞赛规定人数
    if (apply.getTeam.getTeamHeadcount == apply.getTeam.getCompetition.getCompetitionPeopleSum) { //设置队伍状态为已满员
      apply.getTeam.setTeamState(2)
    }
    else { //设置为队伍状态招募中
      apply.getTeam.setTeamState(1)
    }
    try {
      applyService.updateApplyByDispose(apply)
      ResponseMessage("1", "处理成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "处理失败")
    }
  }

  /**
   * 拒绝加入队伍申请
   *
   * @param applyId applyId
   * @return
   */
  @RequestMapping(value = Array("/refuse"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def refuse(applyId: String): ResponseMessage = {
    val apply: Apply = new Apply
    apply.setApplyId(applyId)
    apply.setApplyDisposeTime(System.currentTimeMillis)
    apply.setApplyState(2)
    try {
      applyService.updateApplyByDispose(apply)
      ResponseMessage("1", "处理成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "处理失败")
    }
  }

  /**
   * 获取用户正在申请的申请列表
   *
   * @return
   */
  @RequestMapping(value = Array("/getMyJoinApplyList"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def getMyJoinApplyList: ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try {
      val applies: util.List[Apply] = applyService.findMyApplyList(user.getUserId, 0)
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("applies", applies)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }

  /**
   * 获取用户已结束的申请列表
   *
   * @return
   */
  @RequestMapping(value = Array("/getHistoryJoinApplyList"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def getHistoryJoinApplyList: ResponseMessage = {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    try { //除状态0以外都是已结束的申请
      val applies: util.List[Apply] = applyService.findMyHistoryApplyList(user.getUserId, 0)
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("applies", applies)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }

  /**
   * 撤销加入队伍申请
   *
   * @param applyId applyId
   * @return
   */
  @RequestMapping(value = Array("/cancelMyTeamApply"), method = Array(RequestMethod.POST, RequestMethod.GET))
  def cancelMyTeamApply(applyId: String): ResponseMessage = try {
    applyService.deleteTeamApply(applyId)
    ResponseMessage("1", "撤销成功")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "撤销失败")
  }
}
