package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.FileDao
import com.tracy.competition.domain.entity.File
import com.tracy.competition.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 14:19
 */
@Service
class FileServiceImpl extends FileService {

  @Autowired private val fileDao: FileDao = null

  /**
   * 插入新文件
   *
   * @param file file
   * @return
   */
  override def insertFile(file: File): Unit = fileDao.insertFile(file)

  /**
   * 根据比赛id查询文件
   *
   * @param competitionId competitionId
   * @return
   */
  override def findFileByCompetitionId(competitionId: String): util.List[File] = {
    fileDao.findFileByCompetitionId(competitionId)
  }

  /**
   * 根据比赛id删除文件
   *
   * @param CompetitionId CompetitionId
   * @return
   */
  override def deleteFileByCompetitionId(CompetitionId: String): Unit = {
    fileDao.deleteFileByCompetitionId(CompetitionId)
  }

  /**
   * 根据文件id搜索文件
   *
   * @param fileId fileId
   * @return
   */
  override def findFileById(fileId: String): File = fileDao.findFileById(fileId)

  /**
   * 根据文件id删除文件
   *
   * @param fileId fileId
   * @return
   */
  override def deleteFileById(fileId: String): Unit = fileDao.deleteFileById(fileId)

  /**
   * 根据公告id查询文件
   *
   * @param notificationId notificationId
   * @return
   */
  override def findFileByNotificationId(notificationId: String): util.List[File] = {
    fileDao.findFileByNotificationId(notificationId)
  }
}
