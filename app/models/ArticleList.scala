package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class ArticleList(pages: Long, entries: Long, pageSize: Long, pageNumber: Long, article: List[Article])

object ArticleList {
  import Article._
  implicit val articleListReads: Reads[ArticleList] = (
    (JsPath \ "total_pages").read[Long] and
      (JsPath \ "total_entries").read[Long] and
      (JsPath \ "page_size").read[Long] and
      (JsPath \ "page_number").read[Long] and
      (JsPath \ "articles").read[List[Article]]
    )(ArticleList.apply _)
}




