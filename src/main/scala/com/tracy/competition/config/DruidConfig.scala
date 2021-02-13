package com.tracy.competition.config

import com.alibaba.druid.pool.DruidDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}

/**
 * @author Tracy
 * @date 2021/2/9 0:45
 */
@Configuration
@PropertySource(Array("classpath:db-config.properties"))
class DruidConfig {

  /**
   * 在容器中注入Druid数据源
   *
   * @return 数据源
   */
  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean def druid = new DruidDataSource
}
