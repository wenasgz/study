package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

object testSummary2 {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("testSummary2") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    // 获取当前运行路径
    val userPath = System.getProperty("user.dir")
    //读取文件
    val rdd = sc.textFile(userPath+ "/sparkmllib/src/main/resources/data/wxh/c04/testSummary2.txt") //创建RDD文件路径
      .map(_.split(' ') //按“ ”分割
      .map(_.toDouble)) //转成Double类型
      .map(line => Vectors.dense(line)) //转成Vector格式
    val summary = Statistics.colStats(rdd) //获取Statistics实例
    //关于距离的举例都有一点问题,主要是用于多维(1~n)上两点的距离
    /**
      *曼哈顿距离（Manhattan Distance）是由十九世纪的赫尔曼·闵可夫斯基所创词汇 ，是种使用在几何度量空间的几何学用语，
      * 用以标明两个点在标准坐标系上的绝对轴距总和。
      * 例如在平面上，坐标（x1, y1）的i点与坐标（x2, y2）的j点的曼哈顿距离为：
      * d(i,j)=|X1-X2|+|Y1-Y2|.
      */
    println("曼哈顿距离: "+summary.normL1) //计算曼哈顿距离
    /**
      * 欧几里得度量（euclidean metric）（也称欧氏距离）是一个通常采用的距离定义，指在m维空间中两个点之间的真实距离，
      * 或者向量的自然长度（即该点到原点的距离）。在二维和三维空间中的欧氏距离就是两点之间的实际距离。
      *
      */
    println("欧几里得距离: "+summary.normL2) //计算欧几里得距离
  }
}

