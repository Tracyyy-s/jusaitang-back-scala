package com.tracy.competition.controller

import java.io.IOException
import java.util
import java.util.{List, UUID}

import com.tracy.competition.domain.entity.{File, Notification}
import com.tracy.competition.service.{FileService, SystemNoticeService}
import com.tracy.competition.utils.{RedisUtil, ResponseMessage}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}

/**
 * @author Tracy
 * @date 2021/2/9 15:37
 */
@Controller
@RequestMapping(Array("/notice")) class SystemNoticeController {
  @Autowired private val systemNoticeService: SystemNoticeService = null
  @Autowired private val fileService: FileService = null
  @Autowired private val redisUtil: RedisUtil = null

  /**
   * 已指定notificationType为1的为系统通知
   *
   * @return
   */
  @RequestMapping(value = Array("/findAll"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findNotice: ResponseMessage = try {
    val notifications: util.List[Notification] = systemNoticeService.findNoticeByType(1)
    val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
    responseMessage.getData.put("notifications", notifications)
    responseMessage
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "获取失败")
  }

  /**
   * 保存新公告
   *
   * @param notification notification
   * @return
   */
  @RequestMapping(value = Array("/insertNotice"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody
  @throws[IOException]
  def insertNotice(@RequestBody notification: Notification): ResponseMessage = { //生成id
    notification.setNotificationId(UUID.randomUUID.toString)
    //将notificationId存入redis，存储文件时使用
    redisUtil.set("notificationId", notification.getNotificationId)
    //获得当前毫秒值作为公告时间
    notification.setNotificationTime(System.currentTimeMillis)
    //设置类型为1（即系统公告）
    notification.setNotificationType(1)
    println(notification)
    try systemNoticeService.insertNotice(notification)
    catch {
      case e: Exception =>
        e.printStackTrace()
        return ResponseMessage("0", "添加失败")
    }
    ResponseMessage("1", "添加成功")
  }

  /**
   * 删除公告
   *
   * @param notificationId notificationId
   * @return
   */
  @RequestMapping(value = Array("/deleteNotice"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody
  @throws[IOException]
  def deleteNoticeById(notificationId: String): ResponseMessage = try {
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
    systemNoticeService.deleteNotificationById(notificationId)
    ResponseMessage("1", "删除成功")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "删除失败")
  }

  /**
   * 修改公告内容
   *
   * @param notification notification
   * @return
   */
  @RequestMapping(value = Array("/updateNotice"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def updateNotification(@RequestBody notification: Notification): ResponseMessage = {
    println(notification)
    //将competitionId存入redis，修改文件时使用
    redisUtil.set("notificationId", notification.getNotificationId)
    try {
      systemNoticeService.updateNotification(notification)
      ResponseMessage("1", "修改成功")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "修改失败")
    }
  }
}
