package net.nassoft.ml.scala.wxh.c04

import org.apache.spark._
import org.apache.spark.mllib.util.MLUtils

object testLabeledPoint2 {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("testLabeledPoint2")
    //建立本地环境变量
    val sc = new SparkContext(conf) //建立Spark处理

    val mu = MLUtils.loadLibSVMFile(sc, "/data/f/Workshop/study/sparkmllib/src/main/resources/data/wxh/c04/a.txt") //从路径读取文件
    mu.foreach(println) //打印内容
  }
}
