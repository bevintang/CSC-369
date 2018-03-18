import scala.io._
import org.apache.spark.SparkContext._

object EmployeeDepartment {
    def main(args: Array[String]) {
        val emps = Source.fromFile("employees.txt").getLines.toList;
        val deps = Source.fromFile("departments.txt").getLines.toList; 

        val rddEmps = sc.parallelize(emps)
        val rddDeps = sc.parallelize(deps)
        val rddCart = rddEmps.cartesian(rddDeps)

        val matches = rddCart.filter {x => x._1.split(",")(1) == x._2.split(",")(0)}
        matches.collect.foreach(x => println(x._1.split(",")(0) + ", " + x._2.split(",")(1)))
    }
}