package com.tracy.competition.dao

import java.util
import java.util.List

import com.tracy.competition.domain.entity.Notification
import org.apache.ibatis.annotations.{Mapper, Param}

/**
 * @author Tracy
 * @date 2021/2/9 17:19
 */
@Mapper
trait WinNotificationServiceDao {
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
   * 保存手动录入的获奖信息
   *
   * @param userId userId
   * @param ranking ranking
   * @param competitionId competitionId
   * @return
   */
  def updateWinRanking(@Param("userId") userId: String, @Param("competitionId") competitionId: String, @Param("ranking") ranking: Integer): Unit

  /**
   * 清除获奖信息
   *
   * @param competitionId competitionId
   * @return
   */
  def deleteWin(competitionId: String): Unit

  /**
   * 更新获奖通知内容
   *
   * @param notification notification
   * @return
   */
  def updateWinNotification(notification: Notification): Unit
}

