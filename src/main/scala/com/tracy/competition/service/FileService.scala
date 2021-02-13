package com.tracy.competition.service

import java.util
import java.util.List

import com.tracy.competition.domain.entity.File

/**
 * @author Tracy
 * @date 2021/2/9 13:34
 */
trait FileService {
  /**
   * 插入新文件
   *
   * @param file file
   * @return
   */
  def insertFile(file: File): Unit

  /**
   * 根据比赛id查询文件
   *
   * @param competitionId competitionId
   * @return
   */
  def findFileByCompetitionId(competitionId: String): util.List[File]

  /**
   * 根据比赛id删除文件
   *
   * @param competitionId competitionId
   * @return
   */
  def deleteFileByCompetitionId(competitionId: String): Unit

  /**
   * 根据文件id搜索文件
   *
   * @param fileId fileId
   * @return
   */
  def findFileById(fileId: String): File

  /**
   * 根据文件id删除文件
   *
   * @param fileId fileId
   * @return
   */
  def deleteFileById(fileId: String): Unit


  /**
   * 根据公告id查询文件
   *
   * @param notificationId notificationId
   * @return
   */
  def findFileByNotificationId(notificationId: String): util.List[File]
}
