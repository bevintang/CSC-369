import scala.io._
import scala.collection.JavaConversions._
import scala.collection.immutable.TreeMap

/* 
	Read in files and print the sales (in dollars) of each store (ordered by state).
	Output: state, storeID, sales (dollars)
	Sample: AL, 123, 34453
 */

object Lab5 {
	def main (args: Array[String]) : Unit = {
		/*
			Import all files
		*/
		// Order: lineID, salesID, prodID, quantity
		val lineitems = Source.fromFile("lineitem.csv").getLines.toList
		// Order: salesID, date, time, storeID, custID
		val sales = Source.fromFile("sales.csv").getLines.toList
		// Order: prodID, description, price
		val products = Source.fromFile("products.csv").getLines.toList
		// Order: storeID, name, address, city, ZIP, state, phone#
		val stores = Source.fromFile("stores.csv").getLines.toList

		/* 
			Map files to store usable data 
		*/
		// Entry: (salesID, prodID, quantitySold)
		val transactions = lineitems.map(line => 
				(line.split(",")(1).toInt,
				line.split(",")(2).toInt,
				line.split(",")(3).toInt))

		// Entry: (salesID, storeID)
		val salesStoreMap = sales.map(sale =>
				(sale.split(",")(0).toInt,
				sale.split(",")(3).toInt)).toMap

		// Entry: (prodID, price)
		val prodPriceMap = products.map(prod =>
				(prod.split(",")(0).toInt,
				prod.split(",")(2).toDouble)).toMap

		// Entry: (storeID, state)
		val storeStateMap = stores.map(store =>
				(store.split(",")(0).toInt,
				store.split(",")(5).toString)).toMap

		/*
			Go through each lineitem and fill a TreeMap with the stores and revenue
		*/
		var treemap = new java.util.TreeMap[String, Double]()
		transactions.foreach{
			case(salesID:Int, prodID:Int, quantitySold:Int) =>
				val storeID = salesStoreMap.getOrElse(salesID, -1)
				val state = storeStateMap.getOrElse(storeID, "N/A")
				val cost = prodPriceMap.getOrElse(prodID, 0).toString.toDouble
				val revenue = quantitySold.toInt * cost
				val key = state + "," + storeID

				// Add to TreeMap
				if (treemap.contains(key)) {
					val currentRevenue = treemap.getOrElse(key, 0.0).toString.toDouble
					treemap.put(key, currentRevenue + revenue)
				}
				else {
					treemap.put(key, revenue)
				}
		}

		/*
			Print the results
		*/
		for (e <- treemap.entrySet()){
			val keyComponents = e.getKey.split(",")
			val value = e.getValue
			println(keyComponents(0) + ", " + keyComponents(1) + f", $value%.2f")
		}

	}
}