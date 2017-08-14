package examples

/**
 * Created by lsj on 2017/7/11.
 */
object Timer1 {
  def oneSecond(): Unit=
  {
//    while (true){
      println("exec function!")
      Thread.sleep(1000)
      oneSecond()

  }
  def main(args: Array[String]): Unit=
  {
    oneSecond()
  }
}
