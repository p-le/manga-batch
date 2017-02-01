package phu.quang.le

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._

import akka.{ NotUsed, Done }
import akka.util.ByteString
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import akka.actor.Props
import phu.quang.le.actors.MyActor
import scala.util.Random
import akka.stream.scaladsl.Tcp.IncomingConnection
import akka.stream.scaladsl.Tcp.ServerBinding

object Main extends App {
  implicit val system = ActorSystem("my-system")
  implicit val context = system.dispatcher
  implicit val materializer = ActorMaterializer()
      
//  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8888)
//  bindingFuture.onComplete { 
//    case Success(binding) =>
//      println(s"Server is listening on localhost:8888")
//    case Failure(e) =>
//      println(s"Binding failed with ${e.getMessage()}")
//      system.terminate()
//  }
  
  val myActor = system.actorOf(Props( new MyActor), name="my-actor")
  val scheduler = QuartzSchedulerExtension(system)
  val connections: Source[IncomingConnection, Future[ServerBinding]] = Tcp().bind("0.0.0.0", 1935);
  connections.runForeach {
    connection =>
      println(connection.remoteAddress)
  }
  
//  scheduler.schedule("Every10Seconds", myActor, MyActor.Factorial(Random.nextInt(30)))
}