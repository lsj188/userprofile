package examples.actor

import java.net.{InetAddress, UnknownHostException}

import scala.actors.Actor

/**
 * 通过重用线程获得更好的性能，用react()替换receive()函数
 **/
object NameResolver extends Actor {
  override def act(): Unit = {
    react {
      case (name: String, actor: Actor) =>
        actor ! getIp(name)
        act()
      case "Exit" =>
        println("naem resolver exiting!")
      case msg =>
        println("Unhandled Message " + msg)
        act()
    }
  }

  def getIp(name: String): Option[InetAddress] = {
    try {
      Some(InetAddress.getByName(name))
    } catch {
      case _: UnknownHostException => None
    }
  }
}

object RunNameResolver extends App
{
  //重用线程
//  NameResolver.start()
//  NameResolver!("www.baidu.com",self)
//  self.receiveWithin(0){case x=> println(x)}
//  NameResolver!("wwww.baidu.com",self)
//  self.reactWithin(0){case x=> println(x)}
//  NameResolver!("Exit",self)
//  self.reactWithin(0){case x=> println(x)}

}