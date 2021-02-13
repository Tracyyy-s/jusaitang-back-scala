package com.tracy.competition.dao

import java.util
import java.util.Set

import com.tracy.competition.domain.entity.Role
import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/9 17:16
 */
@Mapper
trait RoleDao {
  /**
   * 通过userId获取user的角色
   *
   * @param userId userId
   * @return
   */
  def getRoleByUserId(userId: String): util.Set[Role]
}

