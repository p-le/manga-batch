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

object Main extends App {
  
  implicit val system = ActorSystem("my-system")
  implicit val context = system.dispatcher
  implicit val materializer = ActorMaterializer()
  
  val chatRoom = system.actorOf(ChatRoom.props("dashboard"))
  
  def wsHandler: Flow[Message, Message, NotUsed] = {
    Flow[Message].mapConcat {
      case tm: TextMessage => TextMessage(Source.single("Hello ") ++ tm.textStream) :: Nil
      case bm: BinaryMessage =>
        bm.dataStream.runWith(Sink.ignore)
        Nil
    }
  }

  val routes: Flow[HttpRequest, HttpResponse, NotUsed] = {
    path("chat") {
      setCookie(HttpCookie("VLAXUUID", "aaabbbbcccc")) {
        handleWebSocketMessages { wsHandler }  
      }
    }
  }
  
  val bindingFuture = Http().bindAndHandle(routes, "0.0.0.0", 10000)
}