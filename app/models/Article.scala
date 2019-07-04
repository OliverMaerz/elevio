package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class Article(id:Long, title: String, translations: Option[List[Translation]] = None)
// Translation is optional as it is not returned in the API request for a list of articles

object Article {
  implicit val articleReads: Reads[Article] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "title").read[String] and
      (JsPath \ "translations").readNullable[List[Translation]]
    )(Article.apply _)
}









