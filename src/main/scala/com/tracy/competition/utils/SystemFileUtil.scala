package com.tracy.competition.utils

import java.io.{File, IOException}


/**
 * @author Tracy
 * @date 2021/2/9 13:00
 */
object SystemFileUtil {

  /**
   * 判断是否文件名是否已经存在
   *
   * @param fileName 文件名
   * @param files    文件
   * @return boolean
   */
  def isExists(fileName: String, files: java.util.Collection[com.tracy.competition.domain.entity.File]): Boolean = { //判断是否已经存在该文件名
    import scala.collection.JavaConverters._
    for (file <- files.asScala) {
      if (fileName == file.getFileName) return true
    }
    false
  }

  /**
   * 创建文件
   *
   * @param path     父路径
   * @param fileName 文件名
   * @return file
   * @throws IOException exception
   */
  @throws[IOException]
  def createFile(path: String, fileName: String): File = { //封装文件对象
    val file = new File(path, fileName)
    //判断文件夹是否存在，如果不存在则创建
    if (!file.exists) {
      if (!file.getParentFile.exists) file.getParentFile.mkdirs
      file.createNewFile
    }
    file
  }
}
