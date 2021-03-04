package com.tracy.competition.realm

import java.util
import java.util.{HashSet, Set}

import com.tracy.competition.domain.constant.EncoderConstant
import com.tracy.competition.domain.entity.{Promise, Role, User}
import com.tracy.competition.service.{PromiseService, RoleService, UserService}
import org.apache.shiro.authc.{AuthenticationException, AuthenticationInfo, AuthenticationToken, SimpleAuthenticationInfo, UnknownAccountException, UsernamePasswordToken}
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.util.ByteSource
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConverters._

/**
 * @author Tracy
 * @date 2021/2/9 13:06
 */
class UserRealm extends AuthorizingRealm{
  @Autowired private val userService: UserService = null

  @Autowired private val roleService: RoleService = null

  @Autowired private val promiseService: PromiseService = null

  /**
   * 授权
   *
   * @param principalCollection principalCollection
   * @return
   */
  override protected def doGetAuthorizationInfo(principalCollection: PrincipalCollection): AuthorizationInfo = { //开始获取权限
    val primaryPrincipal: Any = principalCollection.getPrimaryPrincipal
    val simpleAuthorizationInfo: SimpleAuthorizationInfo = new SimpleAuthorizationInfo

    primaryPrincipal match {
      case user: User =>
        //获取user的角色
        val roleSet: util.Set[Role] = roleService.getRoleByUserId(user.getUserId)
        val roles: util.Set[String] = new util.HashSet[String]
        for (role <- roleSet.asScala) {
          roles.add(role.getRoleName)
        }
        simpleAuthorizationInfo.addRoles(roles)
        //通过角色获取权限
        val promiseSet: util.Set[Promise] = promiseService.getPromiseByRole(roleSet)
        val promises: util.Set[String] = new util.HashSet[String]

        for (promise <- promiseSet.asScala) {
          promises.add(promise.getPromiseName)
        }
        simpleAuthorizationInfo.addStringPermissions(promises)
      case _ =>
    }

    simpleAuthorizationInfo
  }

  /**
   * 认证
   *
   * @param authenticationToken authenticationToken
   * @return
   * @throws AuthenticationException AuthenticationException
   */
  @throws[AuthenticationException]
  override protected def doGetAuthenticationInfo(authenticationToken: AuthenticationToken): AuthenticationInfo = { //转换为UsernamePasswordToken
    val upToken: UsernamePasswordToken = authenticationToken.asInstanceOf[UsernamePasswordToken]
    //从数据库中获取用户信息
    println(userService.findUserByUserName(upToken.getUsername))

    val userFindUserByUsername: User = userService.findUserByUserName(upToken.getUsername)
    if (userFindUserByUsername == null) throw new UnknownAccountException("用户不存在")
    userFindUserByUsername.setRoles(roleService.getRoleByUserId(userFindUserByUsername.getUserId))
    //盐值 以用户名作为盐值
    //配置文件中标明用MD5加密了1024次
    val credentialsSalt: ByteSource = ByteSource.Util.bytes(EncoderConstant.SALT)
    //比对密码
    new SimpleAuthenticationInfo(userFindUserByUsername, userFindUserByUsername.getPassword, credentialsSalt, this.getName)
  }
}
