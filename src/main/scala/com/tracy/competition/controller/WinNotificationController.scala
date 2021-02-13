package com.tracy.competition.controller

import java.io.IOException
import java.util
import java.util.{List, UUID}

import com.tracy.competition.domain.entity.{File, Notification, UserCompetition}
import com.tracy.competition.service.{ApplyService, FileService, WinNotificationService}
import com.tracy.competition.utils.{RedisUtil, ResponseMessage}
import org.apache.commons.collections4.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}

/**
 * @author Tracy
 * @date 2021/2/9 15:41
 */
@Controller
@RequestMapping(Array("/win")) class WinNotificationController {
  @Autowired private val winNotificationService: WinNotificationService = null
  @Autowired private val applyService: ApplyService = null
  @Autowired private val redisUtil: RedisUtil = null
  @Autowired private val fileService: FileService = null

  /**
   * 获取所有获奖通知
   *
   * @return
   */
  @RequestMapping(value = Array("/findAllWinNotification"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findAllWinNotification: ResponseMessage = try {
    val notifications: util.List[Notification] = winNotificationService.findAllWinNotification(2)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("notifications", notifications)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  @RequestMapping(value = Array("/findWinByCompetitionId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findWinByCompetitionId(competitionId: String): ResponseMessage = try {
    val userCompetitions: util.List[UserCompetition] = applyService.findWinByCompetitionId(competitionId)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("userCompetitions", userCompetitions)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 保存新公告
   *
   * @param notification
   * @return
   */
  @RequestMapping(value = Array("/insertWinNotification"), method = Array(RequestMethod.POST))
  @ResponseBody
  @throws[IOException]
  def insertWinNotification(@RequestBody notification: Notification): ResponseMessage = { //生成id
    notification.setNotificationId(UUID.randomUUID.toString)
    println("---------------------------------" + notification.getUserIds)
    //判断是否为上传文件的获奖通知
    if (CollectionUtils.isEmpty(notification.getUserIds)) { //将notificationId存入redis，存储文件时使用
      redisUtil.set("notificationId", notification.getNotificationId)
    }
    //获得当前毫秒值作为通知时间
    notification.setNotificationTime(System.currentTimeMillis)
    //设置类型为2（即获奖通知）
    notification.setNotificationType(2)
    try winNotificationService.insertWinNotification(notification)
    catch {
      case e: Exception =>
        e.printStackTrace()
        return ResponseMessage("0", "添加失败")
    }
    ResponseMessage("1", "添加成功")
  }

  /**
   * 删除获奖通知
   *
   * @param notificationId
   * @return
   */
  @RequestMapping(value = Array("/deleteWinNotification"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody
  @throws[IOException]
  def deleteWinNotificationById(notificationId: String, competitionId: String): ResponseMessage = try {
    var s: String = null
    val files: util.List[File] = fileService.findFileByNotificationId(notificationId)
    //如果有文件
    if (files.size > 0) { //截取文件保存所在的文件夹路径
      s = files.get(0).getFilePath.substring(0, files.get(0).getFilePath.length - files.get(0).getFileName.length - 1)
      println(s)
      //快速for循环 快捷键iter
      import scala.collection.JavaConverters._
      for (file <- files.asScala) {
        println(file)
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
      //删除信息
    }
    winNotificationService.deleteWinNotificationById(notificationId, competitionId)
    ResponseMessage("1", "删除成功")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "删除失败")
  }

  /**
   * 获取回显表单数据
   *
   * @param competitionId
   * @param notificationId
   * @return
   */
  @RequestMapping(value = Array("/findWinForm"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findWinForm(notificationId: String, competitionId: String): ResponseMessage = try {
    val userCompetitions: util.List[UserCompetition] = applyService.findWinByCompetitionId(competitionId)
    val files: util.List[File] = fileService.findFileByNotificationId(notificationId)
    if (userCompetitions != null && userCompetitions.size != 0) {
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功,为获奖名单")
      responseMessage.getData.put("userCompetitions", userCompetitions)
      return responseMessage
    }
    else if (files != null && files.size != 0) {
      val responseMessage: ResponseMessage = ResponseMessage("2", "获取成功,为获奖文件")
      responseMessage.getData.put("files", files)
      return responseMessage
    }
    ResponseMessage("0", "获取失败")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 更新公告
   *
   * @param notification notification
   * @return
   */
  @RequestMapping(value = Array("/updateWinNotification"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody
  @throws[IOException]
  def updateWinNotification(@RequestBody notification: Notification): ResponseMessage = {
    if (notification.getUserIds == null || notification.getUserIds.size == 0) redisUtil.set("notificationId", notification.getNotificationId)
    println(notification)
    try winNotificationService.updateWinNotification(notification)
    catch {
      case e: Exception =>
        e.printStackTrace()
        return ResponseMessage("0", "修改失败")
    }
    ResponseMessage("1", "修改成功")
  }
}

