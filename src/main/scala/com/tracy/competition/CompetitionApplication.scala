package com.tracy.competition

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * @author Tracy
 * @date 2021/2/8 23:53
 */
object CompetitionApplication extends App {

  // ~ project entrance
  SpringApplication.run(classOf[AppConfig])
}

@SpringBootApplication
@EnableTransactionManagement
class AppConfig {

}
