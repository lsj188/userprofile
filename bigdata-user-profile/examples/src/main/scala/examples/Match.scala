package examples

/**
 * Created by lsj on 2017/7/12.
 * 模式匹配
 */
object TestMatch {

  /**
   * sealed 封闭类关键字，
   * 如果使用了封闭类做样本模式匹配，编译器会警告提示缺失的模式匹配组合
   **/
  sealed abstract class Expr

  case class Var(name: String) extends Expr

  case class Number(num: Double) extends Expr

  case class UnOp(operator: String, expr: Expr) extends Expr

  case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

  /**
   * 变量模式
   **/
  def desc(x: Any): Any = x match {
    case 5 => println("five")
    case Nil => println("empty")
    case _ => println("No Match" + x)
  }

  def main(args: Array[String]): Unit = {
    //    desc(5)
    //    desc(Nil)
    //    desc("test this!")
    import java.lang.Math.{E, PI}
    val pi = Math.PI
    /**
     * 常量模式
     **/
    val CL = E match {
      //      case E => "this is E:"+E
      case `pi` => "this is Pi:" + PI
      case _ => "this is " + E
    }
    //    print(CL)
    /**
     * 构造模式
     **/
    def testClass(expr: Expr): Any = expr match {
      case Var(name) => println("Var Class")
      case Number(num) => println("Number class")
      case UnOp(op, expr) => println("UnOp class")
    }
    testClass(Var("aa"));
    testClass(UnOp("-", Number(1)))
    /**
     * 序列模式
     **/
    def str(x: List[String]): List[String] = x match {
      case List("0", _*) => x
      case _ => x ::: ("error" :: Nil)

    }
    str(List("0", "test1", "test2")).foreach(print)
    println("=======================================")
    str(List("test3", "test1", "test2")).foreach(println)

    /**
     * 元组模式
     **/
    def tupleTest(expr: Any) = expr match {
      case (a, b, c) => println("a,b,c")
      case (a, b) => println(a, b)
      case _ => print("No match")
    }
    tupleTest((1, 2, 3));
    tupleTest((3, 1, 2, 3, 4))

    /**
     * 模式守卫
     **/
    def testClass2(expr: Expr): Any = expr match {
      case Var(name) => println("Var Class")
      case Number(num) => println("Number class")
      case UnOp(op, expr) => println("UnOp class")
      //      case BinOp("+",x,x) => BinOp("*",x,Number(2))
      case BinOp("+", x, y) if (x == y) => BinOp("*", x, Number(2))
    }
    /**
     * 模式重叠
     **/
    def simplifyAll(expr: Expr): Expr = expr match {
      case UnOp("-", UnOp("-", e)) => {
        print();
        simplifyAll(e)
      }
      case BinOp("+", e, Number(0)) => simplifyAll(e)
      case BinOp("*", e, Number(1)) => simplifyAll(e)
      case BinOp("/", e, Number(1)) => simplifyAll(e)
      case UnOp(op, e) => UnOp(op, simplifyAll(e))
      case BinOp(op, e, x) => BinOp(op, simplifyAll(e), simplifyAll(x))

    }

    /**
     * Option类型
     **/
    def show(x: Option[String]) = x match {
      case Some(x) => x
      case None => "?"
    }
    val map = Map("a" -> "a1", "b" -> "b1")
    println(map get "a", show(map get "a"))
    println(map get "c", show(map get "c"))
    /**
     * 模式在变量定义中
     **/
    val t = (123, "abc")
    val (a, b) = t
    println(a, b)

    /**
     * 模式用作偏函数的样本序列
     * * */
    val testFun: Option[Int] => Int = {
      case Some(x) => x
      case None => 0
    }
    println(Some(12),testFun(Some(12)))
    /**
     * for 模式匹配的使用
     * */
    val results=List(Some(12),Some(3),None,Some(11))
    for (Some(item) <-results) println(item)

    /**
     * List 模式匹配
     * */
    val list1=List(1,2,3,"aa")
    val List(al,bl,cl,dl)=list1
    val a1::b1::c1::d1=list1
    println(al,bl,cl,dl,a1,b1,c1,d1)
   }
}
