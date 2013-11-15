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
package com.mooo.nilewapps.noyt.services

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

import spray.http.MediaTypes._
import spray.routing.Directives._
import spray.routing.RequestContext

import com.mooo.nilewapps.noyt.feeds.FeedGenerator._

trait FeedService {

  /**
   * Returns a route to a video feed.
   */
  def feed(channels: String): RequestContext => Unit = {
    parameters('maxResults.as[Int] ?) { maxResults =>
      respondWithMediaType(`text/html`) {
        complete {
          /* Download, parse, aggregate and render feeds */
          feeds(channels.split('+'), maxResults.getOrElse(25)) map {
            html.feed.render(_).body
          }
        }
      }
    }
  }
}
