package com.tracy.competition.utils

import java.io.{ByteArrayOutputStream, IOException}
import java.nio.charset.StandardCharsets
import java.util

import com.tracy.competition.domain.entity.{Team, User}
import org.apache.poi.hpsf.{DocumentSummaryInformation, SummaryInformation}
import org.apache.poi.hssf.usermodel.{HSSFCell, HSSFCellStyle, HSSFRow, HSSFSheet, HSSFWorkbook}
import org.apache.poi.ss.usermodel.{FillPatternType, IndexedColors}
import org.springframework.http.{HttpHeaders, HttpStatus, MediaType, ResponseEntity}

/**
 * @author Tracy
 * @date 2021/2/9 12:40
 */
object POIUtils {

  //导出
  def userExcel(list: util.List[User]): ResponseEntity[Array[Byte]] = {
    //1. 创建一个 Excel 文档
    val workbook = new HSSFWorkbook
    //2. 创建文档摘要
    workbook.createInformationProperties()
    //3. 获取并配置文档信息
    val docInfo: DocumentSummaryInformation = workbook.getDocumentSummaryInformation
    //文档类别
    docInfo.setCategory("报名信息")

    //4. 获取文档摘要信息
    val summInfo: SummaryInformation = summaryInfo(workbook.getSummaryInformation)

    //5. 创建样式
    //创建标题行的样式
    val headerStyle: HSSFCellStyle = headStyleSetting(workbook.createCellStyle)

    val sheet: HSSFSheet = sheetParams(workbook.createSheet("报名信息表"))

    //6. 创建标题行
    val r0: HSSFRow = sheet.createRow(0)
    //设置行值
    val c0: HSSFCell = r0.createCell(0)
    c0.setCellValue("序号")
    c0.setCellStyle(headerStyle)
    val c1: HSSFCell = r0.createCell(1)
    c1.setCellStyle(headerStyle)
    c1.setCellValue("姓名")
    val c2: HSSFCell = r0.createCell(2)
    c2.setCellStyle(headerStyle)
    c2.setCellValue("学校")
    val c3: HSSFCell = r0.createCell(3)
    c3.setCellStyle(headerStyle)
    c3.setCellValue("学院")
    val c4: HSSFCell = r0.createCell(4)
    c4.setCellStyle(headerStyle)
    c4.setCellValue("年级")
    val c5: HSSFCell = r0.createCell(5)
    c5.setCellStyle(headerStyle)
    c5.setCellValue("班级")
    val c6: HSSFCell = r0.createCell(6)
    c6.setCellStyle(headerStyle)
    c6.setCellValue("性别")
    val c7: HSSFCell = r0.createCell(7)
    c7.setCellStyle(headerStyle)
    c7.setCellValue("联系电话")
    for (i <- 0 until list.size) {
      val user: User = list.get(i)
      val row: HSSFRow = sheet.createRow(i + 1)
      row.createCell(0).setCellValue(i + 1)
      row.createCell(1).setCellValue(user.getName)
      row.createCell(2).setCellValue(user.getCollege.getUniversity.getUniversityName)
      row.createCell(3).setCellValue(user.getCollege.getCollegeName)
      row.createCell(4).setCellValue(user.getPeriod.toDouble)
      row.createCell(5).setCellValue(user.getUserClassName)
      row.createCell(6).setCellValue(user.getSex)
      row.createCell(7).setCellValue(user.getPhone)
    }

    packageResponseEntity(workbook)
  }

  def teamExcel(teamList: util.List[Team]): ResponseEntity[Array[Byte]] = {
    val workbook = new HSSFWorkbook
    workbook.createInformationProperties()
    val docInfo: DocumentSummaryInformation = workbook.getDocumentSummaryInformation
    docInfo.setCategory("队伍报名信息")
    val summInfo: SummaryInformation = summaryInfo(workbook.getSummaryInformation)

    val headerStyle: HSSFCellStyle = headStyleSetting(workbook.createCellStyle)

    val sheet: HSSFSheet = sheetParams(workbook.createSheet("报名信息表"))

    val r0: HSSFRow = sheet.createRow(0)
    val c0: HSSFCell = r0.createCell(0)
    c0.setCellValue("序号")
    c0.setCellStyle(headerStyle)
    val c1: HSSFCell = r0.createCell(1)
    c1.setCellStyle(headerStyle)
    c1.setCellValue("队伍名")
    val c2: HSSFCell = r0.createCell(2)
    c2.setCellStyle(headerStyle)
    c2.setCellValue("姓名")
    val c3: HSSFCell = r0.createCell(3)
    c3.setCellStyle(headerStyle)
    c3.setCellValue("学校")
    val c4: HSSFCell = r0.createCell(4)
    c4.setCellStyle(headerStyle)
    c4.setCellValue("学院")
    val c5: HSSFCell = r0.createCell(5)
    c5.setCellStyle(headerStyle)
    c5.setCellValue("年级")
    val c6: HSSFCell = r0.createCell(6)
    c6.setCellStyle(headerStyle)
    c6.setCellValue("班级")
    val c7: HSSFCell = r0.createCell(7)
    c7.setCellStyle(headerStyle)
    c7.setCellValue("性别")
    val c8: HSSFCell = r0.createCell(8)
    c8.setCellStyle(headerStyle)
    c8.setCellValue("联系电话")
    //当前行，默认1,  0为表头
    var nowLine = 1
    for (i <- 0 until teamList.size) {
      val team: Team = teamList.get(i)
      System.out.println(team)
      val row: HSSFRow = sheet.createRow(nowLine)
      row.createCell(0).setCellValue(i + 1)
      row.createCell(1).setCellValue(team.getTeamName)
      row.createCell(2).setCellValue(team.getCaptain.getName)
      row.createCell(3).setCellValue(team.getCaptain.getCollege.getUniversity.getUniversityName)
      row.createCell(4).setCellValue(team.getCaptain.getCollege.getCollegeName)
      row.createCell(5).setCellValue(team.getCaptain.getPeriod.toDouble)
      row.createCell(6).setCellValue(team.getCaptain.getUserClassName)
      row.createCell(7).setCellValue(team.getCaptain.getSex)
      row.createCell(8).setCellValue(team.getCaptain.getPhone)
      //行自加
      nowLine += 1
      //队伍成员标题行
      val rr0: HSSFRow = sheet.createRow(nowLine)
      //设置行值,从1开始，代表成员偏移一格
      val cc1: HSSFCell = rr0.createCell(1)
      cc1.setCellValue("成员序号")
      cc1.setCellStyle(headerStyle)
      val cc2: HSSFCell = rr0.createCell(2)
      cc2.setCellStyle(headerStyle)
      cc2.setCellValue("姓名")
      val cc3: HSSFCell = rr0.createCell(3)
      cc3.setCellStyle(headerStyle)
      cc3.setCellValue("学校")
      val cc4: HSSFCell = rr0.createCell(4)
      cc4.setCellStyle(headerStyle)
      cc4.setCellValue("学院")
      val cc5: HSSFCell = rr0.createCell(5)
      cc5.setCellStyle(headerStyle)
      cc5.setCellValue("年级")
      val cc6: HSSFCell = rr0.createCell(6)
      cc6.setCellStyle(headerStyle)
      cc6.setCellValue("班级")
      val cc7: HSSFCell = rr0.createCell(7)
      cc7.setCellStyle(headerStyle)
      cc7.setCellValue("性别")
      val cc8: HSSFCell = rr0.createCell(8)
      cc8.setCellStyle(headerStyle)
      cc8.setCellValue("联系电话")
      nowLine += 1
      for (j <- 1 until team.getUsers.size) {
        val user: User = team.getUsers.get(j)
        val row1: HSSFRow = sheet.createRow(nowLine)
        row1.createCell(1).setCellValue(j)
        row1.createCell(2).setCellValue(user.getName)
        row1.createCell(3).setCellValue(user.getCollege.getUniversity.getUniversityName)
        row1.createCell(4).setCellValue(user.getCollege.getCollegeName)
        row1.createCell(5).setCellValue(user.getPeriod.toDouble)
        row1.createCell(6).setCellValue(user.getUserClassName)
        row1.createCell(7).setCellValue(user.getSex)
        row1.createCell(8).setCellValue(user.getPhone)
        nowLine += 1
      }
    }

    packageResponseEntity(workbook)
  }

  /**
   * 摘要信息初始化
   * @param summInfo  summaryInformation
   * @return
   */
  private def summaryInfo(summInfo: SummaryInformation): SummaryInformation = {
    //文档标题
    summInfo.setTitle("报名信息")
    //文档作者
    summInfo.setAuthor("admin")
    // 文档备注
    summInfo.setComments("本文档由竞赛管理系统导出")
    summInfo
  }

  /**
   * 设置头参数
   * @param headerStyle headerStyle
   * @return
   */
  private def headStyleSetting(headerStyle: HSSFCellStyle): HSSFCellStyle = {
    headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index)
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)
    headerStyle
  }

  /**
   * 设置列参数
   * @param sheet  sheet
   * @return
   */
  private def sheetParams(sheet: HSSFSheet): HSSFSheet = {
    //设置列的宽度
    sheet.setColumnWidth(0, 5 * 256)
    sheet.setColumnWidth(1, 10 * 256)
    sheet.setColumnWidth(2, 20 * 256)
    sheet.setColumnWidth(3, 20 * 256)
    sheet.setColumnWidth(4, 10 * 256)
    sheet.setColumnWidth(5, 20 * 256)
    sheet.setColumnWidth(6, 5 * 256)
    sheet.setColumnWidth(7, 15 * 256)
    sheet
  }

  /**
   * 将workbook封装至http响应体
   *
   * @param workbook workbook
   * @return
   */
  private def packageResponseEntity(workbook: HSSFWorkbook): ResponseEntity[Array[Byte]] = {
    val baos = new ByteArrayOutputStream
    val headers = new HttpHeaders
    try {
      headers.setContentDispositionFormData("attachment", new String("报名表.xls".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1))
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM)
      workbook.write(baos)
    } catch {
      case e: IOException =>
        e.printStackTrace()
    }
    new ResponseEntity[Array[Byte]](baos.toByteArray, headers, HttpStatus.CREATED)
  }
}
