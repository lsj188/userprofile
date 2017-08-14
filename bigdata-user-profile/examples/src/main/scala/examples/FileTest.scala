package examples

/**
 * Created by lsj on 2017/7/17.
 */
object FileTest {
  import  scala.io.Source
  def getLines(fn:String):List[String]=Source.fromFile(fn).getLines().toList
  def getMax(fileLines:List[String]):String=fileLines.reduceLeft((a,b)=>if (a.length>b.length) a else b)
  def main(args: Array[String]) {
//    val filename="F:\\资料文件\\scala教程\\examples\\abstract-members\\AbstractCurrency.scala.err"
//    println(getMax(getLines(filename)))

  }
}
