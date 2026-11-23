package com.cn.tz16.warehouse.realtime.utils

import com.typesafe.config.{Config, ConfigFactory}

/**
 * @author: KING
 * @description: 全局配置文件工具类
 * @Date:Created in 2020-09-13 15:59
 */
object GlobalConfigUtil {
  //使用ConfigFactory读取配置
  private val config: Config = ConfigFactory.load()

  //獲取配置
  val `bootstrap.servers`: String = config.getString("bootstrap.servers")
  val `zookeeper.connect`: String = config.getString("zookeeper.connect")
  val `group.id`: String = config.getString("group.id")
  val `enable.auto.commit`: String = config.getString("enable.auto.commit")
  val `auto.commit.interval.ms`: String = config.getString("auto.commit.interval.ms")
  val `auto.offset.reset`: String = config.getString("auto.offset.reset")
  val `key.serializer`: String = config.getString("key.serializer")
  val `key.deserializer`: String = config.getString("key.deserializer")
  val `input.topic.click_log`: String = config.getString("input.topic.click_log")
  val `input.topic.canal`: String = config.getString("input.topic.canal")
  val `output.topic`: String = config.getString("output.topic")

  def main(args: Array[String]): Unit = {
    println(`bootstrap.servers`)
  }
}