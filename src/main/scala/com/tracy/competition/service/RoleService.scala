package com.tracy.competition.service;

import java.util

import com.tracy.competition.domain.entity.Role;


/**
 * @author Tracy
 * @date 2020/11/4 17:09
 */
trait RoleService {

    /**
     * 通过adminid获取角色
     *
     * @param userId userId
     * @return
     */
    def getRoleByUserId(userId: String): util.Set[Role]

}
