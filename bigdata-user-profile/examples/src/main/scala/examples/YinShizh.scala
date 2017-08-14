package examples


/**
 * Created by lsj on 2017/8/4.
 * 隐式转换(implicit )
 * scala.Predef  中定义了一些类型及类型之间转换的方法
 */
class YinShizh {

}

case class Person6(val name: String, val age: Int)

case class Person5(val name: String, val age: Int)

/**
 * 隐式参数
 **/
class PreferredPrompt(val preference: String)

//参数加val,var变为成类成员变量
class PreferredDrink(val preference: String)

object Greeter {
  def greet(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink) {
    println("Welcome," + name + ".The systme is ready.")
    println(prompt.preference, drink.preference)
  }
}

object YinShizh extends App {

  implicit def double2Int(x: Double): Int = x.ceil.toInt

  val i: Int = 3.5
  println(i)

  //美元转日元
  implicit def dollar2Yen(x: US.Dollar): Japan.Yen = Japan.Yen from x

  val dollar = US.Dollar * 100
  val jpy = Japan.Yen
  println(dollar + (US.Dollar from jpy))
  println(jpy + dollar)


  /**
   * 隐式参数
   **/
  val bobsPrompt = new PreferredPrompt("relax>")
  Greeter.greet("lsj")(bobsPrompt, new PreferredDrink("drink"))

  object JosePrps {
    implicit val prompt = new PreferredPrompt("lsj1relax>")
    implicit val drink = new PreferredDrink("drinkrelax>")
  }

  //将prompt引入到作用域才可以，否则报错，必须有implicit才可以这么用

  import JosePrps._

  Greeter.greet("lsj1")

  /**
   * 带有隐式转换参数的函数
   **/
  def maxListItem[T](elements: List[T])(implicit order: T => Ordered[T]): T = elements match {
    case List() => throw new IllegalArgumentException("Empty List!")
    case List(x) => x
    case x :: rest => {
      //      val maxItem = maxListItem(rest)(order)// order 可省略，让其隐式转换
      val maxItem = maxListItem(rest) // order 可省略，让其隐式转换
      //      if (order(x) > maxItem) x else maxItem//order 可省略，让其隐式转换
      if (x > maxItem) x else maxItem //order 可省略，让其隐式转换

    }
  }

  /**
   * 也可以使用带视界的参数函数实现
   * [T <% Ordered[T]] 只要T可以隐式转换为Ordered[T]都可传入
   **/
  def maxListItem1[T <% Ordered[T]](elements: List[T]): T = elements match {
    case List() => throw new IllegalArgumentException("Empty List!")
    case List(x) => x
    case x :: rest => {
      //      val maxItem = maxListItem(rest)(order)// order 可省略，让其隐式转换
      val maxItem = maxListItem1(rest) // order 可省略，让其隐式转换
      //      if (order(x) > maxItem) x else maxItem//order 可省略，让其隐式转换
      if (x > maxItem) x else maxItem //order 可省略，让其隐式转换

    }
  }

  println("maxListitem=" + maxListItem(List(1, 2, 24, 42, 524, 534, 5, 32465, 26, 65, 34, 6532, 457, 3, 76, 367, 36, 12)))
  println("maxListitem1=" + maxListItem1(List(1, 2, 24, 42, 524, 534, 5, 32465, 26, 65, 34, 6532, 457, 3, 76, 367, 36, 12)))
  val persons = List(Person5("test3", 3), Person5("test2", 2), Person5("test11", 11), Person5("test10", 10), Person5("test5", 5))
  implicit def person6ToOrdered(person6: Person5): Ordered[Person5] = {
    new Person5(person6.name, person6.age) with Ordered[Person5] {
      override def compare(that: Person5): Int = this.age.compareTo(that.age)
    }
  }
  println("maxPer1="+maxListItem(persons))
  println("maxPer2="+maxListItem(persons))



}

