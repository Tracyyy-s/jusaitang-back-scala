package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.CollegeDao
import com.tracy.competition.domain.entity.College
import com.tracy.competition.service.CollegeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 14:13
 */
@Service
class CollegeServiceImpl extends CollegeService {

  @Autowired private val collegeDao: CollegeDao = null

  /**
   * 根据学院名和用户所在学校查询学院
   *
   * @param collegeName  collegeName
   * @param universityId universityId
   * @return
   */
  override def findCollegeByNameAndUniversityId(collegeName: String, universityId: String): College = {
    collegeDao.findCollegeByNameAndUniversityId(collegeName, universityId)
  }

  /**
   * 根据id查询学院
   *
   * @param collegeId collegeId
   * @return
   */
  override def findCollegeById(collegeId: String): College = collegeDao.findCollegeById(collegeId)

  /**
   * 根据学校id查询学院列表
   *
   * @param universityId universityId
   * @return
   */
  override def findCollegeByUniversityId(universityId: String): util.List[College] = {
    collegeDao.findCollegeByUniversityId(universityId)
  }
}
