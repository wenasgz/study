package net.nassoft.ml.scala.wxh.c04

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint

object testVector {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

//    val vd: Vector = Vectors.dense(2, 0, 6) //建立密集向量
//    println(vd(2)) //打印密集向量第3个值
//    val vs: Vector = Vectors.sparse(4, Array(0, 1, 2, 3), Array(9, 5, 2, 7)) //建立稀疏向量
//    println(vs(2)) //打印稀疏向量第3个值
    use_Vector
  }


  /**
    * Function Feature:建立向量、标签的具体使用
    *
    * dense方法可以理解为MLlib专用的一种集合形式，与Array类相似
    * spare方法是将给定的数据Array数据分解成几个部分进行处理
    * LabeledPoint是建立向量标签的静态类，主要有两个方法：
    *   1、Features用于显示打印标记点所代表的数据内容
    *   2、Label用于显示标记数
    *
    */
  def use_Vector(): Unit = {
    //建立密集向量
    val vd: Vector = Vectors.dense(2, 0, 6)
    //打印密集向量的第3个值
    println(vd(2))
    //对密集向量建立标记点
    val pos = LabeledPoint(1, vd)
    //打印标记点内容数据
    println(pos.features)
    //打印既定标记(比如你标记1，打印就是1.0)
    println(pos.label)
    //建立稀疏向量
    val vs: Vector = Vectors.sparse(4, Array(0, 1, 2, 3), Array(9, 5, 2, 7))
    //打印稀疏向量的第4个值
    println(vs(3))
    //对稀疏向量建立标记点
    val neg = LabeledPoint(2, vs)
    //打印标记点的内容
    println(neg.features)
    //打印标记点
    println(neg.label)

  }


}


