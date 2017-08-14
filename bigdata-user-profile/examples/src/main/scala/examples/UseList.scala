package examples

/**
 * Created by lsj on 2017/8/1.
 *
 */
object UseList extends App {
  /**
   * sort
   **/
  def lsort(xs: List[Int]): List[Int] = xs match {
    case List() => List[Int]()
    case x :: xs1 => insert(x, lsort(xs1))
  }

  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List() => List(x)
    case y :: xs1 => if (x <= y) x :: xs else y :: insert(x, xs1)
  }

  /**
   * 反转
   **/
  def rev(xs: List[Any]): List[Any] = xs match {
    case List() => List()
    case x :: xs1 => rev(xs1) ::: List(x)
  }

  /**
   * add
   **/
  def append[T](left: List[T], right: List[T]): List[T] = left match {
    case List() => right
    case x :: xs => x :: append(xs, right)
  }

  /**
   * 归并排序
   **/
  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
    def merge(xs: List[T], xy: List[T]): List[T] = (xs, xy) match {
      case (Nil, _) => xy
      case (_, Nil) => xs
      case (x :: xs1, y :: xy1) => if (less(x, y)) x :: merge(xs1, xy) else y :: merge(xs, xy1)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, yy) = xs.splitAt(n)
      merge(msort(less)(ys), msort(less)(yy))
    }
  }
  def msort1[T](xs: List[T])(less: (T, T) => Boolean): List[T] = {
    def merge(xs: List[T], xy: List[T]): List[T] = (xs, xy) match {
      case (Nil, _) => xy
      case (_, Nil) => xs
      case (x :: xs1, y :: xy1) => if (less(x, y)) x :: merge(xs1, xy) else y :: merge(xs, xy1)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, yy) = xs.splitAt(n)
      merge(msort(less)(ys), msort(less)(yy))
    }
  }
  /**
   * 折叠操作/:,:\
   **/
  def sum(xs: List[Int]): Int = (0 /: xs)(_ + _)

  def sum1(xs: List[Int]): Int = (xs :\ 0)(_ + _)

  def product(xs: List[Int]): Int = (1 /: xs)(_ * _)

  def flatLeft[T](xs: List[List[T]]) = (List[T]() /: xs)(_ ::: _)

  def flatRight[T](xs: List[List[T]]) = (xs :\ List[T]())(_ ::: _)


  val list1 = List(234, 324, 234, 52, 353, 45, 3245, 25, 642, 66, 7, 35, 735, 753, 67, 357, 653, 484, 684)
  val doubleList = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
  val threeList=List(List(List(1,2,3)),List(List(1),List(2)))
  //  println(list1.productIterator.next())  //productIterator获取迭代器，老版本为elements方法
//  println(List.fill(5)("hello")) //生成多个相同元素列表，老版本对应make方法List.make(5,"hello")
//  println(doubleList.flatten)     //将内嵌List展开，等价println(doubleList.flatMap(for(a <- _) yield a))
  /**
   * 类型推断，需要给柯里化的方法指定类型,println(msort(_<_)(list1))没有指定类型会报错，或者使用这种形式println(msort((x:Int,y:Int)=>x<y)(list1))
   *println(msort1(list1)(_<_))，这个也是可以的，因为在第一个变量传入时已经确定了类型为Int
   * println(msort1(List("a","c","b","f","e","d"))(_<_))
   * */
  println(msort1(List("a","c","b","f","e","d"))(_<_))
//  println(msort[Int](_<_)(list1))
//  println(msort1(list1)(_<_))


 //  lsort(list1).foreach(println)
  //  list1.reverse.foreach(println)
  //  rev(list1).foreach(println)
  //  println("List.tail",list1.tail)  //返回除第一个元素的List
  //  println("List.init",list1.init)  //返回除最后一个元素的List
  //  msort((x: Int, y: Int) => x < y)(list1).foreach(println)
  println("sum=", sum(list1), "sum1=", sum1(list1), "product=", product(list1))
  println("flatLeft=", flatLeft(doubleList), "flatRight=", flatRight(doubleList))
  println("=======================================")

  //  append(List(1,2,3),List(4,5,6)).foreach(println)

  /**
   * function use
   **/
  //  val strBuf = new StringBuilder
  //  list1.addString(strBuf)
  //  strBuf.foreach(println)
  //  println(list1.toArray)

}
