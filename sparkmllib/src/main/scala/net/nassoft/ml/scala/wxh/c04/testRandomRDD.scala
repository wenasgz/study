package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.random.RandomRDDs._
import org.apache.spark.{SparkConf, SparkContext}

object testRandomRDD {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("testRandomRDD")
    val sc = new SparkContext(conf)
    val randomNum = normalRDD(sc, 100)
    randomNum.foreach(println)
  }
}
