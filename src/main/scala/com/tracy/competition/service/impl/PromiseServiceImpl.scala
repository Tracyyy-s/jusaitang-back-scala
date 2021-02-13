package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.PromiseDao
import com.tracy.competition.domain.entity.{Promise, Role}
import com.tracy.competition.service.PromiseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 13:19
 */
@Service
class PromiseServiceImpl extends PromiseService {

  @Autowired private val promiseDao: PromiseDao = null

  /**
   * 通过角色获取权限
   *
   * @param roles roles
   * @return
   */
  override def getPromiseByRole(roles: util.Set[Role]): util.Set[Promise] = promiseDao.getPromiseByRole(roles)
}
