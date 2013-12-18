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
package se.weln.noyt.parsing

import java.text.SimpleDateFormat
import java.util.Date

import spray.json._

import se.weln.noyt.data.Video

/**
 * Defines methods to parse the Json downloaded using the Google Youtube API.
 */
class JsonVideoParser extends VideoParser {

  /**
   * Defines the time format used in the Json data.
   */
  val TimeFormat = "yyyy-MM-dd'T'HH:mm:SS.s'Z'"

  /**
   * Parses a String to a Date.
   */
  def parseDate(date: String) =
    new SimpleDateFormat(TimeFormat).parse(date)

  /**
   * Parses a String into a Json object and queries the "feed" field which
   * contains all the desired information.
   */
  def feed(json: String) =
    json.asJson.asJsObject.getFields("feed").head.asJsObject

  /**
   * Returns the author of a feed from the "feed" Json object.
   */
  def author(feed: JsObject): Seq[Option[String]] = feed.fields.get("author") match {
    case Some(JsArray(list)) => list.headOption match {
      case Some(o: JsObject) => o.getFields("name", "uri") map {
        case JsObject(t) => t.get("$t")
        case _ => None
      } map {
        case Some(JsString(name)) => Some(name)
        case _ => None
      }
      case _ => Seq()
    }
    case _ => Seq()
  }

  /**
   * Parses the "feed" Json object and returns a list of videos from the given
   * information.
   */
  def videos(feed: JsObject): Seq[Video] = {
    /* Parse author */
    val auth = author(feed)

    /* Parse videos in the "entry" field */
    feed.fields.get("entry") match {
      case Some(JsArray(entries)) => entries map {
        /* Get information of one Video */
        _.asJsObject.getFields("id", "published", "title") map {
          case JsObject(fields) => fields.get("$t")
          case _ => None
        } map {
          /* Unwrap the JsStrings into Strings */
          case Some(JsString(e)) => Some(e)
          case _ => None
        }
      } map { entry =>
        /* Transform the list of Strings into a Video object */
        Video(
          id = entry.head.map(_.split("/").last),
          publishTime = entry(1).map(parseDate(_)),
          title = entry(2),
          author = auth(0),
          channel = auth(1).map(_.split("/").last))
      }
      case _ => Seq()
    }
  }

  /**
   * Takes a Json formated String, parses the information and returns a list of
   * Videos.
   */
  def apply(jsonString: Option[String]): Seq[Video] = jsonString match {
    case Some(json) => videos(feed(json))
    case None => Seq()
  }
}

object JsonVideoParser {

  /**
   * Returns a new JsonVideoParser
   */
  def parseFeed = new JsonVideoParser
}
