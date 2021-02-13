package com.tracy.competition.dao

import com.tracy.competition.domain.entity.University
import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/9 17:18
 */
@Mapper trait UniversityDao {
  /**
   * 根据用户的学院id查询学校
   *
   * @param collegeId collegeId
   * @return
   */
  def findUniversityByCollegeId(collegeId: String): University
}

