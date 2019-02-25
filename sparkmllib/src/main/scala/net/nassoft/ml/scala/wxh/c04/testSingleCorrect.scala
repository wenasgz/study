package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

object testSingleCorrect {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("testSingleCorrect ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    // 获取当前运行路径
    val userPath = System.getProperty("user.dir")
    //读取文件
    val rdd = sc.textFile(userPath+ "/sparkmllib/src/main/resources/data/wxh/c04/testSingleCorrect.txt") //创建RDD文件路径
      .map(_.split(' ') //切割数据
      .map(_.toDouble)) //转化为Double类型
      .map(line => Vectors.dense(line)) //转为向量
    println(Statistics.corr(rdd, "spearman")) //使用斯皮尔曼计算相关系数
  }
}
