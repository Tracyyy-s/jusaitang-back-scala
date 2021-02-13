package com.tracy.competition.controller

import java.io.{BufferedInputStream, File, FileInputStream, IOException, OutputStream}
import java.util
import java.util.{List, UUID}

import com.tracy.competition.domain.entity.{Competition, Notification}
import com.tracy.competition.service.FileService
import com.tracy.competition.utils.{RedisUtil, ResponseMessage, SystemFileUtil}
import javax.servlet.http.HttpServletResponse
import org.apache.commons.lang3.{ArrayUtils, StringUtils}
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RequestParam, ResponseBody}
import org.springframework.web.multipart.MultipartFile

/**
 * @author Tracy
 * @date 2021/2/9 15:17
 */
object FileController {
  /**
   * 竞赛类别文件
   */
  private val COMPETITION = "competition"
  /**
   * 通告类别文件
   */
  private val NOTIFICATION = "notification"
}

@Controller
@PropertySource(Array("classpath:file.properties"))
@RequestMapping(Array("/file"))
class FileController {
  @Autowired private val fileService: FileService = null
  @Autowired private val redisUtil: RedisUtil = null

  //抽取上传路径
  @Value("${filePath}") private val filePath: String = null

  /**
   * 校验上传竞赛通知的文件，进行保存或修改
   */
  @RequestMapping(value = Array("/uploadFile"), method = Array(RequestMethod.POST))
  @ResponseBody
  @throws[IOException]
  def uploadCompetitionFile(@RequestParam("multipartFiles") multipartFiles: Array[MultipartFile]): ResponseMessage = { //获得竞赛通知表单的对应竞赛id
    val competitionId: String = redisUtil.get("competitionId").toString
    //设置文件保存路径
    val path: String = filePath + competitionId + "\\"
    val uploadMsg: ResponseMessage = doUploadFile(multipartFiles, path, FileController.COMPETITION, competitionId)
    if (uploadMsg != null) {
      return uploadMsg
    }
    redisUtil.del("competitionId")
    return ResponseMessage("1", "操作成功")
  }

  /**
   * 校验上传公告和获奖通知的文件，进行保存或修改
   */
  @RequestMapping(value = Array("/uploadNoticeFile"), method = Array(RequestMethod.POST))
  @ResponseBody
  @throws[IOException]
  def uploadNoticeFile(@RequestParam("multipartFiles") multipartFiles: Array[MultipartFile]): ResponseMessage = { //获得系统公告表单的对应公告id
    val notificationId: String = redisUtil.get("notificationId").toString
    val path: String = filePath + notificationId + "\\"
    println(notificationId)
    println(path)
    val uploadMsg: ResponseMessage = doUploadFile(multipartFiles, path, FileController.NOTIFICATION, notificationId)
    if (uploadMsg != null) {
      return uploadMsg
    }
    redisUtil.del("notificationId")
    return ResponseMessage("1", "操作成功")
  }

  /**
   * 删除文件
   */
  @RequestMapping(value = Array("/deleteFile"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def deleteFile(fileId: String): ResponseMessage = {
    try {
      val file: com.tracy.competition.domain.entity.File = fileService.findFileById(fileId)
      val f: java.io.File = new File(file.getFilePath)
      //文件是否存在
      if (f.exists) { //存在就删除
        if (f.delete) {
          println("本地文件删除成功")
        }
        else {
          println("本地文件删除失败")
        }
      }
      else {
        println("本地不存在")
      }
      fileService.deleteFileById(fileId)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        return ResponseMessage("0", "删除失败")
    }
    return ResponseMessage("1", "删除成功")
  }

  /**
   * 文件下载
   *
   * @param file     file
   * @param response response
   * @return ResponseMessage
   */
  @RequestMapping(value = Array("/downloadFile"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def downloadFile(@RequestBody file: com.tracy.competition.domain.entity.File, response: HttpServletResponse): ResponseMessage = { //获取文件的信息
    try {
      val dbFile: com.tracy.competition.domain.entity.File = fileService.findFileById(file.getFileId)
      //拿到文件路径
      val filePath: String = dbFile.getFilePath
      val f: java.io.File = new File(filePath)
      if (f.exists) {
        val buffer: Array[Byte] = new Array[Byte](1024)
        var fis: FileInputStream = null
        var bis: BufferedInputStream = null
        try {
          fis = new FileInputStream(f)
          bis = new BufferedInputStream(fis)
          //从响应中拿到输出流对象
          val os: OutputStream = response.getOutputStream
          var i: Int = bis.read(buffer)
          while ( {
            i != -(1)
          }) {
            os.write(buffer, 0, i)
            i = bis.read(buffer)
          }
          os.close()
          return ResponseMessage("0", "下载文件成功")
        } catch {
          case e: Exception =>
            e.printStackTrace()
            return ResponseMessage("-1", "下载文件失败")
        } finally {
          if (bis != null) {
            try bis.close()
            catch {
              case e: IOException =>
                e.printStackTrace()
            }
          }
          if (fis != null) {
            try fis.close()
            catch {
              case e: IOException =>
                e.printStackTrace()
            }
          }
        }
      }
      return ResponseMessage("-1", "下载文件不存在")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        return ResponseMessage("-1", "下载文件失败")
    }
  }

  /**
   * 上传文件
   *
   * @param multipartFiles 用户端传来文件
   * @param path           文件保存的父路径
   * @param fileType       文件类型，包括竞赛类型、通知类型
   * @param idForFileType  id
   * @return responseMessage
   * @throws IOException exception
   */
  @throws[IOException]
  private def doUploadFile(multipartFiles: Array[MultipartFile], path: String, fileType: String, idForFileType: String): ResponseMessage = {

    //导入Break控制包
    import scala.util.control.Breaks._

    var files: util.List[com.tracy.competition.domain.entity.File] = null
    if (ArrayUtils.isNotEmpty(multipartFiles)) {
      for (multipartFile <- multipartFiles) {

        breakable({
          val fileName: String = multipartFile.getOriginalFilename
          if (StringUtils.isEmpty(fileName)) {
            return ResponseMessage("0", "无效的文件名")
          }
          //如果是比赛类型文件
          if (fileType == FileController.COMPETITION) {
            files = fileService.findFileByCompetitionId(idForFileType)
          }
          //如果是通知类型文件
          if (fileType == FileController.NOTIFICATION) {
            files = fileService.findFileByNotificationId(idForFileType)
          }
          if (files != null && SystemFileUtil.isExists(fileName, files)) {
            //todo: continue is not supported
            break() // == continue

          }
          val file: java.io.File = SystemFileUtil.createFile(path, fileName)
          //获得文件对象
          val f: com.tracy.competition.domain.entity.File = packageFileEntity(path, fileName, fileType, idForFileType)
          try { // 文件写入
            multipartFile.transferTo(file)
            //文件保存至数据库
            fileService.insertFile(f)
          } catch {
            case e: Exception =>
              e.printStackTrace()
              return ResponseMessage("0", "操作失败")
          }
        })
      }
    }
    null
  }

  /**
   * 封装自定义File对象
   *
   * @param parentPath    文件夹路径
   * @param fileName      文件名
   * @param fileType      文件类型
   * @param idForFileType 类型对应的id
   * @return file对象
   */
  private def packageFileEntity(parentPath: String, fileName: String, fileType: String, idForFileType: String): com.tracy.competition.domain.entity.File = {
    val f: com.tracy.competition.domain.entity.File = new com.tracy.competition.domain.entity.File
    f.setFileId(UUID.randomUUID.toString)
    f.setFileName(fileName)
    f.setFilePath(parentPath + fileName)
    if (fileType == FileController.COMPETITION) {
      val competition: Competition = new Competition
      competition.setCompetitionId(idForFileType)
      f.setCompetition(competition)
    }
    if (fileType == FileController.NOTIFICATION) {
      val notification: Notification = new Notification
      notification.setNotificationId(idForFileType)
      f.setNotification(notification)
    }
    f
  }
}

