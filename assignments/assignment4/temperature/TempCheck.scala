import scala.io.Source
import scala.collection.mutable

object TempCheck {
	def printTop(s: String): Unit = {
		val tokens = s.split(" ")
		var temps = scala.collection.mutable.Set(-999)
		print(tokens(0) + " ")
		for (i <- 1 to tokens.length-1) temps += tokens(i).toInt
		println(temps.max)
	}

	def main (args: Array[String]): Unit = {
		val lines = Source.fromFile("input.txt").getLines.toList
		lines.foreach(printTop(_))
	}
}