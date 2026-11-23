package com.cn.tz16.warehouse.realtime.process

import java.sql.{Connection, DriverManager, ResultSet, Statement}

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.cn.tz16.warehouse.realtime.bean.{DimGoodsCatDBEntity, DimGoodsDBEntity, DimOrgDBEntity, DimShopsDBEntity}
import com.cn.tz16.warehouse.realtime.utils.RedisUtil
import redis.clients.jedis.Jedis

/**
 * @author: KING
 * @description: 从mysql中加载维度表到redis中
 * @Date:Created in 2020-09-19 21:48
 */
object DimensionDataLoader {

  def main(args: Array[String]): Unit = {
    //一。获取mysql,redis连接
    Class.forName("com.mysql.jdbc.Driver");
//    val connection: Connection = DriverManager.getConnection("jdbc:mysql://hadoop-13:3306/shop", "root", "root")
    val connection: Connection = DriverManager.getConnection("jdbc:mysql://hadoop-11:3306/shop?useUnicode=true&characterEncoding=utf8", "root", "Shucang@2026")
    //3.构建SQL语句
    val jedis: Jedis = RedisUtil.getJedis()
    //二.加载商品维度数据到redis
    loadDimGoods(connection,jedis)
    //三。加载店铺维度
    loadDimShops(connection,jedis)
    //四.加载商品分类维度
    loadDimGoodsCats(connection,jedis)
    //五.加载区域维度
    loadDimOrg(connection,jedis)
    jedis.close()
    connection.close()

  }

  //二.加载商品维度数据到redis
  def loadDimGoods(connection: Connection, jedis: Jedis): Unit = {
    //構建sql
    val sql = "select goodsId,goodsName,shopId,goodsCatId from goods"

    val statement: Statement = connection.createStatement()
    val resultSet: ResultSet = statement.executeQuery(sql)
    while (resultSet.next()) {
      val goodsId = resultSet.getString("goodsId")
      val goodsName = resultSet.getString("goodsName")
      val shopId = resultSet.getString("shopId")
      val goodsCatId = resultSet.getString("goodsCatId")
      val entity = DimGoodsDBEntity(goodsId.toLong,goodsName,shopId.toLong,goodsCatId.toInt)
      //寫入redis  保存為String
      val json: String = JSON.toJSONString(entity,SerializerFeature.DisableCircularReferenceDetect)
      println(json)
      jedis.hset("shop:dim_goods",goodsId,json)
    }
  }

  //三.加载店铺维度
  def loadDimShops(connection: Connection, jedis: Jedis): Unit = {
    //構建sql
    val sql = "select shopId,areaId,shopName,shopCompany from shops"

    val statement: Statement = connection.createStatement()
    val resultSet: ResultSet = statement.executeQuery(sql)
    while (resultSet.next()) {
      val shopId = resultSet.getString("shopId")
      val areaId = resultSet.getString("areaId")
      val shopName = resultSet.getString("shopName")
      val shopCompany = resultSet.getString("shopCompany")
      val entity = DimShopsDBEntity(shopId.toInt,areaId.toInt,shopName,shopCompany)
      //寫入redis  保存為String
      val json: String = JSON.toJSONString(entity,SerializerFeature.DisableCircularReferenceDetect)
      println(json)
      jedis.hset("shop:dim_shops",shopId,json)
    }
  }

  //四。加载商品分类维度
  def loadDimGoodsCats(connection: Connection, jedis: Jedis): Unit = {
    //構建sql
    val sql = "select catId,parentId,catName,cat_level from goods_cats"

    val statement: Statement = connection.createStatement()
    val resultSet: ResultSet = statement.executeQuery(sql)
    while (resultSet.next()) {
      val catId = resultSet.getString("catId")
      val parentId = resultSet.getString("parentId")
      val catName = resultSet.getString("catName")
      val cat_level = resultSet.getString("cat_level")
      val entity = DimGoodsCatDBEntity(catId.toLong,parentId.toLong,catName,cat_level.toInt)
      //寫入redis  保存為String
      val json: String = JSON.toJSONString(entity,SerializerFeature.DisableCircularReferenceDetect)
      println(json)
      jedis.hset("shop:dim_goods_cats",catId,json)
    }
  }

  //五.加载区域维度
  def loadDimOrg(connection: Connection, jedis: Jedis): Unit = {
    //構建sql
    val sql = "select orgId,parentId,orgName,orgLevel from org"

    val statement: Statement = connection.createStatement()
    val resultSet: ResultSet = statement.executeQuery(sql)
    while (resultSet.next()) {
      val orgId = resultSet.getString("orgId")
      val parentId = resultSet.getString("parentId")
      val orgName = resultSet.getString("orgName")
      val orgLevel = resultSet.getString("orgLevel")
      val entity = DimOrgDBEntity(orgId.toInt,parentId.toInt,orgName,orgLevel.toInt)
      //寫入redis  保存為String
      val json: String = JSON.toJSONString(entity,SerializerFeature.DisableCircularReferenceDetect)
      println(json)
      jedis.hset("shop:dim_org",orgId,json)
    }
  }

}