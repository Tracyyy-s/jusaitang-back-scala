package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.Notification

/**
 * @author Tracy
 * @date 2021/2/9 13:37
 */
trait SystemNoticeService {

  /**
   * 根据类型获取通知
   *
   * @param notificationType notificationType
   * @return
   */
  def findNoticeByType(notificationType: Integer): util.List[Notification]


  /**
   * 保存新的公告内容
   *
   * @param notification notification
   * @return
   */
  def insertNotice(notification: Notification): Unit

  /**
   * 删除公告
   *
   * @param notificationId notificationId
   * @return
   */
  def deleteNotificationById(notificationId: String): Unit

  /**
   * 更新公告
   *
   * @param notification notification
   * @return
   */
  def updateNotification(notification: Notification): Unit
}
