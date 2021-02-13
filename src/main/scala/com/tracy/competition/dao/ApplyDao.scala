package com.tracy.competition.dao

import com.tracy.competition.domain.entity.Apply
import com.tracy.competition.domain.entity.UserCompetition
import com.tracy.competition.domain.entity.UserTeam
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.util
import java.util.List


/**
 * 处理报名及获奖信息
 *
 * @author Tracy
 * @date 2020/11/14 17:09
 */
@Mapper
trait ApplyDao {
    /**
     * 保存报名信息
     *
     * @param userCompetition userCompetition
     * @return
     */
    def insertApply(userCompetition: UserCompetition): Unit

    /**
     * 根据用户id和竞赛id查询报名信息
     *
     * @param userCompetition userCompetition
     * @return
     */
    def findApplyByUserIdAndCompetitionId(userCompetition: UserCompetition): UserCompetition

    /**
     * 根据用户id和竞赛id删除报名信息
     *
     * @param userCompetition userCompetition
     */
    def deleteApply(userCompetition: UserCompetition): Unit

    /**
     * 根据竞赛id获取获奖列表
     *
     * @param competitionId competitionId
     * @return
     */
    def findWinByCompetitionId(competitionId: String): util.List[UserCompetition]

    /**
     * 保存队伍申请
     *
     * @param apply apply
     * @return
     */
    def insertTeamApply(apply: Apply): Unit

    /**
     * 通过处理加入队伍的结果，修改申请信息
     *
     * @param apply apply
     */
    def updateApplyByDispose(apply: Apply): Unit

    /**
     * 获取用户所有申请中的申请
     *
     * @param userId userId
     * @param applyState applyState
     * @return
     */
    def findMyApplyList(@Param("userId") userId: String, @Param("applyState") applyState: Integer): util.List[Apply]

    /**
     * 获取用户所有已结束的申请，状态参数，正在申请的状态0，除去状态为0的都为已结束申请
     *
     * @param userId userId
     * @param applyState applyState
     * @return
     */
    def findMyHistoryApplyList(@Param("userId") userId: String, @Param("applyState") applyState: Integer): util.List[Apply]

    /**
     * 保存用户-队伍信息
     *
     * @param userTeam userTeam
     */
    def insertUserTeam(userTeam: UserTeam): Unit

    /**
     * 根据用户id,申请状态，队伍id查找记录
     *
     * @param apply apply
     * @return
     */
    def findApplyByUserIdAndApplyStateAndTeamId(apply: Apply): Apply

    /**
     * 根据申请id删除申请
     *
     * @param applyId applyId
     */
    def deleteTeamApply(applyId: String): Unit

    /**
     * 删除队伍所有申请
     *
     * @param teamId teamId
     */
    def deleteApplyByTeamId(teamId: String): Unit
}
