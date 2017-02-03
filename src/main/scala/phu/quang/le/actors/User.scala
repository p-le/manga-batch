package phu.quang.le.actors

import akka.actor.Actor
import akka.actor.Props

object User {
  def props(name: String): Props = Props(classOf[User], name)
}

class User(name: String) extends Actor {
  
  def receive: Receive = {
    ???
  }
}