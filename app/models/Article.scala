package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class Article(id:Int, title: String, body: Option[String] = None)
// body parameter is optional as it is not returned in the API request for a list of articles

object Article {
  implicit val articleReads: Reads[Article] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "title").read[String] and
      (JsPath \ "body").readNullable[String]
    )(Article.apply _)
}


case class Articles(entries: Int, articles: List[Article])

object Articles {
  import Article._
  implicit val articlesReads: Reads[Articles] = (
    (JsPath \ "entries").read[Int] and
      (JsPath \ "articles").read[List[Article]]
    )(Articles.apply _)

}







