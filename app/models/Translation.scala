package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

/** Class to hold a translation of an article */
case class Translation(id: Long, languageId: String, body: Option[String] = None )
/* body parameter is optional as the API request for a list of articles does not return it */


object Translation {
  implicit val translationReads: Reads[Translation] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "language_id").read[String] and
      (JsPath \ "body").readNullable[String]
    )(Translation.apply _)
}
