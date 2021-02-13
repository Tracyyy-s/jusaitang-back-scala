package com.tracy.competition.service.impl

import java.util

import com.tracy.competition.dao.RoleDao
import com.tracy.competition.domain.entity.Role
import com.tracy.competition.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Tracy
 * @date 2021/2/9 13:26
 */
@Service
class RoleServiceImpl extends RoleService {

  @Autowired private val roleDao: RoleDao = null

  /**
   * 通过adminid获取角色
   *
   * @param userId userId
   * @return
   */
  override def getRoleByUserId(userId: String): util.Set[Role] = roleDao.getRoleByUserId(userId)
}
