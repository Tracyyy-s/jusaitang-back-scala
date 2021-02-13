package com.tracy.competition.service

import java.util

import com.tracy.competition.domain.entity.{Promise, Role}

/**
 * @author Tracy
 * @date 2021/2/9 13:18
 */
trait PromiseService {

  /**
   * 通过角色获取权限
   *
   * @param roles roles
   * @return
   */
  def getPromiseByRole(roles: util.Set[Role]): util.Set[Promise]
}
