package com.cn.tz16.warehouse.realtime.utils

import java.text.SimpleDateFormat

/**
 * 日期工具类（进行日期的格式转换）
 */
object DateUtil {

  // 需求：将年月日时间分秒 => 年月日
  def datetime2date(datetime:String) = {
    // 1. 先将datetime转换为Date
    val datetimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = datetimeSdf.parse(datetime)

    // 2. 再将Date转换为日期类型
    val dateSdf = new SimpleDateFormat("yyyyMMdd")
    dateSdf.format(date)
  }

  def main(args: Array[String]): Unit = {
    println(datetime2date("2019-10-17 11:27:00"))
  }
}