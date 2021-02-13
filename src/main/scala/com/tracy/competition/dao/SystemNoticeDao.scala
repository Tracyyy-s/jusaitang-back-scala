package com.tracy.competition.dao

import java.util
import java.util.List

import com.tracy.competition.domain.entity.Notification
import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/9 17:16
 */
@Mapper
trait SystemNoticeDao {
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
   * 更新公告
   *
   * @param notification notification
   * @return
   */
  def updateNotification(notification: Notification): Unit
}

