package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.linalg.Matrices

object testMatrix {
  def main(args: Array[String]) {
    val mx = Matrices.dense(2, 3, Array(1, 2, 3, 4, 5, 6)) //创建一个分布式矩阵
    println(mx) //打印结果
  }
}
