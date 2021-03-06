package net.nassoft.ml.scala.wxh.c04

import org.apache.spark.mllib.linalg.{Matrices, Vectors}
import org.apache.spark.mllib.stat.Statistics

/**
  * 运用
  * 1.建立零假说（Null Hypothesis），即认为观测值与理论值的差异是由于随机误差所致；
  * 2.确定数据间的实际差异，即求出卡方值；
  * 3.如卡方值大于某特定概率标准（即显着性差异）下的理论值，则拒绝零假说，即实测值与理论值的差异在该显着性水平下是显着的
  *
  * Hypothesis testing (假定检测)
  * spark通过 Statistics 类来支持 Pearson's chi-squared （卡方检测），主要是比较两个及两个以上样本率( 构成比）以及两个分类变量的关联性分析。
  * 其根本思想就是在于比较理论频数和实际频数的吻合程度或拟合优度问题。卡方检测有两种用途，分别是“适配度检定”（Goodness of fit）以及“独立性检定”（independence）。
  * Goodness fo fit（适合度检验）： 执行多次试验得到的观测值，与假设的期望数相比较，观察假设的期望值与实际观测值之间的差距，
  * 称为卡方适合度检验，即在于检验二者接近的程度。比如掷色子。
  * Indenpendence(独立性检验)： 卡方独立性检验是用来检验两个属性间是否独立。其中一个属性做为行，另外一个做为列，
  * 通过貌似相关的关系考察其是否真实存在相关性。比如天气温变化和肺炎发病率。
  * 假定检测的基本思路是，首先我们假定一个结论，然后为这个结论设置期望值，用实际观察值来与这个值做对比，并设定一个阀值，
  * 如果计算结果大于阀值，则假定不成立，否则成立。
  * 根据以上表述，我们需要确定四个值：
  * 1) 结论：结论一般是建立在零假设( Null Hypothesis)的基础上的。零假设即认为观测值与理论值的差异是由于随机误差所致。
  * 比如：“掷色子得到的各种结果概率相同”——这个结论显然我们认定的前提是即便不同也是随机因素导致。
  * 2) 期望值：期望值也就是理论值，理论值可以是某种平均数，比如我们投掷120次色子，要维护结论正确，
  * 那么每个数字的出现理论值就应该都是20
  * 3) 观测值：也就是实际得到的值
  * 4) 阀值：阀值是根据自由度和显著性水平计算出来的（excel 中的 chiinv() 函数）。自由度=(结果选项数-1)x(对比组数-1)，
  * 比如我们将两组掷色子值做比较，那么自由度就是(6-1)x(2-1)=5。显著性水平(a)是原假设为正确的，而我们确把原假设当做错误加以拒绝，
  * 犯这种错误的概率，依据拒绝区间所可能承担的风险来决定，一般选择0.05或0.01。
  * 最后就是计算卡方值：卡方值是各组 （观测值－理论值）^2/理论值  的总和。最后就是比较方差值和阀值。如果小于阀值则接受结论，
  * 否则拒绝结论。或者根据卡方值反算概率p值(excel 中的 chidist() 函数)，将它和显著性水平比较，小于则拒绝，大于则接受。
  **/
object testChiSq {
  def main(args: Array[String]) {
    val vd = Vectors.dense(1, 2, 3, 4, 5,6) //

    /**
      * 假设检验是统计学中强大的工具，用于确定结果是否具有统计学意义，无论该结果是否偶然发生。
      * spark.mllib目前支持Pearson的卡方（χ2χ2）检验，以获得适合度和独立性。
      * 输入数据类型确定是否进行拟合优度或独立性检验。 适合度测试需要输入类型的Vector，
      * 而独立性测试需要一个Matrix作为输入。
      */

    val vdResult = Statistics.chiSqTest(vd)
    println(vdResult)
    println("-------------------------------")
    val mtx = Matrices.dense(3, 2, Array(1, 3, 5, 2, 4, 6))
    val mtxResult = Statistics.chiSqTest(mtx)
    println(mtxResult)
  }
}
