package phu.quang.le.actors

import akka.actor.Actor

object MyActor {
  case object Hello
}

class MyActor extends Actor {
  import MyActor._
  def receive: Actor.Receive = {
    case Hello => println("Hello")
  }
}