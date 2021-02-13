package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.College

/**
 * @author Tracy
 * @date 2021/2/9 13:32
 */
trait CollegeService {

  /**
   * 根据学院名和用户所在学校查询学院
   *
   * @param collegeName collegeName
   * @param universityId universityId
   * @return
   */
  def findCollegeByNameAndUniversityId(collegeName: String, universityId: String): College

  /**
   * 根据id查询学院
   *
   * @param collegeId collegeId
   * @return
   */
  def findCollegeById(collegeId: String): College

  /**
   * 根据学校id查询学院列表
   *
   * @param universityId universityId
   * @return
   */
  def findCollegeByUniversityId(universityId: String): util.List[College]
}
