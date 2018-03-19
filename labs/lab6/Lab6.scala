import scala.io._
import scala.collection.JavaConversions._
import org.apache.spark.SparkContext._
import org.apache.spark.rdd._
import org.apache.spark.{ SparkConf, SparkContext }

obj Lab6 {
	def main(args:Array[String]) : Unit = {
		val conf = new SparkConf().setAppName("Lab6")
		val sc = new SparkContext(conf)

		/*
			Import csv files
		*/
		// Order: lineID, salesID, prodID, quantity
		val lineitems = sc.textFile("lineitem.csv")
		// Order: salesID, date, time, storeID, custID
		val sales = sc.textFile("sales.csv")
		// Order: prodID, description, price
		val products = sc.textFile("products.csv")
		// Order: storeID, name, address, city, ZIP, state, phone#
		val stores = sc.textFile("stores.csv")

		/*
			Make general maps of files
		*/
		val lineitemsMap = lineitems.map(line => 
				(line.split(",")(0).toInt, 		// lineID
				(line.split(",")(1).toInt, 		// salesID
				line.split(",")(2).toInt,		// prodID
				line.split(",")(3).toInt)))		// quantitySold
		val salesMap = sales.map(sale =>
				(sale.split(",")(0).toInt,		// salesID
				sale.split(",")(3).toInt))		// storeID
		val productsMap = products.map(prod =>
				(prod.split(",")(0).toInt,		// prodID
				prod.split(",")(2).toDouble))	// price
		val storesMap = stores.map(store =>
				(store.split(",")(0).toInt,		// storeID
				store.split(",")(5)))			// state

		// Order: (salesID, state)
		val salesStateMap = salesMap.map{
			case(salesID, storeID) => (storeID, salesID)
		}.leftOuterJoin(storesMap).map{
			case(storeID, (salesID, None)) => (salesID, "")
			case(storeID, (salesID, Some(state))) => (salesID, state)
		}

		/*
			Join maps to get results (state, storeID, revenue)
		*/
		// Order: (salesID, revenue)
		val productSalesMap = lineitemsMap.map{
			case (lineID, (salesID, prodID, quantitySold)) =>
				(prodID, (salesID, quantitySold))
		}.leftOuterJoin(productsMap).
		map{
			case(prodID, ((salesID, quantitySold), None)) =>
				(salesID, 0.0)
			case(prodID, ((salesID, quantitySold), Some(price))) =>
				(salesID, quantitySold*price)
		}

		// Order: (salesID, storeID, revenue)
		val salesStoreRevMap = productSalesMap.leftOuterJoin(salesMap).map{
			case (salesID, (revenue, None)) => (0, (0, 0.0))
			case (salesID, (revenue, Some(storeID))) => (salesID, (storeID, revenue))
		}

		// Order: (state, storeId, revenue)
		val stateStoreRevMap = salesStoreRevMap.leftOuterJoin(salesStateMap).map{
			case (salesID, ((storeID, revenue), None)) => ("None " + storeID, revenue)
			case (salesID, ((storeID, revenue), Some(state))) => (state + storeID, revenue)
		}

		/*
			Print contents of stateStoreRevMap
		*/
		stateStoreRevMap.reduceByKey(_ + _).collect().sortBy(_._1).foreach(
			store => println(store._1 + ", " + store._2))

	}
}