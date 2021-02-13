package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.Notification

/**
 * @author Tracy
 * @date 2021/2/9 13:39
 */
trait WinNotificationService {
  /**
   * 获取所有获奖通知
   *
   * @param notificationType notificationType
   * @return
   */
  def findAllWinNotification(notificationType: Integer): util.List[Notification]


  /**
   * 保存新获奖通知
   *
   * @param notification notification
   * @return
   */
  def insertWinNotification(notification: Notification): Unit

  /**
   * 删除获奖通知
   *
   * @param notificationId notificationId
   * @param competitionId competitionId
   * @return
   */
  def deleteWinNotificationById(notificationId: String, competitionId: String): Unit

  /**
   * 修改获奖通知
   *
   * @param notification notification
   * @return
   */
  def updateWinNotification(notification: Notification): Unit
}
