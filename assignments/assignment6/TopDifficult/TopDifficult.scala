object TopDifficult {

	def main(args:Array[String]) : Unit = {
		val courses = sc.textFile("../courses.txt")
		val courseMap = courses.map(line => (line.split(",")(0)))
		val courseDiff = courseMap.map{
				case (course, diff) => (diff, course)
		}.sortByKey(false)
		
		courseDiff.take(5).foreach(tuple => println(tuple._1 + " " + tuple._2))
	}
}