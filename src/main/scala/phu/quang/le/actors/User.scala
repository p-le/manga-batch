package phu.quang.le.actors

import akka.actor._
import phu.quang.le.utils.Configured
import redis.RedisClient
import akka.event.Logging
import scala.concurrent.ExecutionContext.Implicits._

object User {
  case class Connected(webSocket: ActorRef)
  
  case class MessageSent(message: String)
  case class MessageReceived(message: String)
  
  def props: Props = Props(classOf[User])
}

class User extends Actor with Configured {
  import User._
  
  private val logger = Logging(context.system, this)
  
  def receive: Receive = {
    case Connected(webSocket) =>
      val subscribe = context.actorOf(
          SubscribeActor.props(Seq("dashboard"), Nil)
            .withDispatcher("rediscala.rediscala-client-worker-dispatcher"))
      context.become(connected(webSocket))
  }
  
  def connected(webSocket: ActorRef): Receive = {
     case received: MessageReceived =>
       logger.info("Received Message: {}", received)
       webSocket ! received
     case sent: MessageSent => 
       logger.info("Sent Message: {}", sent)
       val redis = configured[RedisClient]
       redis.publish("dashboard", sent.message)
  }
}