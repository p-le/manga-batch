package phu.quang.le.actors

import akka.actor._
import java.util.UUID

object ChatRoom {
  //commands
  case class Join(userName: String)
  case object Leave
  
  //events
  case class UserJoined(users: User, message: Array[String])
  case class UserLeft()
  
  def props(name: String): Props = Props(classOf[ChatRoom], name)
}

class ChatRoom(name: String) extends Actor {
  import ChatRoom._
  
  def receive: Actor.Receive = {
    case Join(userName) =>
      context.actorOf(User.props(userName))   
  }
}