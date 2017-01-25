package phu.quang.le

import akka.actor.ActorSystem
import akka.actor.Props
import phu.quang.le.actors.MyActor
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver
import scala.collection.JavaConverters._
import phu.quang.le.utils.Configuration
import phu.quang.le.utils.DBConfiguration

object Main extends App with DBConfiguration {
  implicit val system = ActorSystem("my-system");
  implicit val context = system.dispatcher
  
  val myActor = system.actorOf(Props[MyActor])
  val scheduler = QuartzSchedulerExtension(system)
  
  scheduler.schedule("Every10Seconds", myActor, MyActor.Hello)
}