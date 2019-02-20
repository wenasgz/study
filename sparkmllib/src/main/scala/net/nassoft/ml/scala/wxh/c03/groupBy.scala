/*
 * Copyright (c) 2019.
 * @author: luxf
 * E-mail: luxf@nassoft.net
 *
 */

import org.apache.spark.{SparkConf, SparkContext}

object groupBy {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("groupBy ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    var arr = sc.parallelize(Array(1, 2, 3, 4, 5)) //创建数据集
    val g1 = arr.groupBy(myFilter(_))
    arr.foreach(println)
    println("----------------------")
    g1.foreach(println)

    /**
      * 结果
      * (g2,CompactBuffer(1, 2))
      * (g1,CompactBuffer(3, 4, 5))
      */

  }

  def myFilter(num: Int): String = { //自定义方法
    if (num >= 3) "g1" else "g2"//条件
  }


}
