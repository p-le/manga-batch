package phu.quang.le.actors

import akka.actor.Actor
import phu.quang.le.utils.Configured
import reactivemongo.api.DefaultDB

object MyActor {
  case object Hello
}

class MyActor extends Actor with Configured {
  import context.dispatcher
  import MyActor._
  def receive: Actor.Receive = {
    case Hello =>
      val connection = configured[DefaultDB]
      connection.collectionNames.map { println(_) }
  }
}