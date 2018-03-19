import scala.io._
import scala.collection.JavaConversions._
import org.apache.spark.SparkContext._
import org.apache.spark.rdd._
import org.apache.spark.{ SparkConf, SparkContext }

obj Lab7 {
	def main(args:Array[String]) : Unit = {
		val conf = new SparkConf().setAppName("Lab6")
		val sc = new SparkContext(conf)
	}
}