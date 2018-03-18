import scala.io.Source

object Divisible {
	def mod3(s: String): Unit = {
		val num = s.toInt
		if (num % 3 == 0) println(num) 
	}

	def printmod3(s: String): Unit = {
		val numbers = s.split(" ")
		numbers.foreach(mod3(_))
	}

	def main(args: Array[String]): Unit = {
		val lines = Source.fromFile("input.txt").getLines.toList
		lines.foreach(printmod3(_))
	}
}