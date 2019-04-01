/*
 * Copyright (c) 2019.
 * @author: luxf
 * E-mail: luxf@nassoft.net
 *
 */

package net.nassoft.ml.scala.wxh.c05

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.evaluation.{RankingMetrics, RegressionMetrics}
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.{SparkConf, SparkContext}
// jblas的矩阵库-java的一个高性能矩阵计算库
import org.jblas.DoubleMatrix

/**
  * 协同过滤(处理对象movie,使用算法ALS:最小二乘法(实现用户相似度推荐)
  * 余弦相似度实现商品相似度推荐
  */
object CFTest {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("CFTest")
    val sc = new SparkContext(conf)
    // 获取当前运行路径及文件
    val userPath = System.getProperty("user.dir")
    /**
      * The full u data set, 100000 ratings by 943 users on 1682 items.
      * Each user has rated at least 20 movies.  Users and items are
      * numbered consecutively from 1.  The data is randomly
      *               ordered. This is a tab separated list of
      * user id | item id | rating | timestamp.
      * The time stamps are unix seconds since 1/1/1970 UTC
      * ex: 196	242	3	881250949
      */
    val filePath = userPath + "/sparkmllib/src/main/resources/data/wxh/c05/ml-100k/u.data"
    CF(sc, filePath)
  }

  /**
    *
    * @param sc
    * @param fileName
    */
  def CF(sc: SparkContext, fileName: String): Unit = {
    val movieFile = sc.textFile(fileName)
    val RatingDatas = movieFile.map(_.split("\t").take(3))
    //转为Ratings数据
    val ratings = RatingDatas.map(x => Rating(x(0).toInt, x(1).toInt, x(2).toDouble))
    /**
      * 最小二乘法（Alternating Least Squares    ALS）：解决矩阵分解的最优化方法
      * ALS的实现原理是迭代式求解一系列最小二乘回归问题。在每一次迭代时，固定用户因子矩阵或是物品因子矩阵中的一个，
      * 然后用固定的这个矩阵以及评级数据来更新另一个矩阵。
      * 之后，被更新的矩阵被固定住，再更新另外一个矩阵。如此迭代，直到模型收敛（或是迭代了预设好的次数）。
      */
    //获取用户评价模型,设置k因子,和迭代次数,隐藏因子lambda,获取模型
    /*
    *   rank ：对应ALS模型中的因子个数，也就是在低阶近似矩阵中的隐含特征个数。因子个
          数一般越多越好。但它也会直接影响模型训练和保存时所需的内存开销，尤其是在用户
          和物品很多的时候。因此实践中该参数常作为训练效果与系统开销之间的调节参数。通
          常，其合理取值为10到200。
        iterations ：对应运行时的迭代次数。ALS能确保每次迭代都能降低评级矩阵的重建误
           差，但一般经少数次迭代后ALS模型便已能收敛为一个比较合理的好模型。这样，大部分
           情况下都没必要迭代太多次（10次左右一般就挺好）。
       lambda ：该参数控制模型的正则化过程，从而控制模型的过拟合情况。其值越高，正则
          化越严厉。该参数的赋值与实际数据的大小、特征和稀疏程度有关。和其他的机器学习
          模型一样，正则参数应该通过用非样本的测试数据进行交叉验证来调整。
    * */
    val model = ALS.train(ratings, 50, 10, 0.01)

    //基于用户相似度推荐
    println("userNumber:" + model.userFeatures.count() + "\t" + "productNum:" + model.productFeatures.count())
    //指定用户及商品,输出预测值
    println("指定用户789及商品123,输出预测值: " + model.predict(789, 123))
    //为指定用户推荐的前N商品
    model.recommendProducts(789, 11).foreach(println(_))
    //为每个人推荐前十个商品
    model.recommendProductsForUsers(10).take(1).foreach {
      case (x, rating) => println(rating(0))
    }


    //基于商品相似度(使用余弦相似度)进行推荐,获取某个商品的特征值
    /**
      * 计算相似度的方法有相似度是通过某种方式比较表示两个物品的向量而得到的。
      * 常见的相似度衡量方法包括皮尔森相关系数（Pearson correlation）、
      * 针对实数向量的余弦相似度（cosine similarity）和
      * 针对二元向量的杰卡德相似系数（Jaccard similarity）
      */
    val itemFactory = model.productFeatures.lookup(567).head
    val itemVector = new DoubleMatrix(itemFactory)
    //求余弦相似度
    val sim = model.productFeatures.map {
      case (id, factory) =>
        val factorVector = new DoubleMatrix(factory)
        val sim = cosineSimilarity(factorVector, itemVector)
        (id, sim)
    }
    val sortedsim = sim.top(11)(Ordering.by[(Int, Double), Double] {
      case (id, sim) => sim
    })
    println(sortedsim.take(10).mkString("\n"))
    //模型评估,通过均误差
    //实际用户评估值
    val actualRatings = ratings.map {
      case Rating(user, item, rats) => ((user, item), rats)
    }
    val userItems = ratings.map {
      case (Rating(user, item, rats)) => (user, item)
    }
    //模型的用户对商品的预测值
    val predictRatings = model.predict(userItems).map {
      case (Rating(user, item, rats)) => ((user, item), rats)
    }
    //联合获取rate值
    val rates = actualRatings.join(predictRatings).map {
      case x => (x._2._1, x._2._2)
    }
    //求均方差
    val regressionMetrics = new RegressionMetrics(rates)
    //越接近0越佳
    println(regressionMetrics.meanSquaredError)
    //全局平均准确率(MAP)
    val itemFactors = model.productFeatures.map { case (id, factor)
    => factor
    }.collect()
    val itemMatrix = new DoubleMatrix(itemFactors)
    //分布式广播商品的特征矩阵
    val imBroadcast = sc.broadcast(itemMatrix)
    //计算每一个用户的推荐,在这个操作里，会对用户因子矩阵和电影因子矩阵做乘积，其结果为一个表示各个电影预计评级的向量（长度为
    //1682，即电影的总数目）
    val allRecs = model.userFeatures.map { case (userId, array) =>
      val userVector = new DoubleMatrix(array)
      val scores = imBroadcast.value.mmul(userVector)
      val sortedWithId = scores.data.zipWithIndex.sortBy(-_._1)
      val recommendedIds = sortedWithId.map(_._2 + 1).toSeq //+1,矩阵从0开始
      (userId, recommendedIds)
    }
    //实际评分
    val userMovies = ratings.map { case Rating(user, product, rating) =>
      (user, product)
    }.groupBy(_._1)
    val predictedAndTrueForRanking = allRecs.join(userMovies).map { case
      (userId, (predicted, actualWithIds)) =>
      val actual = actualWithIds.map(_._2)
      (predicted.toArray, actual.toArray)
    }

    /**
      * 使用MLlib的 RankingMetrics 类来计算基于排名的评估指标。
      * 类似地，需要向我们之前的平均准确率函数传入一个键值对类型的RDD。
      * 其键为给定用户预测的推荐物品的ID数组，而值则是实际的物品ID数组。
      */
    //求MAP,越大越好吧
    val rankingMetrics = new RankingMetrics(predictedAndTrueForRanking)
    println("Mean Average Precision = " + rankingMetrics.meanAveragePrecision)
  }

  //余弦相似度计算-基于jblas实现的相似度计算函数
  def cosineSimilarity(vec1: DoubleMatrix, vec2: DoubleMatrix): Double = {
    vec1.dot(vec2) / (vec1.norm2() * vec2.norm2())
  }
}
