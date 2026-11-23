package com.cn.tz16.warehouse.realtime.utils

import redis.clients.jedis.Jedis

/**
 * @author: KING
 * @description:
 * @Date:Created in 2020-09-13 17:24
 */
object RedisUtil {
  def getJedis(): Jedis ={
    val jedis: Jedis = new Jedis("hadoop-13",6379)
    jedis
  }

  def main(args: Array[String]): Unit = {
    val jedis: Jedis = RedisUtil.getJedis()
    jedis.hset("test","111","2222")
    jedis.close()
  }
}