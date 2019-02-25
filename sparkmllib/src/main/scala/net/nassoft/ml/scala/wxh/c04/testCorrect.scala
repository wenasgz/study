package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

object testCorrect {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("testCorrect ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    // 获取当前运行路径
    val userPath = System.getProperty("user.dir")
    //读取文件
    val rddX = sc.textFile(userPath+ "/sparkmllib/src/main/resources/data/wxh/c04/testCorrectx.txt") //创建RDD文件路径
      .flatMap(_.split(' ') //进行分割
      .map(_.toDouble)) //转化为Double类型
    //读取文件
    val rddY = sc.textFile(userPath+ "/sparkmllib/src/main/resources/data/wxh/c04/testCorrecty.txt") //创建RDD文件路径
      .flatMap(_.split(' ') //进行分割
      .map(_.toDouble)) //转化为Double类型
    val correlation: Double = Statistics.corr(rddX, rddY) //计算不同数据之间的皮尔逊相关系数-数据余弦分开程度
    println(correlation) //打印结果
  }
}
