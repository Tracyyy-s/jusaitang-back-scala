package com.tracy.competition.dao

import java.util

import com.tracy.competition.domain.entity.{Competition, User, UserCompetition}
import com.tracy.competition.utils.CompetitionNotificationVO
import org.apache.ibatis.annotations.Mapper


/**
 * @author Tracy
 * @date 2020/11/4 17:09
 */
@Mapper
trait CompetitionDao {
    /**
     * 通过竞赛id查询比赛详情
     *
     * @param competitionId competitionId
     * @return
     */
    def findCompetitionById(competitionId: String): Competition

    /**
     * 通过竞赛id删除比赛
     *
     * @param competitionId competitionId
     * @return
     */
    def deleteCompetitionById(competitionId: String): Unit

    /**
     * 插入新比赛
     *
     * @param competition competition
     * @return
     */
    def insertCompetition(competition: Competition): Unit

    /**
     * 修改竞赛通知及竞赛文件，竞赛内容
     *
     * @param competitionNotificationVO competitionNotificationVO
     * @return
     */
    def updateCompetition(competitionNotificationVO: CompetitionNotificationVO): Unit

    /**
     * 查询所有比赛详情
     *
     * @return
     */
    def findAllCompetition: util.List[Competition]

    /**
     * 根据比赛id获得参赛列表
     *
     * @param competitionId competitionId
     * @return
     */
    def findUserByCompetitionId(competitionId: String): util.List[User]

    /**
     * 根据当前用户id获得已参加列表
     *
     * @param userId userId
     * @return
     */
    def findCompetitionListByUserId(userId: String): util.List[UserCompetition]

    /**
     * 查询所有组队比赛列表
     *
     * @return
     */
    def findAllTeamCompetition: util.List[Competition]

    /**
     * 通过竞赛id,查询报名该竞赛的用户列表
     *
     * @param competitionId competitionId
     * @return
     */
    def findUserList(competitionId: String): util.List[User]

    /**
     * 获取组队赛已报名竞赛列表
     *
     * @param userId userId
     * @return
     */
    def findTeamCompetitionListByUserId(userId: String): util.List[UserCompetition]
}
