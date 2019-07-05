package models

import play.api.data._
import play.api.data.Forms._

import play.api.data.validation.Constraints._

case class Search(keywords: String)

// this could be defined somewhere else,
// but I prefer to keep it in the companion object
object Search {
  val searchForm: Form[Search] = Form(
    mapping(
      "keywords" -> text
    )(Search.apply)(Search.unapply)
  )

  val searchFormConstraints = Form(
    mapping(
      "keywords" -> nonEmptyText,
    )(Search.apply)(Search.unapply)
  )
}
