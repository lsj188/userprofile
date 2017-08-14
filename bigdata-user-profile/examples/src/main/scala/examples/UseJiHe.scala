package examples

import scala.collection.immutable
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Created by lsj on 2017/8/1.
 */
object UseJiHe extends App {
  var listBuf = new ListBuffer[Any]
  //当为val类型时listBuf=listBuf:+5;listBuf=5+:listBuf不可用
  listBuf += 3;
  listBuf += 4;
  listBuf = listBuf :+ 5;
  listBuf = 5 +: listBuf
  println(listBuf, listBuf(0))

  val arrayBuf = new ArrayBuffer[Any]
  arrayBuf += 2;
  arrayBuf += 3;
  arrayBuf += "abc";
  println(arrayBuf)

  val queue = immutable.Queue[String]()
  //由于构造类为保护的，要使用伴生对象获得实例
  val queueItem = queue.enqueue("a").enqueue("b").enqueue(12).enqueue(List("a1", "b1"))
  //  queue+=12  //无+=
  println(queueItem, queue, queueItem.last, queueItem.length)
  val queue1 = new mutable.Queue[Any]
  //  queue1.enqueue("a").enqueue(12)  //返回类型为Unit
  queue1.enqueue("a", "b", "c", "d")
  queue1 += 12;
  println(queue1, queue1(0))

  val stack = new mutable.Stack[Any]()
  stack.push(1, 2, 3, 4, 9).push("5")
  println(stack, stack.pop(), stack.length)
  val str = "See spot Run.test,aa. fun"
  str.split("[ ,.!]").foreach(println)

  val set = mutable.Set[String]()
  set += "the";
  set += "is";
  set += "test";
  println(set)
  val map = mutable.Map.empty[String, Int]
  map("one") = 1;
  map("two") = 2;
  map("three") = 3;
  map += ("four" -> 4)
  println(map)
  val map1 = immutable.HashMap[String, Int]("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4, "five" -> 5, "six" -> 6)
  println(map1)

  val treeSet = mutable.TreeSet[String]() //
  treeSet ++= List("e", "g", "j", "d");
  println(treeSet)
  val treeMap = immutable.TreeMap[Int, String](4 -> "four", 5 -> "five", 6 -> "six", 1 -> "one", 2 -> "two", 3 -> "three") //TreeMap只有不可变对象
  println("treeMap", treeMap)

  /**
   * 自定义类型
   * */
  def makMap: mutable.Map[String, String] {def default(key: String): String} = {
    new mutable.HashMap[String, String] with mutable.SynchronizedMap[String, String]
    {
      override def default(key:String)="Why do you want to know"
    }
  }
  val makMap1=makMap
  makMap1++=Map("one" -> "1", "two" -> "2", "three" ->" 3")
  println(makMap1("one"),makMap1("four"))

}
