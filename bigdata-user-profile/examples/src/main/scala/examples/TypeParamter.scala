package examples

/**
 * 变化型注解：Scala编译器会把类或特质结构体的所有位置分类为正、负、中立；所谓的“位置”是指类或特质的结构体内可能会用到类型参数的地方。
 * 注解了+号的类型参数只能被用在正的位置上，而注解了-号的类型参数只能用在负的位置上，没有变化型注解的类型参数可以用于任何位置。
 * 处于声明类的最顶层被划分为正的位置，默认情况下，更深的内嵌层位置的分类会与它的外层一致，不过仍有一些列外会改变分类；
 * 方法值参数位置是方法外部位置的翻转类别，这里正的位置翻转为负的，负的翻转为正的，而中位置仍为中性。
 * 见教材P290，Cat样例
 **/


/**
 * 1） U >: T    这是类型下界的定义，也就是U必须是类型T的父类(或本身，自己也可以认为是自己的父类)。
 * 2) S <: T     这是类型上界的定义，也就是S必须是类型T的子类（或本身，自己也可以认为是自己的子类)。
 *
 **/
class Cell[+T](init: T) {
  //当加上[this]时会忽略变化类型的转变，如果去掉就会报错
  private[this] var current = init

  def get = current

  //会报类型协变错识
  //  def set(x: T) {
  //    new Cell[T](x)
  //  }

  //因为负号位，需要下界符号进行参数类型逆变
  def set[U >: T](x: U) = {
    //参数类型改变后，X类型U为current类型T的父类，所以是不能直接赋值的，需要返回新的对象
    //    current=x
    new Cell[U](x)
  }
}

/**
 *当Person构造参数未加val标识时编译报错（value lastName is not a member of test.Person）
 **/
class Person(val fristName: String, val lastName: String) extends Ordered[Person] {
  override def compare(that: Person): Int = {
    val lastNamecompartion = lastName.compareToIgnoreCase(that.lastName)
    if (lastNamecompartion != 0)
      lastNamecompartion
    else
      fristName.compareToIgnoreCase(that.fristName)
  }

  override def toString = "fristName=" + fristName + "lastName=" + lastName
}

/**
 * <:   下界用例
 **/
object OrderMergerSort {
  /**
   * 传入的参数类型必须为Ordered的子类
   **/
  def orderMergerSort[T <: Ordered[T]](xs: List[T]): List[T] = {
    def merger(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) => {
        if (x < y) x :: merger(xs1, ys)
        else y :: merger(xs, ys1)
      }
    }
    val middle = xs.length / 2
    if (middle == 0) xs
    else {
      val (xs1, ys1) = xs.splitAt(middle)
      merger(orderMergerSort(xs1), orderMergerSort(ys1))
    }

  }
}

class Publication(val title: String)

class Book(title: String) extends Publication(title)

object Library {
  val books: Set[Book] =
    Set(
      new Book("Programming in Scala"),
      new Book("Walden")
    )

  def printBookList(info: Book => AnyRef) {
    for (book <- books) println(info(book))
  }
}


class Queue[+T] private(private[this] var leading: List[T], private[this] var trailing: List[T]) {
  private def mirror() =
    if (leading.isEmpty) {
      while (!trailing.isEmpty) {
        leading = trailing.head :: leading
        trailing = trailing.tail
      }
    }

  def head: T = {
    mirror()
    leading.head
  }

  def tail: Queue[T] = {
    mirror()
    new Queue(leading.tail, trailing)
  }

  def append[U >: T](x: U) =
    new Queue[U](leading, x :: trailing)
}

object RunTypeParamter extends App {
  /**
   * 参数类型
   * 逆变(Book->Publication):Library.printBookList入了getTitle，getTitle参数为Publication函数体只能访问其参数P,Book为Publication的子类，
   * 而在Publication里面声明的函数在其子类同样有效，所以是没有问题的，这也是逆变的意义所在。
   * 协变(Publication=>String->Book=>AnyRef):getTitle函数返回String类型，printBookList参数（Book=>AnyRef)返回类型为AnyRef，
   * 由于Function1定义的返回类型定义为协变,Stringo类型为AnyRef的子类，println()会调用对象的toString方法，
   * 这个方法对于所有AnyRef的子类都有效，所以也没有问题，这也是协变的意义所在
   *
   **/
  def getTitle(p: Publication): String = p.title

  Library.printBookList(getTitle)

  val c1 = new Cell[String]("abc")
  val c2: Cell[AnyRef] = c1
  val c3 = c2.set(1)
  val s: String = c1.get
  println("c1=", c1.get, "c2=", c2.get, "c3=", c3.get)

  //测试下界使用
  val persons = List(new Person("test", "1"),
    new Person("test", "5"),
    new Person("test", "2"),
    new Person("test", "4"),
    new Person("test", "3"))
  val persons1 = OrderMergerSort.orderMergerSort(persons)
  persons1.foreach(println)
  println("=======================================")
//  val strs=List("sfd","dfsfa","dfsfa","2df","lkjlkj")
//  OrderMergerSort.orderMergerSort(strs).foreach(println)  //编译会报错，因为String不是Ordered的子类

}