package examples

/**
 * Created by lsj on 2017/7/14.
 * 模式匹配和case类
 */
case class Person3(first:String, last:String, age:Int);
object MatchAndCase
{
  def main(args : Array[String]) : Unit =
  {
    val ted = Person3("Ted", "Neward", 37)
    val amanda = Person3("Amanda", "Laucher", 27)
    System.out.println(process(ted))
    System.out.println(process(amanda))
  }
  def process(p : Person3) =
  {
    "Processing " + p + " reveals that" +
      (p match
      {
        case Person3(_, _, a) if a > 30 =>
          " they're certainly old."
        case Person3(_, "Neward", _) =>
          " they come from good genes...."
        case Person3(first, last, ageInYears) if ageInYears > 17 =>
          first + " " + last + " is " + ageInYears + " years old."
        case _ =>
          " I have no idea what to do with this person"
      })
  }
}