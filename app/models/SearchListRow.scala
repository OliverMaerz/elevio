package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class SearchListRow(id: Long, title: String, category_id: Long)

object SearchListRow {
  implicit val searchListRowReads: Reads[SearchListRow] = (
    // for some reason the id on search response  is a string - so map to Long
    (JsPath \ "id").read[String].map[Long](_.toLong) and
      (JsPath \ "title").read[String] and
      (JsPath \ "category_id").read[Long]
    )(SearchListRow.apply _)
}





