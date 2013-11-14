/**
 *  Copyright 2013 Robert Welin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mooo.nilewapps.noyt.parsing

import java.text.SimpleDateFormat
import java.util.Date
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

import spray.json._
import spray.json.DefaultJsonProtocol._

import com.mooo.nilewapps.noyt.data.Video

object JsonVideoParser extends VideoParser {

  val TimeFormat = "yyyy-MM-dd'T'HH:mm:SS.s'Z'"

  def parseDate(date: String) =
    new SimpleDateFormat(TimeFormat).parse(date)

  def author(feed: JsObject) = feed.fields.get("author") match {
    case Some(JsArray(List(JsObject(authorFields), _*))) =>
      authorFields.get("name") flatMap {
        case JsObject(nameFields) => nameFields.get("$t") match {
          case Some(JsString(name)) => Some(name)
          case Some(_) | None => None
        }
        case _ => None
      }
    case _ => None
  }

  def videos(feed: JsObject) = {
    val auth = author(feed)
    feed.fields.get("entry") match {
      case Some(JsArray(entries)) => entries map {
        _.asJsObject.getFields("id", "published", "title") map {
          case JsObject(fields) => fields.get("$t")
          case _ => None
        } map {
          case Some(JsString(e)) => Some(e)
          case _ => None
        }
      } map { entry =>
        Video(
          id = entry.head.map(_.split("/").last),
          publishTime = entry(1).map(parseDate(_)),
          title = entry(2),
          user = auth)
      }
      case _ => Seq()
    }
  }

  def apply(jsonString: Seq[Future[Option[String]]]): Seq[Future[Seq[Video]]] = {
    def feed(json: String) = json.asJson.asJsObject.getFields("feed").head.asJsObject

    jsonString map {
      _ map {
        case Some(json) => videos(feed(json))
        case None => Seq()
      }
    }
  }
}
