package phu.quang.le.actors

import akka.actor.Actor
import akka.stream.ActorMaterializer
import akka.stream._
import akka.stream.scaladsl._
import akka.{ NotUsed, Done }

import scala.concurrent._
import scala.concurrent.duration._


object MyActor {
  
  final case object Hello
  final case object PP
  
  final case class Factorial(n: Int)
  
  final case class Author(handle: String)
  final case class HashTag(name: String)
  final case class Tweet(author: Author, timestamp: Long, body: String) {
    def hashtags: Set[HashTag] = 
      body.split(" ").collect {
        case t if t.startsWith("#") => HashTag(t)
      }.toSet
  }
}

class MyActor()(implicit materializer: ActorMaterializer) extends Actor {
  import context.dispatcher
  import MyActor._
  
  def receive: Actor.Receive = {
    case Hello => 
      val source: Source[Int, NotUsed] = Source(1 to 5)
      source.runForeach { println(_) }
      
    case Factorial(n) =>
      println(s"Num: $n")
      val source: Source[Int, NotUsed] = Source(1 to n)
//      source.runForeach { println(_) }
      val factorials = source.scan(BigInt(1))((acc, next) => acc * next)
      
      factorials
        .zipWith(Source(1 to n))((num, idx) => s"$idx! = $num")
        .throttle(1, 1 second, 1, ThrottleMode.shaping)
        .runForeach(println)
    case PP =>
      val akkaTag = HashTag("#akka")
      val tweets: Source[Tweet, NotUsed] = ???

      val hashtags: Source[HashTag, NotUsed] = tweets.mapConcat { _.hashtags.toList }
      val authors: Source[Author, NotUsed] = tweets.filter { _.hashtags.contains(akkaTag) }.map { _.author }
      val writeAuthors: Sink[Author, Unit] = ???
      val writeHashtags: Sink[HashTag, Unit] = ???
      val count: Flow[Tweet, Int, NotUsed] = Flow[Tweet].map(_ => 1)
      
      val g = RunnableGraph.fromGraph(GraphDSL.create() {
        implicit b =>
          import GraphDSL.Implicits._
          
          val broadcast = b.add(Broadcast[Tweet](2))
          tweets ~> broadcast.in
          broadcast.out(0) ~> Flow[Tweet].map(_.author) ~> writeAuthors
          broadcast.out(1) ~> Flow[Tweet].mapConcat(_.hashtags.toList) ~> writeHashtags
          ClosedShape
      })

      authors.runWith(Sink.foreach(println))
      g.run()
      
    case _ => println("Sup?")
  }
}