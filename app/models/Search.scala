package models

import play.api.data._
import play.api.data.Forms._

import play.api.data.validation.Constraints._


/* class to hold the data entered in the search form (currently only the text field for keywords)  */
case class Search(keywords: String)

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
