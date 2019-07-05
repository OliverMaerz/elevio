package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


/** Class to hold the list of articles from a search response */
case class SearchList(keywords: String, pages: Long, entries: Long, pageSize: Long, pageNumber: Long, results: List[SearchListRow])

object SearchList {
  import SearchListRow._
  implicit val searchListReads: Reads[SearchList] = (
    (JsPath \ "queryTerm").read[String] and
      (JsPath \ "totalPages").read[Long] and
      (JsPath \ "totalResults").read[Long] and
      (JsPath \ "totalPages").read[Long] and
      (JsPath \ "currentPage").read[Long] and
      (JsPath \ "results").read[List[SearchListRow]]
    )(SearchList.apply _)
}




