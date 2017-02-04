package phu.quang.le.actors

import redis.actors.RedisSubscriberActor
import java.net.InetSocketAddress
import redis.api.pubsub._
import akka.actor.Props

object SubscribeActor {
  def props(channels: Seq[String], patterns: Seq[String]): Props = 
    Props(classOf[SubscribeActor], channels, patterns)
}

class SubscribeActor(channels: Seq[String] = Nil, patterns: Seq[String] = Nil)
  extends RedisSubscriberActor(
    new InetSocketAddress("localhost", 6379),
    channels,
    patterns,
    onConnectStatus = connected => { println(s"connected: $connected")}
  ) {

  def onMessage(message: Message) {
    println(context.parent.path.toString())
    context.parent ! User.MessageReceived(message.data.utf8String)
  }

  def onPMessage(pmessage: PMessage) {
    println(pmessage)
    context.parent ! User.MessageReceived(pmessage.data.utf8String)
  }
}