package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.{SparkConf, SparkContext}

object testStratifiedSampling2 {
  def main(args: Array[String]) {
    val conf = new SparkConf() //创建环境变量
      .setMaster("local") //设置本地化处理
      .setAppName("testSingleCorrect2 ") //设定名称
    val sc = new SparkContext(conf) //创建环境变量实例
    // 获取当前运行路径
    val userPath = System.getProperty("user.dir")
    //读取文件
    val data = sc.textFile(userPath+ "/sparkmllib/src/main/resources/data/wxh/c04/testStratifiedSampling2.txt") //创建RDD文件路径
      .map(row => { //开始处理
      if (row.length == 3) //判断字符数
        (1,row) //建立对应map
      else (2, row) //建立对应map
    })

    /**
      * sampleByKey方法将翻转硬币来决定观察是否被采样，因此需要一次通过数据，并提供预期的样本大小。
      * sampleByKeyExact需要比sampleByKey中使用的每层简单随机抽样更多的资源，但将提供99.99％置信度的确切采样大小。
      */
    val fractions = Map(1 -> 0.3,2->0.7) //设定抽样格式
    val approxSample = data.sampleByKey(withReplacement = false, fractions) //计算抽样样本
    approxSample.foreach(println) //打印结果

    println("-------------------------")
    val approxSampleExt = data.sampleByKeyExact(withReplacement = false, fractions) //计算抽样样本
    approxSampleExt.foreach(println) //打印结果


  }
}
