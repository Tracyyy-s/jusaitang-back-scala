package com.tracy.competition.service

import com.tracy.competition.domain.entity.University

/**
 * @author Tracy
 * @date 2021/2/9 13:39
 */
trait UniversityService {
  /**
   * 根据用户的学院id查询学校
   *
   * @param collegeId collegeId
   * @return
   */
  def findUniversityByCollegeId(collegeId: String): University
}
