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
package se.weln.noyt.services

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

import spray.http.MediaTypes._
import spray.routing.Directives._
import spray.routing.RequestContext

import se.weln.noyt.feeds.FeedGenerator._
import se.weln.noyt.html

trait FeedService {

  /**
   * Returns a route to a video feed.
   */
  def feed(channels: String): RequestContext => Unit = {
    parameters('maxResults.as[Int] ?, 'select ?) { (maxResults, select) =>
      respondWithMediaType(`text/html`) {
        complete {
          /* Download, parse, aggregate and render feeds */
          val channelList =
            select.getOrElse(channels).split('+').filter(!_.isEmpty)
          feeds(channelList, maxResults.getOrElse(16)) map {
            html.feed.render(channels, select, _).body
          }
        }
      }
    }
  }
}
