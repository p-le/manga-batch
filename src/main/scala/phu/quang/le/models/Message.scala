package phu.quang.le.models

import akka.stream.scaladsl.Source
import akka.util.ByteString

sealed trait Message

sealed trait TextMessage extends Message {
  def textStream: Source[String, _]
}

sealed trait BinaryMessage extends Message {
  def dataStream: Source[ByteString, _]
}