import scala.io._
import org.apache.spark.SparkContext._

object EmployeeDepartment {
    def main(args: Array[String]) {
        val Employees = Source.fromFile("Employees").getLines.toArray;
        val Departments = Source.fromFile("Departments").getLines.toArray; 
        
        val result = Employees.cartesian(Departments)
        result.filter(case(Emp, Dep) => Emp.split(",")(1) == Dep.split(",")(0))
        val printResult = result.map(case(Emp, Dep) => (Emp.split(",")(1), Dep.split(",")(0)))
        printResult.foreach(x=> println(x(0) + ", " + x(1))) 
    }
}