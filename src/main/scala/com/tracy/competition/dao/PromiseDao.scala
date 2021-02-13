package com.tracy.competition.dao

import java.util
import java.util.Set

import com.tracy.competition.domain.entity.{Promise, Role}
import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/9 17:15
 */
@Mapper trait PromiseDao {
  /**
   * 通过角色获取权限
   *
   * @param roles roles
   * @return
   */
  def getPromiseByRole(roles: util.Set[Role]): util.Set[Promise]
}




