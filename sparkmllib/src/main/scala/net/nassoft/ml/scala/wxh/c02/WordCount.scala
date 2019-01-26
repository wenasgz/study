package net.nassoft.ml.scala.wxh.c02

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]) {
    // 创建环境变量
    val conf = new SparkConf().setMaster("local").setAppName("wordCount")
    // 创建环境变量实例
    val sc = new SparkContext(conf)
    // 获取当前运行路径
    val userPath = System.getProperty("user.dir")
    //读取文件
    val data = sc.textFile(userPath + "/sparkmllib/src/main/resources/data/wxh/c02/wc.txt")
    //word计数
    data.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).collect().foreach(println)
  }
}
