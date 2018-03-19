import scala.io._
import scala.collection.JavaConversions._
import org.apache.spark.SparkContext._
import org.apache.spark.rdd._
import org.apache.spark.{ SparkConf, SparkContext }

obj Lab7 {
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
				(line.split(",")(1).toInt, 		// salesID
				(line.split(",")(2).toInt,		// prodID
				line.split(",")(3).toInt)))		// quantitySold
		val salesMap = sales.map(sale =>
				(sale.split(",")(0).toInt,			// salesID
				(sale.split(",")(1).split("-")(2) + "-" +
				sale.split(",")(1).split("-")(1),	// date (YYYY-MM)
				sale.split(",")(3).toInt)))			// storeID
		val productsMap = products.map(prod =>
				(prod.split(",")(0).toInt,		// prodID
				prod.split(",")(2).toDouble))	// price

		/*
			Join maps to create the Map or the result
		*/
		// Order: (storeID, "name, city")
		val storesMap = stores.map(store =>
				(store.split(",")(0).toInt,		// storeID
				store.split(",")(1) + ", " +	// name
				store.split(",")(3)))			// city
		// Order: (salesID, (date, "name, city"))
		val salesNameCityMap = salesMap.map{
			case(salesID, (date, storeID)) => (storeID, (salesID, date))
		}.leftOuterJoin(storesMap).map{
			case(storeID, ((salesID, date), None)) => (salesID, (date, ""))
			case(storeID, ((salesID, date), Some(location))) => (salesID, (date, location))
		}
		// Order: (salesID, revenue)
		val productSalesMap = lineitemsMap.map{
			case (salesID, (prodID, quantitySold)) =>
				(prodID, (salesID, quantitySold))
		}.leftOuterJoin(productsMap).map{
			case(prodID, ((salesID, quantitySold), None)) =>
				(salesID, 0.0)
			case(prodID, ((salesID, quantitySold), Some(price))) =>
				(salesID, quantitySold*price)
		}
		// FINAL Order: (date, (location, revenue))
		val dateLocRevMap = salesNameCityMap.leftOuterJoin(productSalesMap).map{
			case(salesID, ((date, loc), None)) => (date + ", " + loc, 0.0)
			case(salesID, ((date, loc), Some(rev))) => (date + ", " + loc, rev)
		}.reduceByKey(_ + _).map{	// aggregate revenue per location
			case(dateLoc, rev) => 
				(dateLoc.split(", ")(0), 
				(dateLoc.split(", ")(1) + "," + dateLoc.split(", ")(2),
				rev))
		}

		/*
			Print Results
		*/
		for (line <- dateLocRevMap.groupByKey.collect.sortBy(_._1)){
			val date = line._1
			val stores = line._2.toList.sortBy(-_._2).take(10)	// descending revenue
			print(date + " ")
			stores.foreach(print + " ")
			println("\n")
		}
	}
}