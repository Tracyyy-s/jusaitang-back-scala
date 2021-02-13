package com.tracy.competition.controller

import java.util
import java.util.List

import com.tracy.competition.domain.constant.EncoderConstant
import com.tracy.competition.domain.entity.User
import com.tracy.competition.domain.enums.{ErrorEnum, SuccessEnum}
import com.tracy.competition.service.UserService
import com.tracy.competition.utils.ResponseMessage
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.{AuthenticationException, UnknownAccountException, UsernamePasswordToken}
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.subject.Subject
import org.apache.shiro.util.ByteSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, ResponseBody}

/**
 * @author Tracy
 * @date 2021/2/9 15:41
 */
@Controller
@RequestMapping(Array("/user")) class UserController {
  @Autowired private val userService: UserService = null

  /**
   * 用户登录
   *
   * @param user
   * @return
   */
  @ResponseBody
  @RequestMapping(value = Array("/login"), method = Array(RequestMethod.POST))
  def login(@RequestBody user: User): ResponseMessage = { //获取subject
    val subject: Subject = SecurityUtils.getSubject
    if (!subject.isAuthenticated) {
      println("用户未登录")
      //如果用户未登录，执行登录逻辑
      try {
        val usernamePasswordToken: UsernamePasswordToken = new UsernamePasswordToken(user.getUserName, user.getPassword)
        // 执行认证提交
        subject.login(usernamePasswordToken)
        //返回登录成功信息
        val responseMessage: ResponseMessage = ResponseMessage(SuccessEnum.S_LOGIN_SUCCESS)
        //获取用户的信息
        val principal: User = subject.getPrincipal.asInstanceOf[User]
        import scala.collection.JavaConverters._
        for (role <- principal.getRoles.asScala) {
          if (role.getRoleId == user.getRoles.iterator.next.getRoleId) { //将角色返回
            responseMessage.getData.put("roles", principal.getRoles)
            println(principal.getRoles)
            return responseMessage
          }
        }
        if ("1" == user.getRoles.iterator.next.getRoleId) { //无该角色，登出（后同）
          if (SecurityUtils.getSubject.isAuthenticated) subject.logout()
          ResponseMessage(ErrorEnum.E_NOTADMIN)
        }
        else if ("2" == user.getRoles.iterator.next.getRoleId) {
          if (SecurityUtils.getSubject.isAuthenticated) subject.logout()
          ResponseMessage(ErrorEnum.E_NOTSTUDENT)
        }
        else {
          if (SecurityUtils.getSubject.isAuthenticated) subject.logout()
          ResponseMessage(ErrorEnum.E_NOTROLE)
        }
      } catch {
        case e: UnknownAccountException =>
          e.printStackTrace()
          //返回用户不存在
          ResponseMessage(ErrorEnum.E_UNKNOWN_ACCOUNT)
        case e: AuthenticationException =>
          e.printStackTrace()
          //返回密码错误信息
          ResponseMessage(ErrorEnum.E_PASSWORD_ERROR)
      }
    }
    else { //如果用户已经登录则无需执行登录轮逻辑，直接返回登录成功
      ResponseMessage(SuccessEnum.S_LOGINED)
    }
  }

  /**
   * 用户个人信息
   *
   * @return
   */
  @RequestMapping(value = Array("/findUserByUsername"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def findUserByUsername: User = {
    var user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    user = userService.findUserByUserName(user.getUserName)
    user
  }

  /**
   * 修改用户密码
   *
   * @param u
   * @return
   */
  @RequestMapping(value = Array("/updatePassword"), method = Array(RequestMethod.POST))
  @ResponseBody def updatePassword(@RequestBody u: User): ResponseMessage = try {
    val user: User = SecurityUtils.getSubject.getPrincipal.asInstanceOf[User]
    //获取盐值
    val credentialsSalt: ByteSource = ByteSource.Util.bytes(EncoderConstant.SALT)
    //加密密码
    val simpleHash: SimpleHash = new SimpleHash("MD5", u.getPassword, credentialsSalt, 1024)
    user.setPassword(simpleHash.toString)
    userService.updatePassword(user)
    val subject: Subject = SecurityUtils.getSubject
    if (subject.isAuthenticated) subject.logout()
    ResponseMessage("1", "修改成功,请重新登录！")
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "修改失败")
  }

  /**
   * 注销登录
   *
   * @return
   */
  @RequestMapping(value = Array("/logout"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def logout: ResponseMessage = try {
    val subject: Subject = SecurityUtils.getSubject
    if (subject.isAuthenticated) {
      subject.logout()
      ResponseMessage("1", "退出成功")
    }
    else ResponseMessage(ErrorEnum.E_UNAUTHENTICATED)
  } catch {
    case e: Exception =>
      e.printStackTrace()
      ResponseMessage("0", "退出失败")
  }

  /**
   * 根据用户id查找用户
   *
   * @param userId
   * @return
   */
  @RequestMapping(value = Array("/getUserByUserId"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def getUserByUserId(userId: String): User = userService.findUserByUserId(userId)

  /**
   * 判断是否登录
   *
   */
  @RequestMapping(value = Array("/isLogin"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def isLogin: ResponseMessage = {
    if (SecurityUtils.getSubject.isAuthenticated) return ResponseMessage("200", "已登录")
    ResponseMessage("1001", "未登录,自动跳转到登录界面！")
  }

  /**
   * 获得所有用户
   *
   * @return
   */
  @RequestMapping(value = Array("/getAllUser"), method = Array(RequestMethod.POST, RequestMethod.GET))
  @ResponseBody def getAllUser: ResponseMessage = {
    var users: util.List[User] = null
    try {
      users = userService.getAllUser
      val responseMessage: ResponseMessage = ResponseMessage("1", "获取成功")
      responseMessage.getData.put("userList", users)
      responseMessage
    } catch {
      case e: Exception =>
        e.printStackTrace()
        ResponseMessage("0", "获取失败")
    }
  }
}

