package examples

/**
 * Created by lsj on 2017/8/2.
 */
class OnStatusClass {
  var hour = 12
  var minute = 30
override def toString(): String =
{
  "hour="+hour+",minute="+minute
}
}

object RunOnStatusClass extends App
{
  val sclass=new OnStatusClass
  println("scalss.hour=",sclass.hour,"sclass.minute=",sclass.minute)
  println(sclass)
  sclass.hour=20
  println("scalss.hour=",sclass.hour,"sclass.minute=",sclass.minute)
  println(sclass.toString)
}