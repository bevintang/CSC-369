object AvgDiff {

	def main(args:Array[String]) : Unit = {
		val students = sc.textFile("../students.txt")
		val courses = sc.textFile("../courses.txt")
		val grades = sc.textFile("../grades.txt")
		
		val studentMap = students.map(line => (line.split(",")(0).toInt, 
				(line.split(",")(1), (line.split(",")(2), line.split(",")(3) ))))
		val courseMap = courses.map(line => (line.split(",")(0)))
		val gradeMap = grades.map(line => (line.split(",")))

		// Order students: (id, name)
		val idStudent = students.map {case (id, (name, (address, num))) => (id, name)}
		val stuGrades = idStudent.join(gradeMap);	// (id, (student, (course, grade)))
		val courseStuGrade = stuGrades.map {case (id, (name, (course, grade))) => 
				(course, (name, grade))
		}

		// This yields: (course, ((name, grade), difficulty))
		val courseStuGradeDiff = courseStuGrade.join(courseMap)

		// This yeilds: (name, difficulty)
		val studentDiff = courseStuGradeDiff.map {
				case (course, ((name, grade), diff)) => (name, diff.toInt)
		}
		val combinedStudentDiff = studentDiff.combineByKey(
			(score) => (score, 1),
			(acc: (Int, Int), score) => (acc._1 + score, acc._2 + 1),
			(acc1: (Int, Int), acc2(Int, Int)) => 
				(acc1._1 + acc2._1, acc1._2 + acc2._2)).map({
					case (key, value) => (key, value._1 *1.0 / value._2)
				})
		)
		
		// Print each entry
		combinedStudentDiff.foreach(line => 
			println(line.split(",")(0) + line.split(",")(1)))
	}
}