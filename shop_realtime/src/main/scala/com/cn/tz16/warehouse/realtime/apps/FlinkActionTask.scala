package com.cn.tz16.warehouse.realtime.apps

import java.util.Properties

import com.cn.tz16.warehouse.realtime.process.VisitedMeasureProcess
import com.cn.tz16.warehouse.realtime.utils.GlobalConfigUtil
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.flink.api.scala._

/**
 * @author: KING
 * @description:
 * @Date:Created in 2020-09-13 16:56
 */
object FlinkActionTask {

  def main(args: Array[String]): Unit = {
    //1.获取上下文环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val properties: Properties = new Properties
    properties.setProperty("bootstrap.servers", GlobalConfigUtil.`bootstrap.servers`)
    properties.setProperty("zookeeper.connect", GlobalConfigUtil.`zookeeper.connect`)
    properties.setProperty("group.id", "FlinkKafkaTest")
    properties.setProperty("enable.auto.commit", GlobalConfigUtil.`enable.auto.commit`)
    properties.setProperty("auto.commit.interval.ms", GlobalConfigUtil.`auto.commit.interval.ms`)
    properties.setProperty("auto.offset.reset", GlobalConfigUtil.`auto.offset.reset`)
    properties.setProperty("key.serializer", GlobalConfigUtil.`key.serializer`)
    properties.setProperty("key.deserializer", GlobalConfigUtil.`key.deserializer`)

    val click_log_kafkaConsumer: FlinkKafkaConsumer011[String] = new FlinkKafkaConsumer011[String](
      GlobalConfigUtil.`input.topic.click_log`, new SimpleStringSchema(), properties)

    val click_log_DataStream: DataStream[String] = env.addSource(click_log_kafkaConsumer)

    //計算
    VisitedMeasureProcess.process(click_log_DataStream)

    //click_log_DataStream.print()

    env.execute("FlinkTest")
  }
}
