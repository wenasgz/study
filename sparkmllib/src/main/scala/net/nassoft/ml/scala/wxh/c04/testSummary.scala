package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

object testSummary {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("testSummary") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    // 获取当前运行路径
    val userPath = System.getProperty("user.dir")
    //读取文件
    val rdd = sc.textFile(userPath+ "/sparkmllib/src/main/resources/data/wxh/c04/testSummary.txt") //创建RDD文件路径
      .map(_.split(' ') //按“ ”分割
      .map(_.toDouble)) //转成Double类型
      .map(line => Vectors.dense(line)) //转成Vector格式
    val summary = Statistics.colStats(rdd) //获取Statistics实例

    println("个数: "+summary.count) //计算个数
    println("最大值: "+summary.max) //计算最大值
    println("最小值: "+summary.min) //计算最小值
    println("均值: "+summary.mean) //计算均值
    println("不包括0的个数: "+summary.numNonzeros) //不包括0的个数
    /**
      * 标准差（StandardDeviation），在概率统计中最常使用作为统计分布程度（statisticaldispersion）上的测量。
      * 标准差定义是总体各单位标准值与其平均数离差平方的算术平均数的平方根。它反映组内个体间的离散程度。
      * 测量到分布程度的结果，原则上具有两种性质：
      * 为非负数值，与测量资料具有相同单位。一个总量的标准差或一个随机变量的标准差，及一个子集合样品数的标准差之间，有所差别。
      * 简单来说，标准差是一组数据平均值分散程度的一种度量。一个较大的标准差，代表大部分数值和其平均值之间差异较大；一个较小的标准差，
      * 代表这些数值较接近平均值。
      * 例如，两组数的集合{0,5,9,14}和{5,6,8,9}其平均值都是7，但第二个集合具有较小的标准差。
      */
    println("标准差: "+summary.variance) //计算标准差



  }
}
