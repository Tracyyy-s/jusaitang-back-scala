package com.tracy.competition.config.shiro

import java.io.PrintWriter

import com.alibaba.fastjson.JSONObject
import com.tracy.competition.domain.enums.ErrorEnum
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import javax.servlet.{Filter, ServletRequest, ServletResponse}
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.RequestMethod

/**
 * @author Tracy
 * @date 2021/2/9 13:08
 */
class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter{

  override protected def onAccessDenied(request: ServletRequest, response: ServletResponse): Boolean = {
    val jsonObject = new JSONObject
    //用户未登录返回未登录的json信息
    jsonObject.put("status", ErrorEnum.E_UNAUTHENTICATED.getErrorCode)
    jsonObject.put("msg", ErrorEnum.E_UNAUTHENTICATED.getErrorMsg)
    var out: PrintWriter = null
    val res: HttpServletResponse = response.asInstanceOf[HttpServletResponse]
    try {
      res.setCharacterEncoding("UTF-8")
      res.setContentType("application/json")
      out = response.getWriter
      out.println(jsonObject)
    } catch {
      case _: Exception =>

    } finally if (null != out) {
      out.flush()
      out.close()
    }
    false
  }

  /**
   * 解决复杂请求，他们的contentType是application/json，方法类型type是post、patch、delete、put，
   * 这些请求在执行的时候，会先往服务器发送一个探测请求，而这个探测请求的方法类型就是OPTIONS，
   * 这个请求，默认是不会带上cookie的，而且也无法设置让他带上cookie。
   * 如果后端在接收到一个请求之后，判断没有携带cookie,或者携带的cookie已经过期，
   * 那么他会让这个请求返回错误，不会成功返回状态码200。
   * 这样，复杂请求的真正请求就不会到达服务端。
   * 所以复杂请求这里就永远也请求不到数据。
   * <p>
   * 此方法然所有复杂请求的探测请求都返回200
   *
   * @param request
   * @param response
   * @param mappedValue
   * @return
   * @throws Exception
   */
  @throws[Exception]
  override def onPreHandle(request: ServletRequest, response: ServletResponse, mappedValue: Any): Boolean = {
    val req: HttpServletRequest = request.asInstanceOf[HttpServletRequest]
    val res: HttpServletResponse = response.asInstanceOf[HttpServletResponse]
    if (req.getMethod == RequestMethod.OPTIONS.name) return true
    super.onPreHandle(request, response, mappedValue)
  }

  /**
   * 为response设置header，实现跨域
   */
  private def setHeader(request: HttpServletRequest, response: HttpServletResponse): Unit = { //跨域的header设置
    response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"))
    response.setHeader("Access-Control-Allow-Methods", request.getMethod)
    response.setHeader("Access-Control-Allow-Credentials", "true")
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"))
    //防止乱码，适用于传输JSON数据
    response.setHeader("Content-Type", "application/json;charset=UTF-8")
  }

  @Bean def registration(filter: AjaxPermissionsAuthorizationFilter): FilterRegistrationBean[Filter] = {
    val registration = new FilterRegistrationBean[Filter](filter)
    registration.setEnabled(false)
    registration
  }
}
