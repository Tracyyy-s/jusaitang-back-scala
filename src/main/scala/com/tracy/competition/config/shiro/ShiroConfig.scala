package com.tracy.competition.config.shiro

import java.util
import java.util.{LinkedHashMap, Map}

import com.tracy.competition.realm.UserRealm
import javax.servlet.Filter
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.crazycake.shiro.{RedisCacheManager, RedisManager, RedisSessionDAO}
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.context.annotation.{Bean, Configuration}

/**
 * @author Tracy
 * @date 2021/2/9 13:07
 */
@Configuration
class ShiroConfig {

  /**
   * Shiro的Web过滤器Factory 命名:shiroFilter
   */
  @Bean(name = Array("shiroFilter"))
  def shiroFilterFactoryBean(securityManager: SecurityManager): ShiroFilterFactoryBean = {
    val shiroFilterFactoryBean = new ShiroFilterFactoryBean
    //Shiro的核心安全接口,这个属性是必须的
    shiroFilterFactoryBean.setSecurityManager(securityManager)
    val filterMap = new util.LinkedHashMap[String, Filter]
    filterMap.put("authc", new AjaxPermissionsAuthorizationFilter)
    shiroFilterFactoryBean.setFilters(filterMap)
    /*定义shiro过滤链  Map结构
             * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
             * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
             * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
             */ val filterChainDefinitionMap = new util.LinkedHashMap[String, String]
    /* 过滤链定义，从上向下顺序执行，一般将 / ** 放在最为下边:这是一个坑呢，一不小心代码就不好使了;
              authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问 */ filterChainDefinitionMap.put("*.js", "anon")
    filterChainDefinitionMap.put("*.css", "anon")
    filterChainDefinitionMap.put("/druid/**", "anon")
    filterChainDefinitionMap.put("/swagger-ui.html/**", "anon")
    filterChainDefinitionMap.put("/user/login", "anon")
    filterChainDefinitionMap.put("/user/isLogin", "anon")
    filterChainDefinitionMap.put("/user/logout", "anon")
    filterChainDefinitionMap.put("/file/uploadFile", "anon")
    filterChainDefinitionMap.put("/notification/insertCompetition", "anon")
    filterChainDefinitionMap.put("/**", "anon")
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap)
    shiroFilterFactoryBean
  }

  @Bean
  def securityManager: SecurityManager = {
    val securityManager = new DefaultWebSecurityManager
    // 设置realm.
    securityManager.setRealm(userRealm)
    // 自定义缓存实现 使用redis
    securityManager.setCacheManager(cacheManager)
    // 自定义session管理 使用redis
    securityManager.setSessionManager(sessionManager)
    securityManager
  }

  /**
   * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
   *
   * @return
   */
  @Bean
  def userRealm: UserRealm = {
    val userRealm = new UserRealm
    userRealm.setCredentialsMatcher(hashedCredentialsMatcher)
    userRealm
  }

  /**
   * 加密规则
   * 用MD5加密1024次
   *
   * @return
   */
  def hashedCredentialsMatcher: HashedCredentialsMatcher = {
    val hashedCredentialsMatcher = new HashedCredentialsMatcher
    //散列算法:这里使用MD5算法;
    hashedCredentialsMatcher.setHashAlgorithmName("md5")
    //散列的次数，比如散列两次，相当于 md5(md5(""));
    hashedCredentialsMatcher.setHashIterations(1024)
    //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
    hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true)
    hashedCredentialsMatcher
  }

  /**
   * cacheManager 缓存 redis实现
   * 使用的是shiro-redis开源插件
   *
   * @return
   */
  def cacheManager: RedisCacheManager = {
    val redisCacheManager = new RedisCacheManager
    redisCacheManager.setRedisManager(redisManager)
    redisCacheManager
  }

  /**
   * 配置shiro redisManager
   * 使用的是shiro-redis开源插件
   *
   * @return
   */
  def redisManager: RedisManager = {
    val redisManager = new RedisManager
    redisManager.setHost("www.tracys.cn")
    redisManager.setPort(6379)
    // 配置缓存过期时间，单位/秒
    redisManager.setExpire(1800)
    redisManager.setTimeout(0)
    redisManager.setPassword("GONGRAN0812")
    redisManager
  }

  /**
   * Session Manager
   * 使用的是shiro-redis开源插件
   */
  @Bean def sessionManager: DefaultWebSessionManager = {
    val sessionManager = new DefaultWebSessionManager
    sessionManager.setSessionDAO(redisSessionDAO)
    sessionManager
  }

  /**
   * RedisSessionDAO shiro sessionDao层的实现 通过redis
   * 使用的是shiro-redis开源插件
   */
  @Bean def redisSessionDAO: RedisSessionDAO = {
    val redisSessionDAO = new RedisSessionDAO
    redisSessionDAO.setRedisManager(redisManager)
    redisSessionDAO
  }


  /** *
   * 授权所用配置
   *
   * @return
   */
  @Bean def getDefaultAdvisorAutoProxyCreator: DefaultAdvisorAutoProxyCreator = {
    val defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator
    defaultAdvisorAutoProxyCreator.setProxyTargetClass(true)
    defaultAdvisorAutoProxyCreator
  }

  /** *
   * 使授权注解起作用不如不想配置可以在pom文件中加入
   * <dependency>
   * <groupId>org.springframework.boot</groupId>
   * <artifactId>spring-boot-starter-aop</artifactId>
   * </dependency>
   *
   * @param securityManager securityManager
   * @return
   */
  @Bean def authorizationAttributeSourceAdvisor(securityManager: SecurityManager): AuthorizationAttributeSourceAdvisor = {
    val authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager)
    authorizationAttributeSourceAdvisor
  }

  /**
   * Shiro生命周期处理器
   */
  @Bean def getLifecycleBeanPostProcessor = new LifecycleBeanPostProcessor

}
