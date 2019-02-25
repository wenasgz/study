/*
 * Copyright (c) 2019.
 * @author: luxf
 * E-mail: luxf@nassoft.net
 *
 */

package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.stat.test.{BinarySample, StreamingTest}

/**
  * Streaming Significance Testing
  * spark.mllib提供了一些测试的在线实现，以支持A / B测试等用例。 这些测试可以在Spark Streaming DStream [（Boolean，Double）]上执行，
  * 其中每个元组的第一个元素指示控制组（false）或处理组（true），第二个元素是观察值。
  *
  * 流式significance测试支持以下参数：
  * peacePeriod - 从流中忽略的初始数据点数，用于减轻新奇效应。
  * windowSize - 执行假设检验的过去批次数。 设置为0将执行所有以前批次的累积处理。
  */
object TestStreamingSignificanceTesting {

  def main(args: Array[String]): Unit = {

//    val data = ssc.textFileStream(dataDir).map(line => line.split(",") match {
//      case Array(label, value) => BinarySample(label.toBoolean, value.toDouble)
//    })
//
//    val streamingTest = new StreamingTest()
//      .setPeacePeriod(0)
//      .setWindowSize(0)
//      .setTestMethod("welch")
//
//    val out = streamingTest.registerStream(data)
//    out.print()

  }

}
