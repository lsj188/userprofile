package examples

/**
 * Created by lsj on 2017/7/11.
 */
object Timer2 {
  def oneSecond(callback:()=>Unit)
  {
    while (true){
      callback()
      Thread.sleep(1000)
    }
  }
  def sum(a:Int,b:Int)= {
    a+b
  }
  def funtext():Unit={println("parameter is function!")}

  def main(args: Array[String])
  {
//    funtext
//    println("------------------------------")
//    oneSecond(funtext )

    print(sum(10,2))
  }
}
