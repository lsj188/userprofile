package examples

/**
 * Created by lsj on 2017/7/13.
 */
object ExecMain extends App {
  println("=============抽取器用法==============")
  object ExpandedEmail {
    //    def apply(name:String,doms:String*):String=name+"@"+doms.mkString(".")
    def unapplySeq(email: String): Option[(String, Seq[String])] = {
      val parts = email.split("@")
      if (parts.length == 2)
        Some(parts(0), parts(1).split("\\.").reverse.toSeq)
      else None
    }
  }
  val s ="luoshijun@163.com.cn"
  val ExpandedEmail(name,topdom,subdom @ _*)=s
    println("name="+name,"topdom="+topdom,"subdom="+subdom.mkString("."))

}
