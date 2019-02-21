package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.linalg.Matrices

object testMatrix {
  def main(args: Array[String]) {
    /**
      *   第一个参数是新矩阵的行数
      *   第二个参数是新矩阵的列数
      *   第三个参数为传入的数据值
      */
    val mx = Matrices.dense(2, 3, Array(1, 2, 3, 4, 5, 6)) //创建一个分布式矩阵
    println(mx) //打印结果
  }
}
