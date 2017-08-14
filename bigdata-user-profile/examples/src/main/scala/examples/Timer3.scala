package examples

/**
 * Created by lsj on 2017/7/12.
 */
object Timer3 {
  def oneSecond(callback:()=>Unit):Unit={
    while (true){
      callback()
      Thread.sleep(1000)
    }
  }
  def main(args: Array[String])={
    oneSecond(()=>{println("anonymous function!")})
  }
}
