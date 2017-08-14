package examples

/**
 * Created by lsj on 2017/8/8.
 */
class ZhuJie {
  //废弃注解  @deprecated  打上标记，非不可用
  @deprecated def test = println("this is test @deprecated")

  //易变字段 @volatile
  @volatile var a=123

  //二进制序列化 @SerialVersionUID  @serializable
  @SerialVersionUID(123)
  val b=123

  //非序列化 @transient
  @transient
  val c=123

  //自动生成get,set方法  @scala.reflect.BeanProperty  找不到
//  @scala.reflect.BeanProperty var property=0

  //不检查@unchecked
  val int=1
  @unchecked val double=int

}

object RunZhuJie extends App
{

}
