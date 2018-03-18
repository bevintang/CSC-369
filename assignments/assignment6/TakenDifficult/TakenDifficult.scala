
object TakenDifficult {
	def findMax(courses:List[String]) : Int = {
		var max = -1;
		var temp = 0;
		for (line <- courses) {
			temp = Integer.parseInt(line.split(",")(1))
			if (temp > max)
				max = temp
		}
		return max;
	}

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
				case (course, ((name, grade), diff)) => (name, diff)
		}

		// Print the students who have taken the most difficult course
		val maxDiff = findMax(courses)
		for (line <- studentDiff){
			if (line.contains(maxDiff)){
				println(line(0))
			}
		}
	}
}