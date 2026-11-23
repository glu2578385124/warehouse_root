package com.cn.tz16.warehouse.realtime.apps

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._

/**
 * @author: KING
 * @description:
 * @Date:Created in 2020-09-13 15:49
 */
object FlinkTest {

  def main(args: Array[String]): Unit = {
    //1.获取上下文环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    //2.设置运行参数
    env.setParallelism(1)
    val DS: DataStream[String] = env.fromCollection(List("hadoop", "hive", "kylin"))
    DS.print()
    env.execute("FlinkTest")
  }
}