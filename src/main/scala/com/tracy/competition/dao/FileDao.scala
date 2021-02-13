package com.tracy.competition.dao

import java.util

import com.tracy.competition.domain.entity.File
import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/9 17:13
 */
@Mapper
trait FileDao {
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
   * @param CompetitionId CompetitionId
   * @return
   */
  def deleteFileByCompetitionId(CompetitionId: String): Unit

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

  /**
   * 根据公告id删除文件
   *
   * @param notificationId notificationId
   * @return
   */
  def deleteFileByNnotificationId(notificationId: String): Unit
}
