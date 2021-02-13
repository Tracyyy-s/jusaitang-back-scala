package com.tracy.competition.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.{CorsRegistry, ResourceHandlerRegistry, WebMvcConfigurer}

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._

/**
 * @author Tracy
 * @date 2021/2/9 11:38
 */
@Configuration
class WebConfig extends WebMvcConfigurer {

  private final val ORIGINS: Array[String] = Array(
    "GET",
    "POST",
    "PUT",
    "DELETE"
  )

  /**
   * 配置静态资源路径映射
   *
   * @param registry registry
   */
  override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
    registry.addResourceHandler("/images/**").addResourceLocations("file:/file/")
  }

  override def addCorsMappings(registry: CorsRegistry): Unit = {
    registry
      .addMapping("/**")
      .allowedOrigins("*")
      .allowCredentials(true)
      .allowedMethods(ORIGINS: _*)
      .maxAge(3600)
  }
}
