package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.{Competition, Notification}
import com.tracy.competition.utils.CompetitionNotificationVO

/**
 * @author Tracy
 * @date 2021/2/9 13:35
 */
trait NotificationService {

  /**
   * 通知类型查找通知
   *
   * @param notificationType notificationType
   * @return
   */
  def findNotificationByType(notificationType: Integer): util.List[Notification]

  /**
   * 通知类型和状态查找系统公告
   *
   * @param notificationType notificationType
   * @param notificationState notificationState
   * @return
   */
  def findSystemNoticeByTypeAndState(notificationType: Integer, notificationState: Integer): util.List[Notification]

  /**
   * 将新通知，比赛
   *
   * @param notification notification
   * @param competition competition
   * @return
   */
  def insertCompetitionAndNotification(competition: Competition, notification: Notification): Unit


  /**
   * 根据通知id查找内容，用于回显修改通知的信息
   *
   * @param notificationId notificationId
   * @return
   */
  def findDataByNotificationId(notificationId: String): Notification

  /**
   * 根据通知id删除对应通知及文件，竞赛
   *
   * @param notificationId notificationId
   * @return
   */
  def deleteNotificationById(notificationId: String, competitionId: String): Unit


  /**
   * 修改竞赛通知及竞赛文件，竞赛内容
   *
   * @param competitionNotificationVO competitionNotificationVO
   * @return
   */
  def updateNotification(competitionNotificationVO: CompetitionNotificationVO): Unit
}
