package com.tracy.competition.dao

import org.apache.ibatis.annotations.Mapper

/**
 * @author Tracy
 * @date 2021/2/13 20:33
 */
@Mapper
trait TestMapper {

  def doNothing(): Unit
}
