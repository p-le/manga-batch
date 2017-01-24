package phu.quang.le.models

case class Quiz (
  question: String,
  correctAnswer: String
)

object Quiz {
  case object Deleted
  case class Created(id: String)
}