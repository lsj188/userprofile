package examples.actor

import scala.actors.Actor
import scala.actors.Actor._

/**
 * Created by lsj on 2017/8/9.
 */
object Actor1 extends Actor {
  override def act {
    def emoteLater() {
      val mainActor = self
      actor {
        Thread.sleep(1000)
        mainActor ! "Emote"
      }
    }

    var emoted = 0
    emoteLater()

    loop {
      react {
        case "Emote" =>
          println("I'm acting!")
          emoted += 1
          if (emoted < 5)
            emoteLater()
        case msg =>
          println("Received: "+ msg)
      }
    }
  }
}

object RunActor1 extends App
{
  Actor1.start()
  Actor1!"Hi here"
}