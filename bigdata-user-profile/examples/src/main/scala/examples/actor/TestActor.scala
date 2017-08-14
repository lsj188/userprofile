package examples.actor

import examples.tools.DateTool

import scala.actors.Actor

object TestActor extends Actor {
  override def act(): Unit = {
    for (i <- 1 to 10) {
      println(DateTool.getNowDate() + ":This is TestActor")
      Thread.sleep(1000)
    }
  }
}

object TestActorB extends Actor {
  override def act(): Unit = {
    for (i <- 1 to 10) {
      println(DateTool.getNowDate() + ":This is TestActorB")
      Thread.sleep(1000)
    }
  }
}


object RunTestActor extends App {
  //  TestActor.start()
  //  TestActorB.start()

  //创建actor代码块无需启动

  import scala.actors.Actor._

  val testActor = actor(for (i <- 1 to 10) {
    println(DateTool.getNowDate() + ":This is TestActor val")
    Thread.sleep(1000)
  })


  //带消息处理Actor
  val actorMsg = actor(while (true) {
    receive { case msg => Thread.sleep(1000); println("this message is " + msg)}
  })

  //只接收int类型的消息
  val intActor = actor(while (true) {
    receive { case x: Int => println("this is int actor " + x)}
  })
  actorMsg ! "test1"
  actorMsg ! "test2"
  actorMsg ! "test3"
  intActor ! "test2"
  intActor ! 1234

  //将原生线程当作Actor
  self ! "Hello"
  self.receive { case x => println(x)}
  self.receiveWithin(1000) { case x => x} //等待1秒，如没有接收到就报TIMEOUT



}