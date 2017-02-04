package phu.quang.le

import akka.actor._
import akka.stream._
import akka.http.scaladsl._
import akka.stream.scaladsl._
import akka.NotUsed
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.ws._
import phu.quang.le.actors.ChatRoom
import phu.quang.le.actors.User
import akka.http.scaladsl.model.headers.HttpCookie
import scala.concurrent._
import scala.concurrent.duration._
import akka.http.scaladsl.Http.ServerBinding
import akka.event.Logging
import scala.util.{ Success, Failure }
import redis.RedisClient
import phu.quang.le.actors.SubscribeActor
import phu.quang.le.utils.Configuration
import java.util.UUID

object Main extends App with Configuration {
  
  implicit val system = ActorSystem("my-system")
  implicit val context = system.dispatcher
  implicit val materializer = ActorMaterializer()
  
  val logger = Logging.getLogger(system, this);
  
  configure {
    val redis = RedisClient()  
    redis
  }
  
  def wsHandler: Flow[Message, Message, NotUsed] = {
    val user = system.actorOf(User.props, UUID.randomUUID.toString())
    
    val source: Source[Message, NotUsed] = 
      Source.actorRef[User.MessageReceived](100, OverflowStrategy.fail).mapMaterializedValue { 
        webSocket =>
           user ! User.Connected(webSocket)
           NotUsed
      }.map {
        received => TextMessage(received.message)
      }

    val sink: Sink[Message, NotUsed] = 
      Flow[Message].map {
        case tm: TextMessage => User.MessageSent(tm.getStrictText)
        case bm: BinaryMessage => ???
      }.to(Sink.actorRef[User.MessageSent](user, PoisonPill))
    
    Flow.fromSinkAndSource(sink, source)
  }

  val routes: Flow[HttpRequest, HttpResponse, NotUsed] = {
    path("chat") {
      setCookie(HttpCookie("VLAXUUID", "aaabbbbcccc")) {
        handleWebSocketMessages { wsHandler }  
      }
    }
  }
  
  val (host, port) = ("0.0.0.0", 10000)
  val bindingFuture: Future[ServerBinding] = Http().bindAndHandle(routes, host, port)
  
  bindingFuture.onComplete {
    case Success(binding) => logger.info("Server binded successfully to {}:{}", host, port)
    case Failure(ex) => logger.error(ex, "Failed to bind to {}:{}", host, port)
  }
}