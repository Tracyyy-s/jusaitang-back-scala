package com.tracy.competition.controller

import java.io.IOException
import java.util
import java.util.{List, UUID}

import com.tracy.competition.domain.entity.{College, Competition, CompetitionType, Notification, User, File}
import com.tracy.competition.service.{CollegeService, CompetitionTypeService, FileService, NotificationService, UserService}
import com.tracy.competition.utils.{CompetitionNotificationVO, RedisUtil, ResponseMessage}
import org.apache.shiro.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}

/**
 * @author Tracy
 * @date 2021/2/9 15:26
 */
@Controller
@RequestMapping(Array("/notification")) class NotificationController {
  @Autowired private val notificationService: NotificationService = null
  @Autowired private val fileService: FileService = null
  @Autowired private val userService: UserService = null
  @Autowired private val collegeService: CollegeService = null
  @Autowired private val competitionTypeService: CompetitionTypeService = null
  @Autowired private val redisUtil: RedisUtil = null

  /**
   * 通知类型查找通知，已指定notificationType为0的为竞赛通知
   *
   * @return
   */
  @RequestMapping(value = Array("/findNotificationByType"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findNotificationByType: ResponseMessage = try { //获取类型为0（竞赛通知）的数据
    val notifications: util.List[Notification] = notificationService.findNotificationByType(0)
    println(notifications)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("notifications", notifications)
    responseMessage
  } catch {
    case e: Exception =>
      ResponseMessage("0", "获取失败")
  }

  /**
   * 加载所有竞赛类型
   *
   * @return
   */
  @RequestMapping(value = Array("/findAllType"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findAllType: ResponseMessage = try {
    val competitionTypes: util.List[CompetitionType] = competitionTypeService.findAllCompetitionType
    println(competitionTypes)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("competitionTypes", competitionTypes)
    responseMessage
  } catch {
    case e: Exception =>
      ResponseMessage("0", "获取失败")
  }

  /**
   * 保存新竞赛，通知
   *
   * @param competitionNotificationVO competitionNotificationVO
   * @return
   */
  @RequestMapping(value = Array("/insertCompetition"), method = Array(RequestMethod.POST))
  @ResponseBody
  @throws[IOException]
  def insertCompetitionNotification(@RequestBody competitionNotificationVO: CompetitionNotificationVO): ResponseMessage = { //封装比赛类
    val competition: Competition = new Competition
    competition.setCompetitionContent(competitionNotificationVO.getCompetitionContent)
    competition.setCompetitionLevel(competitionNotificationVO.getCompetitionLevel)
    competition.setCompetitionSite(competitionNotificationVO.getCompetitionSite)
    competition.setCompetitionName(competitionNotificationVO.getCompetitionName)
    competition.setCompetitionType(competitionNotificationVO.getCompetitionType)
    competition.setCompetitionPeopleSum(competitionNotificationVO.getCompetitionPeopleSum)
    val competitionId: String = UUID.randomUUID.toString
    //将competitionId存入redis，存储文件时使用
    redisUtil.set("competitionId", competitionId)
    //默认为比赛进行中
    competition.setCompetitionState(1)
    competition.setCompetitionId(competitionId)
    if (competitionNotificationVO.getCompetitionTime == null || "" == competitionNotificationVO.getCompetitionTime || "待定" == competitionNotificationVO.getCompetitionTime) competition.setCompetitionTime("待定")
    else competition.setCompetitionTime(competitionNotificationVO.getCompetitionTime)
    //若为院级比赛
    if (competition.getCompetitionLevel == 1) {
      val college: College = new College
      college.setCollegeId(competitionNotificationVO.getCollegeId)
      competition.setCollege(college)
    }
    //封装通知类
    val notification: Notification = new Notification
    notification.setNotificationTitle(competitionNotificationVO.getNotificationTitle)
    notification.setNotificationId(UUID.randomUUID.toString)
    notification.setNotificationTime(System.currentTimeMillis)
    notification.setNotificationState(1)
    notification.setNotificationType(0)
    notification.setCompetition(competition)
    println(notification)
    println(competition)
    try {
      notificationService.insertCompetitionAndNotification(competition, notification)
      val responseMessage: ResponseMessage = ResponseMessage("1", "添加成功")
      responseMessage.getData.put("competitionId", competition.getCompetitionId)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "添加失败")
    }
  }

  /**
   * 根据通知id查找内容，用于回显修改通知的信息
   *
   * @param notificationId notificationId
   * @return
   */
  @RequestMapping(value = Array("/findDataByNotificationId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findDataByNotificationId(notificationId: String): ResponseMessage = try {
    val notification: Notification = notificationService.findDataByNotificationId(notificationId)
    val files: util.List[File] = fileService.findFileByCompetitionId(notification.getCompetition.getCompetitionId)
    notification.getCompetition.setFiles(files)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("notification", notification)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 根据通知id删除该竞赛通知及竞赛文件，比赛
   *
   * @param notificationId notificationId
   * @param competitionId competitionId
   * @return
   */
  @RequestMapping(value = Array("/deleteByNotificationId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def deleteByNotificationId(notificationId: String, competitionId: String): ResponseMessage = try {
    var s: String = null
    val files: util.List[File] = fileService.findFileByCompetitionId(competitionId)
    //如果有文件
    if (files.size > 0) { //保存文件夹路径
      s = files.get(0).getFilePath.substring(0, files.get(0).getFilePath.length - files.get(0).getFileName.length - 1)
      println(s)
      //快速for循环 快捷键iter
      import scala.collection.JavaConverters._
      for (file <- files.asScala) {
        val f: java.io.File = new java.io.File(file.getFilePath)
        //文件是否存在
        if (f.exists) { //存在就删除
          if (f.delete) println("本地文件删除成功")
          else println("本地文件删除失败")
        }
        else println("本地不存在")
      }
      //获得上级文件夹
      val f1: java.io.File = new java.io.File(s)
      //删除文件夹
      f1.delete
    }
    //删除信息
    notificationService.deleteNotificationById(notificationId, competitionId)
    ResponseMessage("1", "删除成功")
  } catch {
    case e: Exception =>
      ResponseMessage("0", "删除失败")
  }

  /**
   * 根据当前用户学校id查询学院列表，用于回显
   *
   * @return
   */
  @RequestMapping(value = Array("/findCollege"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findCollege: ResponseMessage = try { //获得当前用户（含用户名密码）
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    //根据用户名获得用户的学院id
    val collegeId: String = userService.findCollegeIdByUserName(user.getUserName)
    //根据学校id获得学院列表
    val colleges: util.List[College] = collegeService.findCollegeByUniversityId(collegeService.findCollegeById(collegeId).getUniversity.getUniversityId)
    println(colleges)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("colleges", colleges)
    responseMessage
  } catch {
    case e: Exception =>
      ResponseMessage("0", "获取失败")
  }

  /**
   * 修改竞赛通知及竞赛文件，竞赛内容
   *
   * @param competitionNotificationVO competitionNotificationVO
   * @return
   */
  @RequestMapping(value = Array("/updateNotification"), method = Array(RequestMethod.POST))
  @ResponseBody def updateNotification(@RequestBody competitionNotificationVO: CompetitionNotificationVO): ResponseMessage = { //将competitionId存入redis，修改文件时使用
    redisUtil.set("competitionId", competitionNotificationVO.getCompetitionId)
    try {
      notificationService.updateNotification(competitionNotificationVO)
      ResponseMessage("1", "修改成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "修改失败")
    }
  }
}

