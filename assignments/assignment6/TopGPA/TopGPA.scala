object TopGPA {
	def main(args:Array[String]) : Unit = {
		val gradeMap = Map("A" -> 4, "B" -> 3, "C" -> 2, "D" -> 1, "F" -> 0)
		val students = sc.textFile("../students.txt")
		val grades = sc.textFile("../grades.txt")
		
		val studentMap = students.map(line => (line.split(",")(0).toInt, 
				(line.split(",")(1), (line.split(",")(2), line.split(",")(3) ))))
		val gradesMap = grades.map(line => (line.split(",")))

		// Order students: (id, name)
		val idStudent = students.map {case (id, (name, (address, num))) => (id, name)}
		val studentJoin = idStudent.join(gradeMap);	// (id, (student, (course, grade)))

		// Order: (name, grade-point)
		val stuGrades = studentJoin.map {case (id, (name, (course, grade))) => 
				(name, gradeMap(grade))
		}

		val combinedStudentGpa = stuGrades.combineByKey(
			(score) => (score, 1),
			(acc: (Int, Int), score) => (acc._1 + score, acc._2 + 1),
			(acc1: (Int, Int), acc2(Int, Int)) => 
				(acc1._1 + acc2._1, acc1._2 + acc2._2)).map({
					case (key, value) => (key, value._1 *1.0 / value._2)
				})
		)
		
		// Print each entry
		combinedStudentDiff.sortBy(_._2, false).collect().
			foreach(line => println(line._1 + " " + line._2))
	}
}