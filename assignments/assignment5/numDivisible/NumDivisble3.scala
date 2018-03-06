import scala.io.Source

object NumDivisble3 {
	def main (args: Array[String]) : Unit = {
		val lines = Source.fromFile("input.txt").getLines.toList
		val integerMap = lines.flatMap(_.split("\\s+"))
		val div3Map = integerMap.filter(Integer.parseInt(_)%3 == 0)
		div3Map.distinct.foreach(x => println(x + " appears " + div3Map.count(_ == x) + " times"))
	}
}