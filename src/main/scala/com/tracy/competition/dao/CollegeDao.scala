package com.tracy.competition.dao

import java.util

import com.tracy.competition.domain.entity.College
import org.apache.ibatis.annotations.Mapper


/**
 * @author Tracy
 * @date 2020/11/14 17:09
 */
@Mapper
trait CollegeDao {
    /**
     * 根据学院名和用户所在学校查询学院
     *
     * @param collegeName collegeName
     * @param universityId  universityId
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
