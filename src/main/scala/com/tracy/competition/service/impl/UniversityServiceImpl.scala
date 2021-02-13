package com.tracy.competition.service.impl

import com.tracy.competition.dao.UniversityDao
import com.tracy.competition.domain.entity.University
import com.tracy.competition.service.UniversityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 14:35
 */
@Service
class UniversityServiceImpl extends UniversityService {

  @Autowired private val universityDao: UniversityDao = null
  /**
   * 根据用户的学院id查询学校
   *
   * @param collegeId collegeId
   * @return
   */
  override def findUniversityByCollegeId(collegeId: String): University = {
    universityDao.findUniversityByCollegeId(collegeId)
  }
}
