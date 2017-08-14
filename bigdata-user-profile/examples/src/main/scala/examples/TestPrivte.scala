package examples


/**
 * Created by lsj on 2017/7/28.
 */
object TestPrivte {

  class Inner {
    private def f: Unit = {
      println("f")
    }

    protected val a = 3

    class InnerMost {
      f
      println(a)
    }

  }

  class Innert extends Inner {
    print(this.a)
  }

}
object TestPrivte1 extends App
{
   new TestPrivte.Innert
}