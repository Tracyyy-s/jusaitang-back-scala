package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.{FileDao, NotificationDao, SystemNoticeDao}
import com.tracy.competition.domain.entity.Notification
import com.tracy.competition.service.SystemNoticeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Isolation, Propagation, Transactional}

/**
 * @author Tracy
 * @date 2021/2/9 14:27
 */
@Service
class SystemNoticeServiceImpl extends SystemNoticeService {

  @Autowired private val systemNoticeDao: SystemNoticeDao = null

  @Autowired private val notificationDao: NotificationDao = null

  @Autowired private val fileDao: FileDao = null

  /**
   * 根据类型获取通知
   *
   * @param notificationType notificationType
   * @return
   */
  override def findNoticeByType(notificationType: Integer): util.List[Notification] = {
    systemNoticeDao.findNoticeByType(notificationType)
  }

  /**
   * 保存新的公告内容
   *
   * @param notification notification
   * @return
   */
  override def insertNotice(notification: Notification): Unit = {
    systemNoticeDao.insertNotice(notification)
  }

  /**
   * 删除公告
   *
   * @param notificationId notificationId
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def deleteNotificationById(notificationId: String): Unit = {
    //先删除文件，文件引用公告外键
    fileDao.deleteFileByNnotificationId(notificationId)
    notificationDao.deleteNotificationById(notificationId)
  }

  /**
   * 更新公告
   *
   * @param notification notification
   * @return
   */
  override def updateNotification(notification: Notification): Unit = {
    systemNoticeDao.updateNotification(notification)
  }
}
