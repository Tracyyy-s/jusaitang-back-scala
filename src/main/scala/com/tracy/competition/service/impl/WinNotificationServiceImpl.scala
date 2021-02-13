package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.{FileDao, NotificationDao, WinNotificationServiceDao}
import com.tracy.competition.domain.entity.Notification
import com.tracy.competition.service.WinNotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Isolation, Propagation, Transactional}

import scala.collection.JavaConverters._

/**
 * @author Tracy
 * @date 2021/2/9 14:36
 */
@Service
class WinNotificationServiceImpl extends WinNotificationService {

  @Autowired private val winNotificationServiceDao: WinNotificationServiceDao = null

  @Autowired private val notificationDao: NotificationDao = null

  @Autowired private val fileDao: FileDao = null

  /**
   * 获取所有获奖通知
   *
   * @param notificationType notificationType
   * @return
   */
  override def findAllWinNotification(notificationType: Integer): util.List[Notification] = {
    winNotificationServiceDao.findAllWinNotification(notificationType)
  }

  /**
   * 保存新获奖通知
   *
   * @param notification notification
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def insertWinNotification(notification: Notification): Unit = {
    winNotificationServiceDao.insertWinNotification(notification)
    if (notification.getUserIds != null && notification.getUserIds.size != 0) {
      var ranking = 1
      val competitionId: String = notification.getCompetition.getCompetitionId

      for (userId <- notification.getUserIds.asScala) {
        winNotificationServiceDao.updateWinRanking(userId, competitionId, ranking)
        ranking += 1
      }
    }
  }

  /**
   * 删除获奖通知
   *
   * @param notificationId notificationId
   * @param competitionId  competitionId
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def deleteWinNotificationById(notificationId: String, competitionId: String): Unit = {
    //先删除文件
    fileDao.deleteFileByNnotificationId(notificationId)
    notificationDao.deleteNotificationById(notificationId)
    winNotificationServiceDao.deleteWin(competitionId)
  }

  /**
   * 修改获奖通知
   *
   * @param notification notification
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def updateWinNotification(notification: Notification): Unit = {
    winNotificationServiceDao.updateWinNotification(notification)
    //如果数组中有元素，清空原有获奖记录信息，重新存储
    if (notification.getUserIds != null && notification.getUserIds.size != 0) { //清空
      winNotificationServiceDao.deleteWin(notification.getCompetition.getCompetitionId)
      var ranking = 1
      val competitionId: String = notification.getCompetition.getCompetitionId
      for (userId <- notification.getUserIds.asScala) {
        winNotificationServiceDao.updateWinRanking(userId, competitionId, ranking)
        ranking += 1
      }
    }
  }
}
