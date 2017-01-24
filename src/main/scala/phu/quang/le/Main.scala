package phu.quang.le

import akka.actor.ActorSystem
import akka.actor.Props
import phu.quang.le.actors.MyActor
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension


object Main extends App {
  implicit val system = ActorSystem("my-system");
  
  class Application(val actorSystem: ActorSystem) extends Configuration {
    
  }
  val myActor = system.actorOf(Props[MyActor])
  val scheduler = QuartzSchedulerExtension(system)
  
  
  scheduler.schedule("Every10Seconds", myActor, MyActor.Hello)
}