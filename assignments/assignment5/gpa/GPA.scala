import scala.io._

object GPA {
    val gradeMap = Map("A" -> 4, "B" -> 3, "C" -> 2, "D" -> 1, "F" -> 0)
    def calcGPA(line:String) : Unit = {
        var grades = line.split(", ",3)(2).split(", ")
        val gradeInfo = grades.aggregate((0, 0)) (
            (x,y) => (x._1 + gradeMap(y.split(" ")(0)), x._2 + 1),
            (x,y) => (x._1 + y._1, x._2 + y._2))

        val lineDelim = line.split(",")
        println(lineDelim(0) + "," + lineDelim(1) + ", " + gradeInfo._1 * 1.0 / gradeInfo._2)
    }

    def main(args: Array[String]) : Unit = {
        val lines = Source.fromFile("input.txt").getLines.toList
        lines.foreach(calcGPA)
    }
}