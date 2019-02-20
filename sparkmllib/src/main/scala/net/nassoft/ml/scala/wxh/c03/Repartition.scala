package net.nassoft.ml.scala.wxh.c03

import org.apache.spark.{SparkConf, SparkContext}

object Repartition {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("Repartition ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    val arr = sc.parallelize(Array(1, 2, 3, 4, 5, 6)) //创建数据集
    val arrRe = arr.repartition(3) //重新分区
    println(arrRe.aggregate(0)(math.max(_, _), _ + _))
    println(arrRe.partitions.length)
  } //打印分区数
}
