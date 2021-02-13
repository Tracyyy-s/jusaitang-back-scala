package com.tracy.competition.dao

import java.util

import com.tracy.competition.domain.entity.CompetitionType
import org.apache.ibatis.annotations.Mapper


/**
 * @author Tracy
 * @date 2020/11/14 17:09
 */
@Mapper
trait CompetitionTypeDao {
    /**
     * 获取全部比赛类型
     *
     * @return
     */
    def findAllCompetitionType: util.List[CompetitionType]
}
