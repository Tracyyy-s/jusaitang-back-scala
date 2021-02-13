package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.{CompetitionDao, FileDao, NotificationDao}
import com.tracy.competition.domain.entity.{Competition, Notification}
import com.tracy.competition.service.NotificationService
import com.tracy.competition.utils.CompetitionNotificationVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Isolation, Propagation, Transactional}

/**
 * @author Tracy
 * @date 2021/2/9 14:22
 */
@Service
class NotificationServiceImpl extends NotificationService {
  @Autowired private val notificationDao: NotificationDao = null
  @Autowired private val competitionDao: CompetitionDao = null
  @Autowired private val fileDao: FileDao = null

  /**
   * 通知类型查找通知
   *
   * @param notificationType notificationType
   * @return
   */
  override def findNotificationByType(notificationType: Integer): util.List[Notification] = {
    notificationDao.findNotificationByType(notificationType)
  }

  /**
   * 通知类型和状态查找系统公告
   *
   * @param notificationType  notificationType
   * @param notificationState notificationState
   * @return
   */
  override def findSystemNoticeByTypeAndState(notificationType: Integer, notificationState: Integer): util.List[Notification] = {
    notificationDao.findSystemNoticeByTypeAndState(notificationType, notificationState)
  }

  /**
   * 将新通知，比赛
   *
   * @param notification notification
   * @param competition  competition
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def insertCompetitionAndNotification(competition: Competition, notification: Notification): Unit = {
    competitionDao.insertCompetition(competition)
    notificationDao.insertNotification(notification)
  }

  /**
   * 根据通知id查找内容，用于回显修改通知的信息
   *
   * @param notificationId notificationId
   * @return
   */
  override def findDataByNotificationId(notificationId: String): Notification = {
    notificationDao.findDataByNotificationId(notificationId)
  }

  /**
   * 根据通知id删除对应通知及文件，竞赛
   *
   * @param notificationId notificationId
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def deleteNotificationById(notificationId: String, competitionId: String): Unit = {
    //删通知
    notificationDao.deleteNotificationById(notificationId)
    //删竞赛文件
    fileDao.deleteFileByCompetitionId(competitionId)
    //删竞赛
    competitionDao.deleteCompetitionById(competitionId)
  }

  /**
   * 修改竞赛通知及竞赛文件，竞赛内容
   *
   * @param competitionNotificationVO competitionNotificationVO
   * @return
   */
  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Array(classOf[Exception]))
  override def updateNotification(competitionNotificationVO: CompetitionNotificationVO): Unit = {
    notificationDao.updateNotification(competitionNotificationVO)
    competitionDao.updateCompetition(competitionNotificationVO)
  }
}
