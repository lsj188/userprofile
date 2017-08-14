package examples

/**
 * Created by lsj on 2017/7/28.
 */
object NimingFunTest {
  def main(args: Array[String]) {
    //args.filter((arg:String)=>arg.startsWith("l")).foreach((arg:String)=>println(arg))
    def a = (x: Int) => {
      println("a:" + x);
      x
    }
    def b(x: Int) = {
      println("b:" + x);
      x
    }
    val aa = (x: Int) => {
      println("aa" + x);
      x
    }
    println("aa=", aa(12))
    val a1 = a(3)
    val b1 = b(3)
    println("a1=", a1, "b1=", b1)


    /**
     * 函数作为参数传递
     **/
    def bb(b: Tuple2[Int, Int] => Unit, z: Tuple2[Int, Int]) {
      b(z)
    }
    val f = (x: Tuple2[Int, Int]) => println("f", x._1, x._2)
    val z = Tuple2(1, 2)
    bb(f, z)
    bb((x: Tuple2[Int, Int]) => println("niming" + x._1, x._2), (1, 2))


    /**
     * _*将集合中元素作为单个传递
     **/
    Array(1 to 10: _*).map { item: Int => item * 2}.foreach { x => println(x)}
    println("========================================================")
    /**
     * 函数闭包
     **/
    def funcResult1(message: String) = (name: String) => println(message + " : " + name)
    val funrJava = funcResult1("Java")
    val funrScala = funcResult1("scala")
    funrJava(" is good");
    funrScala(" is gooo to")

  }
}
