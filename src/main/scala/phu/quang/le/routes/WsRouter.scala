package phu.quang.le.routes

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.model.ws.{ Message, TextMessage }
import akka.stream.scaladsl.{ Source, Flow }

trait WsRouter extends Directives {
  def route = 
    path("register") {
      parameter('name) {
        name => handleWebSocketMessages(broadcast(name))
      }  
    }
  
  def broadcast(name: String): Flow[Message, Message, Any] = {
    Flow[Message].mapConcat { 
      case tm: TextMessage =>
        TextMessage(Source.single(name + "::") ++ tm.textStream) :: Nil
      case _ =>
        TextMessage(Source.single(name + "::")) :: Nil
    }
  }
}