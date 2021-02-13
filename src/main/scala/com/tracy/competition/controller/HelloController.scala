package com.tracy.competition.controller

import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody, RestController}

/**
 * @author Tracy
 * @date 2021/2/13 20:31
 */
@RestController
class HelloController {

  @ResponseBody
  @RequestMapping("/nihao")
  def hello(): String = {
    "hello world"
  }
}
