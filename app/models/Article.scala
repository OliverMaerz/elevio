package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


case class Article(id:Long, date: DateTime, author: Option[String] = None, title: String, translations: Option[List[Translation]] = None)
// Translation is optional as it is not returned in the API request for a list of articles

object Article {
  // helper to read data format from json response
  val dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
  val jodaDateReads: Reads[DateTime] = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat))
    )
  )

  implicit val articleReads: Reads[Article] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "updated_at").read[org.joda.time.DateTime](jodaDateReads) and
      (JsPath \ "author").readNullable((JsPath \ "name").read[String]) and
      (JsPath \ "title").read[String] and
      (JsPath \ "translations").readNullable[List[Translation]]
    )(Article.apply _)
}









